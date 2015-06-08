import gzip
import math
import numpy
import re
import StringIO


class Atom(object):

    def __init__(self):

        self.serial = 0
        self.id = ""
        self.residue = ""
        self.chain = 0
        self.residue_serial = 0

        self.X = 0.0
        self.Y = 0.0
        self.Z = 0.0

    def __str__(self):

        return "RES {res_n}:{res_id} ATOM {a_n}: {a_id} -> X: {a_x}, Y: {a_y}, Z: {a_z}".format(

            res_n=self.residue_serial,
            res_id=self.residue,
            a_n=self.serial,
            a_id=self.id,
            a_x=self.X,
            a_y=self.Y,
            a_z=self.Z
        )


class HAtom(object):

    def __init__(self):

        self.serial = 0
        self.id = ""

        self.mol = ""

        self.X = 0.0
        self.Y = 0.0
        self.Z = 0.0

    def __str__(self):

        return "HATOM {a_n}: {a_id} -> X: {a_x}, Y: {a_y}, Z: {a_z}".format(

            a_n=self.serial,
            a_id=self.id,
            a_x=self.X,
            a_y=self.Y,
            a_z=self.Z
        )


class Contact(object):

    def __init__(self, atom, hatom, distance):

        self.atom = atom
        self.hatom = hatom
        self.distance = distance

    def __str__(self):
        return "AT {an:4d}: {res:3s}[{rid:3d}] {at:3s} ---{dist:2.4f}--- {hat:3s} {hn:4d}".format(
            an=self.atom.serial,
            res=self.atom.residue,
            rid=self.atom.residue_serial,
            at=self.atom.id,
            dist=self.distance,
            hat=self.hatom.id,
            hn=self.hatom.serial
        )


class PDB(object):

    DEFAULT_CUTOFF = 6.0
    RELEVANT_CONTACTS = 2

    def __init__(self):

        self.rx_atoms = re.compile(r"^ATOM *")
        self.rx_hatoms = re.compile(r"^HETATM *")

        self.atoms = list()
        self.hatoms = list()

        self.n_atoms = 0
        self.n_hatoms = 0

        self.default_contacts = list()

        self.cutoff = self.DEFAULT_CUTOFF
        self.relevant_number_of_contacts = self.RELEVANT_CONTACTS

    def read_file(self, filename):

        with gzip.open(filename) as pdbfile_z:

            self.id = filename[-11:-7]
            pdbfile = pdbfile_z.readlines()
            self._parse_atoms(pdbfile)
            self._parse_hatoms(pdbfile)

    def _parse_atoms(self, pdbfile):

        for line in filter(self.rx_atoms.match, pdbfile):

            at = Atom()
            at.serial = int(line[7:11])
            at.id = line[13:16].strip()
            at.residue = line[17:20].strip()
            at.residue_serial = int(line[23:26])
            at.chain = line[22].strip()
            at.X = float(line[31:38])
            at.Y = float(line[39:46])
            at.Z = float(line[47:54])

            # Filtramos somente os carbonos alfa:
            if at.id == "CA":
                self.atoms.append(at)

        return at

    def _parse_hatoms(self, pdbfile):

        for line in filter(self.rx_hatoms.match, pdbfile):

            at = HAtom()
            at.serial = int(line[7:11])
            at.id = line[13:16].strip()
            at.mol = line[17:20].strip()
            at.X = float(line[31:38])
            at.Y = float(line[39:46])
            at.Z = float(line[47:54])

            # filtramos as aguas fora
            if not "HOH" in at.mol:
                self.hatoms.append(at)

        return at

    def calc_distance_matrix(self, cutoff=None):

        if cutoff is not None:
            self.cutoff = cutoff

        self.I = range(1, len(self.atoms))
        self.J = range(1, len(self.hatoms))

        self.DMAT = numpy.zeros((len(self.I) + 1, len(self.J) + 1))

        # Calculamos a distancia entre os atomos e salvamos na matriz
        for i in self.I:
            for j in self.J:

                # Distance = ( ((X(a) - X(b))^2) + ((Y(a) - Y(b))^2) ((Z(a) - Z(b))^2) )^1/3

                atom = self.atoms[i]
                hatom = self.hatoms[j]

                dX = math.pow(atom.X - hatom.X, 2)
                dY = math.pow(atom.Y - hatom.Y, 2)
                dZ = math.pow(atom.Z - hatom.Z, 2)

                distance = math.sqrt(dX + dY + dZ)

                self.DMAT[i][j] = distance

                # Se o a distancia for menor que o cutoff padrao, ja adicionamos na lista poupando tempo
                if distance < cutoff:
                #    print("Adding distance {} to the list".format(distance))
                    self.default_contacts.append(Contact(atom, hatom, distance))

    def get_contacts_by_cutoff(self, cutoff):

        if cutoff is not None:
            self.cutoff = cutoff

        for i in self.I:
            for j in self.J:
                distance = self.DMAT[i][j]
                if distance <= cutoff:
                    yield Contact(self.atoms[i], self.hatoms[j], distance)

    def _accumulate_contacts_by_atoms(self):

        for i in self.I:

            n_contacts = 0
            total_distance = 0.0

            for j in self.J:

                distance = self.DMAT[i][j]

                if distance <= self.cutoff:
                    n_contacts += 1
                    total_distance += distance

            # para cara carbono, retornamos o numero de contatos e a distancia media
            if n_contacts > self.relevant_number_of_contacts:
                yield (self.atoms[i], n_contacts, total_distance / n_contacts)

    def print_accumulated_contacts_by_atoms(self):

        for pair in self._accumulate_contacts_by_atoms():
            print("CA {id:4d} RES {res:3s}:{rid:4d} = {c:3d} CTS, {dist:1.4f} AVG_D".format(
                id=pair[0].serial, res=pair[0].residue, rid=pair[0].residue_serial, c=pair[1],
                dist=pair[2]))

    def _accumulate_contacts_by_hatoms(self):

        for j in self.J:

            n_contacts = 0
            total_distance = 0.0

            for i in self.I:

                distance = self.DMAT[i][j]

                if distance <= self.cutoff:
                    n_contacts += 1
                    total_distance += distance

            if n_contacts > self.relevant_number_of_contacts:
                yield (self.hatoms[j], n_contacts, total_distance / n_contacts)

    def print_accumulated_contacts_by_hatoms(self):
        for pair in self._accumulate_contacts_by_hatoms():
            print("HETAM {id:4d} {name:4s} = {c:3d} CTS, {dist:1.4f} AVG_D".format(
                id=pair[0].serial, name=pair[0].id, c=pair[1], dist=pair[2]))

    def __str__(self):

        output = StringIO.StringIO()

        output.write(80 * "#" + "\n")
        output.write("  PROTEIN: {}\n".format(self.id))
        output.write(80 * "#" + "\n")
        # PRINT ATOMS
        output.write(80 * "#" + "\n")
        output.write("     ATOMS     \n")
        output.write(80 * "#" + "\n")
        for atom in self.atoms:
            output.write(atom)
        output.write(80 * "#" + "\n")
        # PRINT HATOMS
        output.write(80 * "#" + "\n")
        output.write("     HATOMS     \n")
        output.write(80 * "#" + "\n")
        for atom in self.hatoms:
            output.write(atom)
        output.write(80 * "#" + "\n")
        io = output.getvalue()
        output.close()
        return io
