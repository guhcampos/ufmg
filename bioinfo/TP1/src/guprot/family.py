#import textwrap
from guprot.protein import Protein
from guprot.needwun import NeedWunAlignment

class Family(object):

    base_protein = None
    base_sequence = None
    family = None
    score_matrix = None
    largest_protein = None

    def __init__(self, base_protein, base_sequence):

        self.base_protein = base_protein
        self.base_sequence = base_sequence
        self.largest_protein = 0


    def build_score(self, gap, sub_matrix):

        if len(self.family) is 0:
            raise ValueError("Eh preciso ler o arquivo de proteinas da familia")

        print("Alinhando proteinas da familia com a original")

        # Inicializamos uma matriz com tamanho da maior sequencia
        size = len(self.base_sequence)+self.largest_protein
        score = [ 0 for i in range(0, size) ]

        for prot in self.family:

            if prot.protid is not None:

                print("Alinhando {pa} [{pas}]com {pb}.{ch} [{pbs}]".format(
                    pa=self.base_protein.protid, 
                    pb=prot.protid,
                    pas=self.base_protein.size(), 
                    pbs=prot.size(),
                    ch=prot.chain
                ))

                nw = NeedWunAlignment(
                    protein_a=self.base_protein,
                    protein_b=prot,
                    subst_matrix=sub_matrix,
                    gap=gap
                 )

                nw.default_alignment()

                try:
                # agora percorremos as sequencias alinhadas e incrementamos a score matrix
                    i = 0
                    for a, b in zip(nw.SEQA, nw.SEQB):
                        if a != b:
                            score[i] += 1
                        i += 1
                except:
                    print("Valor de i={}".format(i))
                    print("Tamanho de score={}".format(len(score)))
                    raise

                self.score_matrix = score

        return self


    def parse_protein_family_file(self, family_file):
        '''
        Parseia o arquivo com a lista de proteinas da familia

        '''

        protein_list = list()

        print("Carregando proteinas similares da familia")

        with open(family_file) as f:

            data = f.readlines()

            # Checando o formato do arquivo de entrada (porcamente)
            if data[0][0] != ">":
                raise ValueError("Arquivo de entrada com formato invalido")

            else:

                d = iter(data)
                p = Protein()
                s = ""

                try:
                    while d:
                        '''
                        Loop que consome as linhas do arquivo atraves do iterador, criando uma nova proteina
                        sempre que encontra o sinal de ">"
                        '''
                        line = d.next()

                        if line[0] == ">":
                            '''
                            Se eh uma nova proteina, a sequencia anterior acabou, precisamos entao inseri-la
                            no objeto antigo e "apenda-lo" na lista de proteinas
                            '''
                            p.sequence = s

                            '''
                            Mantemos a informacao de qual a maior proteina da familia
                            '''
                            l = len(s)
                            if self.largest_protein < l:
                                self.largest_protein = l


                            protein_list.append(p)

                            '''
                            Agora resetamos o objeto de proteina e o de sequencia
                            '''
                            p = Protein()
                            p.protid=line[1:5]
                            p.chain=line[6]
                            s = ""

                        else:
                            s += line.replace("\n", "")

                except StopIteration:
                    pass

            print("Carregadas {} proteinas de familia com sucesso".format(len(protein_list)))
            print("A maior proteina possui {} residuos".format(self.largest_protein))

            self.family = protein_list        

        return self

    def _scoremean(self):
        return sum(self.score_matrix) / len(self.score_matrix)
        #return numpy.stats.hmean(self.score_matrix)

    def score_alignment(self, aligned_sequence):

        bad_guys = list()
        i = 0
        m = self._scoremean()


        # iteramos sobre a sequencia original e a alinhada
        for a, b in zip(self.base_sequence, aligned_sequence):
            # quando houver mutacoes, comparamos com nossa score matrix
            if a != b:

                ##############
                ## DEBUG #####
                ##############
                # print(
                #     """
                #     a         = {a}
                #     b         = {b}
                #     mean      = {m}
                #     matrix[i] = {mi}
                #     """.format(
                #         a=a,
                #         b=b,
                #         m=self._scoremean(),
                #         mi=self.score_matrix[i]
                #     )
                # )


                if self.score_matrix[i] < m:
                    '''
                    Se essa mutacao ocorre menos vezes que a media de ocorrencias de mutacoes, consi
                    deramos uma mutacao danosa.

                    OBS.: isso foi bem arbitrario
                    '''
                    bad_guys.append((a, b, i, self.score_matrix[i]))

            i += 1

        return bad_guys


