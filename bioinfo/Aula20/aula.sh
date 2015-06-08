#!/bin/bash

for arquivo in `ls *.net`
do
    echo "Analisando $arquivo"
    #Rscript ./aula.R $arquivo > ${arquivo}.output
    Rscript ./aula.R $arquivo
done
