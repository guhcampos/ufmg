#!/bin/bash

datadir="./family"
netdir="./grafos"

for arquivo in `ls $datadir`
do
    nome=`echo $(basename $arquivo) | cut -d '.' -f 1`
    echo "gerando grafo de $datadir/$arquivo em $netdir/${nome}.net"
    python ./tp3_geragrafo_gzip.py $datadir/$arquivo > $netdir/${nome}.net
done
