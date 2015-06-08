SHELL 	= /bin/bash
COMPILA ?= g++ -g -Wall -Werror
COMPMSG ?= "Compilando: "

.PHONY: clean

all: servidor

servidor: server.cpp messages.o chatcl.o nasocket.o
	@echo -e $(COMPMSG) $@ $(MSGEND) ;\
	$(COMPILA) $^ -o $@

chatcl.o: chatcl.cpp chatcl.h
	@echo -e $(COMPMSG) $@ $(MSGEND) ;\
	$(COMPILA) -c $< -o $@

nasocket.o: nasocket.cpp nasocket.h
	@echo -e $(COMPMSG) $@ $(MSGEND) ;\
	$(COMPILA) -c $< -o $@

messages.o: messages.cpp messages.h
	@echo -e $(COMPMSG) $@ $(MSGEND) ;\
	$(COMPILA) -c $< -o $@

clean:
	rm -f servidor *.o
