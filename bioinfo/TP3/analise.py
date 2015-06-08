#!/usr/bin/env python
import csv
import igraph
import os
import textwrap
from Bio import pairwise2
from Bio.SubsMat import MatrixInfo as matrizes
from matplotlib import pyplot

grafos_dir = "./grafos"

selvagem = "".join([
    "MARTFFVGGNFKLNGSKQSIKEIVERLNTASIPENVEVVICPPATYLDYSVSLV",
    "KKPQVTVGAQNAYLKASGAFTGENSVDQIKDVGAKWVILGHSERRSYFHED",
    "DKFIADKTKFALGQGVGVILCIGETLEEKKAGKTLDVVERQLNAVLEEVKDW",
    "TNVVVAYEPVWAIGTGLAATPEDAQDIHASIRKFLASKLGDKAASELRILYGG",
    "SANGSNAVTFKDKADVDGFLVGGASLKPEFVDIINSRN"
])

mutada = "".join([
    "MARTPFVGGNWKMNGTKAEAKELVEALKAKLPDDVEVVVAPPAVYLDTAREAL",
    "KGSKIKVAAQNCYKEAKGAFTGEISPEMLKDLGADYVILGHSERRHYFGETDELV",
    "AKKVAHALEHGLKVIACIGETLEEREAGKTEEVVFRQTKALLAGLGDEWKNVVIA",
    "YEPVWAIGTGKTATPEQAQEVHAFIRKWLAENVSAEVAESVRILYGGSVKPANAK",
    "ELAAQPDIDGFLVGGASLKPEFLDIINSRN"
])

fasta_map = {
    "A": "ALA",
    #"B": "",
    "C": "CYS",
    "D": "ASP",
    "E": "GLU",
    "F": "PHE",
    "G": "GLY",
    "H": "HIS",
    "I": "ILE",
    #"J": "",
    "K": "LYS",
    "L": "LEU",
    "M": "MET",
    "N": "ASN",
    #"O": "",
    "P": "PRO",
    "Q": "GLN",
    "R": "ARG",
    "S": "SER",
    "T": "THR",
    #"U": "",
    "V": "VAL",
    "W": "TRP",
    #"X": "",
    "Y": "TYR",
    #"Z": ""
}


def mutacoes():
    """
    Retorna uma lista de mutacoes entre as duas proteinas acima no formato:
    [
        (X, Y),
        (X, Y),
        .
        .
        .
    ]

    Onde X eh o indice do aminoacido modificado e Y eh a abreviatura PDB
    """

    print("Calculando mutacoes entre dTIM e 2YPI")

    # Configuracoes do alinhamento
    matriz = matrizes.pam250
    gap_novo = -10
    gap_continuo = -0.5

    # Alinhamento global usando algoritmo pairwise2
    alinhamento = pairwise2.align.globalds(selvagem, mutada, matriz, gap_novo, gap_continuo)

    print("Alinhamento obtido:")


    seqa = textwrap.wrap(alinhamento[0][0], 80, break_on_hyphens=False)
    seqb = textwrap.wrap(alinhamento[0][1], 80, break_on_hyphens=False)
    ret = ""
    for linea, lineb in zip(seqa, seqb):
        ret += "A: {}\n".format(linea)
        ret += "B: {}\n".format(lineb)

    print(ret)

    # Retornamos todos os residuos da 2YPI que sao diferentes na dTIM, junto com seu indice
    i = 1
    for residuo_selvagem, residuo_mutado in zip(alinhamento[0][0], alinhamento[0][1]):

        if residuo_selvagem != residuo_mutado and residuo_selvagem != "-":
            yield (i, fasta_map[residuo_selvagem])

        i += 1



