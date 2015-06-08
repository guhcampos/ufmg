SHELL = /bin/sh
VPATH = library programs headers

COMPILA=g++ -g -Wall -Werror
CMSG = "compilando: "

USER=guhcampos
PWD=h6j6k3q9

.PHONY: commit update clean all doc

all: straits car

straits: server.o entidade.o stgui.o intel.o comunica.o citymap.o
	@echo $(CMSG)$@;\
	$(COMPILA) $^ -o $@

car: entidade.o car.o
	@echo $(CMSG)$@;\
	$(COMPILA) $^ -o $@

server.o: server.cpp
	@echo $(CMSG)$@;\
	$(COMPILA) -c $< -o $@

car.o: car.cpp
	@echo $(CMSG)$@;\
	$(COMPILA) -c $< -o $@

stgui.o: stgui.cpp
	@echo $(CMSG)$@;\
	$(COMPILA) -c $< -o $@

citymap.o: citymap.cpp
	@echo $(CMSG)$@;\
	$(COMPILA) -c $< -o $@

entidade.o: entidade.cpp
	@echo $(CMSG)$@;\
	$(COMPILA) -c $< -o $@

intel.o: intel.cpp
	@echo $(CMSG)$@;\
	$(COMPILA) -c $< -o $@

comunica.o: comunica.cpp
	@echo $(CMSG)$@;\
	$(COMPILA) -c $< -o $@

doc:
	doxygen

clean:
	@rm -vf *.o straits car

commit:
	@echo "Commiting:";\
	svn commit --username $(USER) --password $(PWD)

update:
	@echo "Updating:";\
	svn update --username $(USER) --password $(PWD)
