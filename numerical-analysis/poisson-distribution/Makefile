SHELL = /bin/sh
VPATH = lib tests tp

COMPILA=g++ -g -Wall -Werror
CMSG = "compilando: "

.PHONY: clean all doc

all: testes

testes: test_random test_poisson

test_poisson: t_poisson.c grandom.o poisson.o callbacks.o
	@echo $(CMSG)$@;\
	$(COMPILA) $^ -o $@

test_random: t_random.c grandom.o callbacks.o
	@echo $(CMSG)$@;\
	$(COMPILA) $^ -o $@

callbacks.o: callbacks.c
	@echo $(CMSG)$@;\
	$(COMPILA) $< -c -o $@

poisson.o: poisson.c grandom.o
	@echo $(CMSG)$@;\
	$(COMPILA) $^ -c -lm -o $@

grandom.o: grandom.c
	@echo $(CMSG)$@;\
	$(COMPILA) -c $< -o $@
	
clean:
	@rm -vf *.o