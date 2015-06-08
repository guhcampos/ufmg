CFLAGS=-Wall -g

COMMONOBJS=simplesockets.o 

.PHONY: clean

all: cliente

cliente: $(COMMONOBJS) cliente.c
	gcc $(CFLAGS) -o cliente $(COMMONOBJS) cliente.c

simplesockets.o: simplesockets.h simplesockets.c

kbd-srv: $(COMMONOBJS) kbd-srv.c
	gcc $(CFLAGS) -o kbd-srv $(COMMONOBJS) kbd-srv.c

scr-srv: $(COMMONOBJS) scr-srv.c
	gcc $(CFLAGS) -o scr-srv $(COMMONOBJS) scr-srv.c

clean:
	rm -f cliente *.o
