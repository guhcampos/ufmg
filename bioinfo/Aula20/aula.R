library(igraph)

arquivo = commandArgs()[6]
grafo = read.graph(arquivo, format="pajek")

print(paste("Analisando arquivo: ", arquivo, sep=""))

# Plotando o Grafo
png(filename=paste("grafo_", arquivo, ".png", sep=""), height=500, width=500, bg="white")
plot(grafo, vertex.size=10, vertex.color="lightblue", edge.color="black", vertex.label.font=1)

# Calculando Valores

vertices = vcount(grafo)
arestas = ecount(grafo)

print(paste("Numero de Nos:", vertices))
print(paste("Numero de Arestas:", arestas))

graus = degree(grafo)

# Plotando Dispersao de Graus
png(filename=paste("disp_g", arquivo, ".png", sep=""), height=500, width=500, bg="white") 
plot(c(1:vertices), graus, xlab="Vertices", ylab="Graus", main="Graus de Vertices")


# Plotando Distribuicao de Graus
dist_grau = degree.distribution(grafo, cumulative=FALSE)
png(filename=paste("dist_g_", arquivo, ".png", sep=""), height=500, width=500, bg="white")
barplot(dist_grau, main="Distribuicao de Graus", xlab="Graus", ylab="PDF", 
            names.arg=c(0:(length(dist_grau)-1)), ylim=c(0, max(dist_grau)))

# Betweenness
bt = betweenness(grafo)
bmax = max(bt)
bindex_max = which(bt == bmax)
bmaxLabel = V(grafo)[bindex_max]$id 

print("Betweenness")
#print(bt)
print(paste("Max Betweenness on Node", bmaxLabel))

# Plotando Betweenness
#print("Plotando grafico de distribuicao de betweenness")
#png(filename=paste("dist_bt_", arquivo, ".png", sep=""), height=500, width=500, bg="white")
# print("Betweenness Sitio Ativo")
# print(paste("ASN [ 10]", bt[10]))
# print(paste("LYS [ 12]", bt[12]))
# print(paste("HIS [ 95]", bt[95]))
# print(paste("GLU [165]", bt[165]))
# print(paste("GLY [171]", bt[171]))

# Closeness
cl = closeness(grafo)
cmax = max(cl)
vindex_max = which(cl == cmax)
vmaxLabel = V(grafo)[vindex_max]$id

print("Closeness")
#print(cl)
print(paste("Max Closeness on Node", vmaxLabel))

# Plotando Closeness
#print("Plotando grafico de distribuicao de closeness")
#png(filename=paste("dist_cl_", arquivo, ".png", sep=""), height=500, width=500, bg="white")

# print("Closeness Sitio Ativo")
# print(paste("ASN [ 10]", cl[10]))
# print(paste("LYS [ 12]", cl[12]))
# print(paste("HIS [ 95]", cl[95]))
# print(paste("GLU [165]", cl[165]))
# print(paste("GLY [171]", cl[171]))


