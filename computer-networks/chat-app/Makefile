COMPILA = gcc -ggdb

all: client daemon

daemon: daemon.o util.o transmitter.o
	@echo "Compilando "$@ ; $(COMPILA) daemon.o transmitter.o util.o -o $@

client: client.o util.o transmitter.o
	@echo "Compilando "$@ ; $(COMPILA) client.o transmitter.o util.o -o $@

daemon.o:	daemon.c defs.h inc.h
	@echo "Compilando "$@; $(COMPILA) -c daemon.c -o $@

client.o:	client.c defs.h inc.h 
	@echo "Compilando "$@; $(COMPILA) -c client.c -o $@

transmitter.o: transmitter.c transmitter.h defs.h inc.h
	@echo "Compilando "$@; $(COMPILA) -c transmitter.c -o $@

util.o: util.c util.h defs.h inc.h
	@echo "Compilando "$@; $(COMPILA) -c util.c -o $@

clean:
	@echo "Limpando arquivos...";\
	rm -rf *.o client daemon *.swp *~
