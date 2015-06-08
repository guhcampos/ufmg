import numpy

class SubMatrix(object):

    SMAT = dict()
    COL_NUMBER = 25

    def __init__(self, matrix_file=None):
        if matrix_file:
            self.parse(matrix_file)


    def parse(self, mfile):
        
        # carregamos os dados da tabela em uma matriz
        d = numpy.loadtxt(mfile, skiprows=1, usecols=range(1, self.COL_NUMBER), dtype=numpy.int16)

        with open(mfile) as f:
            headers = f.readlines()[0].split()

            # criamos o primeiro nivel de mapas usando os cabecalhos da tabela
            for char in headers:
                self.SMAT[char] = dict()

            # agora preenchemos o mapa com os dados da matriz
            for i in range(0, len(d)):
                for j in range(0, len(d)):
                    self.SMAT[headers[i]][headers[j]] = d[i][j]

            return self

    def similarity(self, A, B):
        return self.SMAT[A][B]