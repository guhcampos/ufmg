#!/bin/bash

test0="saida0 java Crypt texto0.txt > saida0"
test1="saida1 java Crypt texto1.txt > saida1"
test2="saida2 java Crypt texto2.txt > saida2"

#Limpa compilacoes antigas
if [ -e RSA.class ]
then
	rm RSA.class
fi
if [ -e Crypt.class ]
then
	rm Crypt.class
fi
if [ -e Primos.class ]
then
	rm Primos.class
fi
#Limpa a saida
if [ -e saida0 ]
then
	rm saida0 saida1 saida2
	touch saida0 saida1 saida2
fi
#Compila o programa
javac Primos.java
javac RSA.java
javac Crypt.java
#Roda testes em ordem de dificuldade
/usr/bin/time -a -f"\t%U user, \t%S system, \t%e real" -o saida0 java Crypt texto0.txt > saida0
/usr/bin/time -a -f"\t%U user, \t%S system, \t%e real" -o saida1 java Crypt texto1.txt > saida1
/usr/bin/time -a -f"\t%U user, \t%S system, \t%e real" -o saida2 java Crypt texto2.txt > saida2
