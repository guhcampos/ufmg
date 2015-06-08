COMPILA = gcc -ggdb
CMSG := "compilando: "

assembler: main.c parser.o
	@echo "Compilando o programa principal...";\
		$(COMPILA) main.c parser.o -o assembler

parser.o: parser.c
	@echo $(CMSG)$@ ;\
		$(COMPILA) -c parser.c -o $@
run: assembler
	@echo "Rodando o programa:" ;\
		./assembler -vf ./programas/divisao.a

clean:
	@echo "Limpando arquivos..." ;\
		rm -rf *.o assembler *.exec
