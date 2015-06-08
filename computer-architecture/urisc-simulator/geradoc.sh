#!/bin/bash

# Gera os ps do codigo

for x in *.c Makefile defs.h inc.h
do
	a2ps --columns=1 -R -l 88 -T 4 -f 8 -E -o "doc/$x.ps" $x
done

# Compila o latex


