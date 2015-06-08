import textwrap


class Protein(object):

    protid = None
    chain = None
    sequence = None

    #def __init__(self, protid=None, chain=None, sequence=None):
    #
    #    self.protid = protid
    #    self.chain = chain
    #    self.sequence = sequence

    def __init__(self, protein_file=None):

        if protein_file:
            self.parse(protein_file)

    def __str__(self):

        ret = 80*"#"
        ret += "\n"

        seq = textwrap.wrap(self.sequence, 80)

        ret += "ID: {} Chain: {} Size: {}\nSequence:".format(self.protid, self.chain, len(self.sequence))

        for line in seq:
           ret += "\n{l}".format(l=line)

        ret += "\n"
        ret += 80*"#"
        ret += ""
        return ret

    def parse(self, protein_file):

        '''
        Parseia os arquivos que contem uma unica proteina

         ''' 
        with open(protein_file) as f:

            data = f.readlines()

            # Checando o formato do arquivo de entrada (porcamente)
            if data[0][0] != ">":
                raise ValueError("Arquivo de entrada com formato invalido")

            protid = data[0][1:].replace("\n", "")
            sequence = "".join(data[1:]).replace("\n", "")

            self.protid=protid
            self.chain=""
            self.sequence=sequence

            return self

    def size(self):
        return len(self.sequence)

