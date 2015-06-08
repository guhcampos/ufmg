from sys import argv
from math import sqrt

#Faz a leitura do arquivo PDB, armazenando as informacoes
#referentes aos carbonos alfa dos aminoacidos pertencentes
#a proteina
def preparaDados(pdf_file):

	residuos = dict()

	f = open(pdf_file,"r")
	residuos_id = 0
	for line in f:
		tipo = line[0:6].strip()
		if (tipo == "ATOM"):
			id_num = line[7:12].strip()
			c = line[12:15].strip()
			atm = line[16:20].strip()
			cadeia = line[21:22].strip()
			id_residuo = line[23:26].strip()
			x = line[30:38].strip()
			y = line[38:46].strip()
			z = line[46:54].strip()
			if (c == "CA" and cadeia == "A"):
				residuos[residuos_id] = (tipo, int(id_num), c,
						atm, float(x), float(y), float(z),
						int(id_residuo))
				residuos_id += 1

	f.close()

	return residuos

#Calcula a matriz de distancias de cada atomo a todos os outros
def calculaDistancias(residuos):
	num_residuos = len(residuos)

	matriz_dist = list()

	#inicializa a matriz
	for i in range(num_residuos):
		matriz_dist.append(list())

	for i in range(num_residuos):
		for j in range(num_residuos):
			matriz_dist[i].append(0.0)

	#calcula as distancias
	for i in range(num_residuos):
		for j in range(num_residuos):
			acum = 0.0
			vx = residuos[i][4] - residuos[j][4]
			vy = residuos[i][5] - residuos[j][5]
			vz = residuos[i][6] - residuos[j][6]
			acum += pow(vx,2)
			acum += pow(vy,2)
			acum += pow(vz,2)
			dist = sqrt(acum)
			matriz_dist[i][j] = dist

	return matriz_dist

#Monta a matriz de contatos dado
def verificaContatos(matriz_dist, limiar_dist, residuos):

	num_residuos = len(residuos)

	matriz = list()
	for i in range(num_residuos):
		matriz.append(list())

	for i in range(num_residuos):
		for j in range(num_residuos):
			if (i != j):
				if (matriz_dist[i][j] <= limiar_dist):
					matriz[i].append(1)
				else:
					matriz[i].append(0)
			else:
				matriz[i].append(0)

	return matriz

#cria arquivo .net
def criaArquivoNet(matriz_dist, matriz_contatos, residuos):

	num_residuos = len(residuos)

	print "*Vertices " + str(num_residuos)
	for i in range(num_residuos):
		line = str(i+1) + "\t" + "\"" + residuos[i][3] + "("
		line += str(residuos[i][7]) + ")\""
		print line

	print "*Edges"
	for i in range(num_residuos):
		for j in range(num_residuos):
			if (matriz_contatos[i][j] == 1):
				if (i < j):
					line = str(i+1) + "\t" + str(j+1)
					line += "\t"
					print line + "%.3f" % (matriz_dist[i][j])


residuos = preparaDados(argv[1])
matriz_dist = calculaDistancias(residuos)
matriz_contatos = verificaContatos(matriz_dist, 7, residuos)
criaArquivoNet(matriz_dist, matriz_contatos, residuos)
