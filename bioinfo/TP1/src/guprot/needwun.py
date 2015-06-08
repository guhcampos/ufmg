import numpy
import textwrap
import sys


class NeedWunAlignment(object):

    DMAT = None    # a matriz de distancias
    SMAT = None
    GAP = -1       # a penalidade por gapping

    I = None
    J = None

    PA = None
    PB = None

    SEQA = None
    SEQB = None

    ALIGNED = False

    # DMAT = Distance Matrix
    # SMAT = Substitution Matrix
    # GAP  = Gap Penalty
    # I    = Sequence A Range
    # J    = Sequence B Range
    # PA   = Protein A
    # PB   = Protein B
    # SEQA = Protein A aligned to B
    # SEQB = Protein B aligned to A
    # ALIGNED = Estao alinhadas?


    def __init__(self, protein_a, protein_b, subst_matrix, gap=None):

        if gap is not None:
            self.GAP = gap

        self.SMAT = subst_matrix

        # Inicializamos os ranges de iteracao
        self.I = range(1, len(protein_a.sequence))
        self.J = range(1, len(protein_b.sequence))

        # Inicializamos a matriz com zeros
#        self.DMAT = numpy.zeros((len(self.J)+1, len(self.I)+1), dtype=numpy.int16)
        self.DMAT = numpy.zeros((len(self.I)+1, len(self.J)+1), dtype=numpy.int16)


        #########
        # DEBUG #
        #########
        # print(
        #     """
        #     ********** DEBUG ***********
        #     DMAT = [{dmatx}] x [{dmaty}]
        #     I = {i}
        #     J = {j}
        #     ********** ENDDEBUG ********
        #     """.format(
        #         dmatx=len(self.DMAT),
        #         dmaty=len(self.DMAT[0]),
        #         i=len(self.I),
        #         j=len(self.J)
        #     )
        # )

        # Preenchemos com as penalidades as primeiras linha/coluna da matriz
        for i in self.I:
            self.DMAT[i][0] = i * self.GAP;

        for j in self.J:
            self.DMAT[0][j] = j * self.GAP;

        self.PA = protein_a
        self.PB = protein_b

    def _similaridade(self, A, B):
        return self.SMAT.similarity(A, B)

    def _compute_matrix(self, gap=None):

        if gap is None:
            gap = self.GAP

        for i in self.I:

            # Qual aminoacido estamos alinhando em A
            A = self.PA.sequence[i]

            for j in self.J:

                # Qual aminoacido estamos alinhando em B
                B = self.PB.sequence[j]

                # Computamos os scores dos tres casos possiveis
                Match  = self.DMAT[i-1][j-1] + self._similaridade(A, B)
                Delete = self.DMAT[i-1][j] + gap
                Insert = self.DMAT[i][j-1] + gap

                # E atualizamos a posicao corrente da matriz
                self.DMAT[i][j] = max(Match, Delete, Insert)

        #print self.DMAT

    def _traceback(self, gap=None):

        if gap is None:
            gap = self.GAP

        align_a = ""
        align_b = ""

        i = len(self.I)
        j = len(self.J)

        while i > 0 or j > 0:

            A = self.PA.sequence[i]
            B = self.PB.sequence[j]

            if i > 0 and j > 0 and self.DMAT[i][j] == self.DMAT[i-1][j-1] + self._similaridade(A, B):
                align_a += A
                align_b += B
                i -= 1
                j -= 1

            elif i > 0 and self.DMAT[i][j] == self.DMAT[i-1][j] + gap:
                align_a += A
                align_b += "-"
                i -= 1

            elif j > 0 and self.DMAT[i][j] == self.DMAT[i][j-1] + gap:
                align_a += "-"
                align_b += B
                j -= 1

            else:
                raise ValueError("Traceback falhou por falta de casos")


        return (align_a[::-1], align_b[::-1])


    def _align(self, gap=None):

        self._compute_matrix(gap)
        aligna, alignb = self._traceback(gap)
        counter = self._count_mutations(aligna, alignb)

        return (aligna, alignb, counter)
        

    def default_alignment(self):

        self.SEQA, self.SEQB, self.mutations = self._align()
        self.ALIGNED = True

        return {
            "protein_a": self.PA,
            "protein_b": self.PB,
            "sequence_a": self.SEQA, 
            "sequence_B": self.SEQB, 
            "score": self.mutations,
        }

    def find_best_alignment(self):

        best = -10
        best_mutations = int(sys.maxint)

        '''
        Nos testes foi percebido que nao havia variacao para valores abaixo de -4 e acima de 4
        Para diminuir o tempo de processamento, mas sem prejudicar a ilustracao do algoritmo, 
        mantemos a pesquisa entre -5 e 5
        '''
        for gap in xrange(-5, 5):

            try:
                a, b, i = self._align(gap)
            except:
                pass

            print("Usando gap: {gap} obtemos: {mut} mutacoes".format(gap=gap, mut=i))

            # Finalmente repetimos o melhor alinhamento
            if i <= best_mutations:
                best = gap
                best_mutations = i

        self.SEQA, self.SEQB, self.mutations = self._align(best)
        self.ALIGNED = True
        self.gap = best

        return {
            "protein_a": self.PA,
            "protein_b": self.PB, 
            "sequence_a": self.SEQA, 
            "sequence_b": self.SEQB, 
            "score": self.mutations
        }


    def _count_mutations(self, aligna, alignb):

        counter = 0

        for (a, b) in zip(aligna, alignb):
            if a != b:
                counter += 1

        return counter


    def __str__(self):

        ret = ""

        if not self.ALIGNED:

            ret += "\n"
            ret += 80*"@"
            ret += "\nProteinas nao alinhadas:\n"
            ret += self.PA.__str__()
            ret += self.PB.__str__()
            ret += 80*"@"
            ret += "\n"

        else:

            ret += "\n"
            ret += 80*"@"
            ret += "\nProteinas alinhadas:\n"
            ret += 80*"#"
            ret += "\n"

            seqa = textwrap.wrap(self.SEQA, 80, break_on_hyphens=False)
            seqb = textwrap.wrap(self.SEQB, 80, break_on_hyphens=False)

            print seqa

            for linea, lineb in zip(seqa, seqb):
                ret += "A: {}\n".format(linea)
                ret += "B: {}\n".format(lineb)
                ret += 80 * " "

            ret += 80*"@"
            ret += "\n"

        return ret
