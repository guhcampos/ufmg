COMPILA = gfortran -Wall -ggdb
#COMPILA = ifort -g
CMSG := "compilando"
FILE := seno

.PHONY: clean cleanall doc data plot

cubicspline: main.f90 extrap_spline.o aval_splines.o
	@echo $(CMSG) $@ ;\
	$(COMPILA) main.f90 aval_splines.o extrap_spline.o -o  $@

aval_splines.o: aval_splines.f90 extrap_spline.o
	@echo $(CMSG) $@ ;\
	$(COMPILA) aval_splines.f90 -c
	
extrap_spline.o: extrap_spline.f90
	@echo $(CMSG) $@ ;\
	$(COMPILA) extrap_spline.f90 -c

seno: seno.f90
	@echo $(CMSG) $@ ;\
		$(COMPILA) seno.f90 -o $@

data: seno ./scripts/gera-dados.sh
	@echo "Gerando dados para testes" ;\
	cd ./scripts ;\
	./gera-dados.sh

plot: ./scripts/plota-graficos.sh ./scripts/gera-final.sh \
./scripts/config.plt
	@echo "Plotando graficos" ;\
	cd ./scripts ;\
	./gera-final.sh ;\
	./plota-graficos.sh

doc: ./scripts/gera-ps.sh
	@echo "Gerando doc ";\
	cd ./scripts ;\
	./gera-ps.sh

clean:
	@echo "Limpando arquivos..." ;\
	rm -f *.o *.mod cubicspline seno

cleanall:
	@echo "Limpeza geral..." ;\
	rm -f *.o *.mod cubicspline seno
	rm ./dados/*.dat
	rm ./saidas/*.out
	rm ./saidas/*.final
	rm ./samples/*.wav

run: cubicspline
	@echo "Rodando o programa:" ;\
	time ./cubicspline ./dados/$(FILE).dat ./saidas/$(FILE).out

