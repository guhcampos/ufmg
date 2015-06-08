LOC := $(shell hostname)
.PHONY: get put sync clean

clean:
	@echo "Limpando" ;\
		rm -rf *.class

ifeq ($(LOC),thor)
get:
	@echo "Baixando versoes da UFMG..." ;\
        rsync -arvuz --exclude='*.class' --exclude='.*.swp'\
                login.dcc.ufmg.br:~gucampos/algebra/. .
        
put:
	@echo "Enviando versoes locais para a UFMG..." ;\
        rsync -arvuz --exclude='*.class' --exclude='.*.swp'\
                . login.dcc.ufmg.br:~gucampos/algebra/. 
        
sync: get put
endif