def estatisticas(mutacoes):

    grafos = []

    # Convertendo as mutacoes para o formato dos ID's do grafo
    mutacoes_ids = [(r[0], "{res}({id})".format(res=r[1], id=r[0])) for r in mutacoes]

    print("Foram encontradas {i} mutacoes".format(i=len(mutacoes_ids)))

    print("Carregando grafos")

    # Lemos todos os grafos gerados e armazenamos em lista
    for arquivo in [ f for f in os.listdir(grafos_dir) if f.endswith(".net") ]:

        grafo = igraph.Graph.Read_Pajek(grafos_dir+"/"+arquivo)
        
        # Se der erro reportamos e seguimos em frente        
        if len(grafo.vs) is 0:
            print("ERRO carregando grafo {file}".format(file=arquivo))
            continue 

        # Se carregou o grafo corretamente prosseguimos com a analise
        else:
            grafos.append(grafo)
            
    for grafo in grafos:

        #print(grafo.summary())

        closeness = grafo.closeness()
        betweenness = grafo.betweenness()

        # Para cada mutacao procuramos no grafo se o vertice daquela posicao corresponde 
        # ao mesmo residuo
        for mutacao in mutacoes_ids:

            # Caso os residuos coincidam:
            #
            # Obs.: o -1 eh necessario porque os vertices sao indexados a partir de 0
            #
            try:
                if grafo.vs[mutacao[0]-1]["id"] == mutacao[1]:

                    print("Encontrada mutacao coincidente {mut} X {gra}".format(
                        mut=mutacao[1], 
                        gra=grafo.vs[mutacao[0]-1]["id"])
                    )

                    # Vamos calcular suas propriedades e retorna-lo
                    yield (
                        grafo.vs[mutacao[0]-1]["id"],
                        grafo.degree(mutacao[0]-1),
                        closeness[mutacao[0]-1],
                        betweenness[mutacao[0]-1]
                    )
            except:
                # se o grafo tiver menos vertices, que estamos tentando indexar, pula
                continue

def acumula_resultados(stats):
    """
    Cria um dicionario de resultados no formato
    {
        "XXX(YY) : [
            (G1, CL1, BT1),
            (G2, CL2, BT2),
            ...
            ]
        "XXX(YY) : [
            ...
        ]
        ...
    }
    Onde XXX(YY) eh o id do nodo ex: PHY[12] e GN, CLN, BTN sao os valores para grau, closeness
    e betweenness calculados para cada vez que esse residuo aparece na familia
    """
    residuos = {}

    for stat in stats:

        # Se nosso dicionario ainda nao tem uma key para este residuo, criamos a key como lista
        if stat[0] not in residuos:
            residuos[stat[0]] = list()
            residuos[stat[0]].append(stat[1:])

        # Se ja existe uma key, appendamos as estatisticas na lista de ocorrencias
        else:
            residuos[stat[0]].append(stat[1:])

    return residuos


def calcula_medias(resultados):

    medias = {}

    for residuo in resultados:

        num_resultados = len(resultados[residuo])

        # Se so tem uma tupla de resultados, nao precisa calcular media
        if num_resultados == 1:
            medias[residuo] = resultados[residuo][0]

        else:
            medias[residuo] = (
                float(sum([ grau[0] for grau in resultados[residuo] ])) / float(num_resultados),
                float(sum([ closeness[1] for closeness in resultados[residuo] ])) / float(num_resultados),
                float(sum([ betweenness[2] for betweenness in resultados[residuo] ])) / float(num_resultados),
            )

    return medias

def plot(medias):

    print("Ao todo, {i} residuos foram selecionados para analise".format(i=len(medias.keys())))

    try:
        grau_file = open("resultados_data_grau.csv", "wb")
        bt_file = open("resultados_data_btns.csv", "wb")
        cl_file = open("resultados_data_clns.csv", "wb")

        grau_writer = csv.writer(grau_file)
        bt_writer = csv.writer(bt_file)
        cl_writer = csv.writer(cl_file)

    except:
        raise

    for residuo in medias.keys():

        grau_writer.writerow([residuo, medias[residuo][0]])
        bt_writer.writerow([residuo, medias[residuo][2]])
        cl_writer.writerow([residuo, medias[residuo][1]])

def main():
 
    muts = mutacoes()
    stats = estatisticas(muts)
    resultados = acumula_resultados(stats)
    medias = calcula_medias(resultados)

    # for media in medias:
    #     print("{:8s}: GR: {:6.6f}, CL: {:6.6f}, BT: {:6.6f}".format(
    #         media, 
    #         medias[media][0],
    #         medias[media][1],
    #         medias[media][2]
    #     ))

    plot(medias)


if __name__ == "__main__":
    main()
