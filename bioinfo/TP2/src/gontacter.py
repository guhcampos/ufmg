import argparse
import os
from gontacts.pdb import PDB

def gontacter():

    argparser = argparse.ArgumentParser()

    argparser.add_argument("-d", "--dir", dest="datadir", required=True)

    args = argparser.parse_args()


    # Lemos todos os arquivos no diretorio
    allfiles = [ f for f in os.listdir(args.datadir) if f.endswith('.pdb.gz')]

    pdbfiles = list()

    # Parseamos todos eles
    for pdbfile in allfiles:

        p = PDB()

        print("\nLendo arquivo {}".format(pdbfile))
        p.read_file(args.datadir+"/"+pdbfile)

        print("Lidos {} carbonos alpha and {} heteroatomos".format(len(p.atoms), len(p.hatoms)))
        pdbfiles.append(p)

        print("Computando matriz de distancias...")
        p.calc_distance_matrix(6.0)

        print("Encontrados {} contatos para valor de cutoff {}").format(len(p.default_contacts), p.DEFAULT_CUTOFF)

        print("\nContatos encontrados:")
        print(80*"#")
        p.print_accumulated_contacts_by_atoms()
        #p.print_accumulated_contacts_by_hatoms()
        print(80*"#")

    print("INICIANDO ANALISE")

    freq_total = dict()
    freq_arquivo = dict()

    for pdbfile in pdbfiles:

        for contact in pdbfile.default_contacts:

            cid = "{:3d}|{:3s}".format(contact.atom.residue_serial,contact.atom.residue)

            if cid not in freq_total.keys():
                freq_total[cid] = 1
                freq_arquivo[cid] = 0

    # conta quantos contatos fez
    for pdbfile in pdbfiles:
        for contact in pdbfile.default_contacts:
            cid = "{:3d}|{:3s}".format(contact.atom.residue_serial,contact.atom.residue)
            freq_total[cid] += 1


    # conta quantos arquivos aparece
    for pdbfile in pdbfiles:
        unique_cids = list()
        for contact in pdbfile.default_contacts:
            cid = "{:3d}|{:3s}".format(contact.atom.residue_serial,contact.atom.residue)
            if cid not in unique_cids:
                unique_cids.append(cid)

        for cid in unique_cids:
            freq_arquivo[cid] += 1


    for key in sorted(freq_total.keys()):
        print("{} fez {:3d} contatos em {:2d} arquivos".format(key, freq_total[key], freq_arquivo[key]))


if __name__ == "__main__":
    gontacter()