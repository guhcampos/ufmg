COMPILA = gcc -ggdb -Wall -ansi

run: sudoku
	./sudoku < entradas/entrada > saida
	./sudoku < entradas/entrada2 > saida2
	./sudoku < entradas/entrada3 > saida3
	./sudoku < entradas/entrada4 > saida4
sudoku: main.o logic.o io.o
	$(COMPILA) main.o logic.o io.o -o sudoku

main.o: main.c logic.o io.o
	$(COMPILA) -c main.c -o main.o

logic.o: logic.c logic.h io.o
	$(COMPILA) -c logic.c -o logic.o

io.o: io.c io.h
	$(COMPILA) -c io.c -o io.o

clean:
	rm -rf *.o saida* sudoku 
