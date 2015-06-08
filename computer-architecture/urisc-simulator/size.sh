#!/bin/bash
lines=0
clear
for x in *.c *.h Makefile size
do
	lines=$(( $lines + `wc -l $x | cut -d " " -f 1` ))
done
echo ""
echo "Voce ja escreveu $lines linhas de codigo"	
echo ""
