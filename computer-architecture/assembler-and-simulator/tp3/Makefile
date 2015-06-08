COMPILA = gcc -ggdb -Wall
CMSG := "compilando: "
FILE=macro

macroser: main.c parser.o
	@echo "Compilando o programa principal...";\
		$(COMPILA) main.c parser.o -o macroser

parser.o: parser.c
	@echo $(CMSG)$@ ;\
		$(COMPILA) -c parser.c -o $@
run: macroser
	echo "Rodando o expansor de macros:" ;\
	./macroser -i ./programas/$(FILE).asm -o ./programas/$(FILE).a;\
	more ./programas/$(FILE).a


clean:
	@echo "Limpando arquivos..." ;\
		rm -rf *.o macroser
