COMPILA = gcc -Wall -ggdb
CMSG := "compilando: "

vmach: io.o proc.o main.o
	@echo "Compilando o programa principal...";\
		$(COMPILA) io.o proc.o main.o -o vmach 
	
main.o: main.c inc.h defs.h
	@echo $(CMSG)$@ ;\
		$(COMPILA) -c main.c -o main.o

io.o: io.c io.h inc.h defs.h
	@echo $(CMSG)$@ ;\
		$(COMPILA) -c io.c -o io.o

proc.o: proc.c proc.h inc.h defs.h
	@echo $(CMSG)$@ ;\
		$(COMPILA) -c proc.c -o proc.o

run: vmach entrada-exemplo 
	@echo "Rodando o programa:" ;\
		./vmach -p 0 -s 500 -i 0 -f divisao.exec

clean:
	@echo "Limpando arquivos...:"  ;\
		rm -rf *.o vmach
