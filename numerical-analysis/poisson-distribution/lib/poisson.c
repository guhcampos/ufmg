/*
 * poisson.c
 *
 *  Created on: 05/12/2009
 *      Author: Gustavo Campos
 */
#include <math.h>
#include <cstdlib>
#include "grandom.h"

#include <cstdio>

double *poissonrej(double t, double max, double(*alfa)(double t))
{
	if (max == 0.0) {
		printf("O limite superior de alfa nao pode ser zero.");
		exit(1);
	}

	// Tempo inicia em 0
	double tempo = 0.0;

	// Contador de eventos
	int counter = 0;

	// Variavel de controle da rejeicao
	double param = 0.0;

	// Alocamos espaco para 1 evento
	double * eventos = (double *) malloc(sizeof(double));
	eventos[0] = 0;

	do {

		// Geramos um ponto aleatorio limitado superiormente por max
		tempo -= log(grandom()) / max;

		// Se extrapolarmos o tempo maximo t o processo para prematuramente
		if (tempo > t) break;

		// Geramos o parametro da rejeicao
		param = grandom();

		// Aplicamos a rejeicao
		if (param <= (alfa(tempo) / max)) {
			eventos[counter] = tempo;
			counter++;
			// Ampliamos o tamanho do vetor
			eventos = (double *) realloc(eventos,counter * sizeof(double));
		}
	} while (tempo<t);

	/*
	 *   Preenchemos o ultimo espaco da array com um valor "sentinela" para deli
	 * mitar o fim do array e assim poder computar seu tamanho depois
	 *
	 *   Esse artificio especifico possibilita que usemos a funcao poissonlen para
	 * saber o tamanho do array
	 */
	eventos[counter+1] = 0;

	return eventos;
}

int poissonlen(double *eventos)
{
	int counter = 0;

	if(eventos[0]==0) return 0;

	while (1) {
		if(eventos[counter]!=0) counter++;
		else break;

	}

	return counter;
}
