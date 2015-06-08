#/usr/bin/env python27

import argparse
from guprot.protein import Protein
from guprot.needwun import NeedWunAlignment
from guprot.family import Family
from guprot.submat import SubMatrix


def guprotaligner():

    argparser = argparse.ArgumentParser()

    argparser.add_argument('-w', '--wild', dest='wild_protein', required=True)
    argparser.add_argument('-m', '--mutated', dest='mutated_protein', required=True)
    argparser.add_argument('-f', '--family', dest='family_db')
    argparser.add_argument('-s', '--submatrix', dest='sub_matrix', required=True)

    args = argparser.parse_args()

    # Inicializamos as proteinas a partir dos arquivos
    wild_protein = Protein()
    wild_protein.parse(args.wild_protein)

    mutated_protein = Protein()
    mutated_protein.parse(args.mutated_protein)

    print(wild_protein)
    print(mutated_protein)

    # A matrix de substituicao eh obtida a partir de arquivo
    substitution_matrix = SubMatrix()
    substitution_matrix.parse(args.sub_matrix)


    '''
    O primeiro passo eh alinhar as duas proteinas testando varias penalidades de gap diferentes atraves
    encontrar o menor score possivel. Para este trabalho, simplificamos e utilizamos o numero de 
    mutacoes apenas para calcular o score

    Dessa forma, escolheremos o melhor alinhamento como o que possui menos mutacoes entre as duas
    '''
    print("Inicializando alinhamento entre {pa} e {pb}".format(
        pa=wild_protein.protid, 
        pb=mutated_protein.protid)
    )

    NW = NeedWunAlignment(wild_protein, mutated_protein, substitution_matrix)

    print("Buscando o melhor alinhamento possivel")
    NW.find_best_alignment()

    print("O melhor alinhamento possui {mut} mutacoes com penalidade {gap}".format(
        mut=NW.mutations, gap=NW.gap)
    )

    '''
    Obtido o melhor alinhamento, agora obtemos uma matrix de score contendo o numero de vezes
    que cada mutacao ocorre na familia utilizando a penalidade por gap resultante do alinhamento
    obtido
    '''
    family = Family(wild_protein, NW.SEQA)
    family.parse_protein_family_file(args.family_db)
    family.build_score(NW.gap, substitution_matrix)


    '''
    Agora identificamos no melhor alinhamento entre as duas proteinas quais sao as mutacoes mais 
    criticas baseando-se na frequencia em que elas ocorrem na familia
    '''
    bad_guys = family.score_alignment(NW.SEQB)

    '''
    Resultados sao escritos no arquivo guprotalignment.dat
    '''
    with open("gualignment_results.txt", 'w') as f:
        header = "{old:16s} {new:16s} {pos:16s} {sco:16s}\n".format(
                old="Residuo Antigo",
                new="Novo Residuo",
                pos="Posicao",
                sco="Score"
            )
        f.write(header)
        for p in bad_guys:
            f.write(
                "{old:16s} {new:16s} {pos:16d} {sco:16d}\n".format(
                    old=p[0],
                    new=p[1],
                    pos=p[2],
                    sco=p[3]
                )
            )

#
# Run the program
#
if __name__ == "__main__":
    guprotaligner()
