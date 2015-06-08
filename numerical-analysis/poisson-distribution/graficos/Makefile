executa: compila
  gdb run

compila: main.o query.o interface.o
  gcc -g -Wall -pg -Werror -Lfence main.o query.o interface.o -o run

main.o: main.o
  gcc -g -pg -c main.c

query.o: query.o
  gcc -g -pg -c query.c

interface.o: interface.o
  gcc -g -pg -c interface.c

clean:
  rm *.o

#testa:
#  valgrind -v --leak-check=full --show-reachable=yes ./Exec
