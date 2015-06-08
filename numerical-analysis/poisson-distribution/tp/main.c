/*
 * main.c
 *
 *  Created on: 05/12/2009
 *      Author: Gustavo Campos
 */

#include <cstdlib>
#include <cstdiio>
#include "../lib/grandom.h"
#include "../lib/poisson.h"
#include "callbacks.h"

int main()
{
	// Inicializamos o gerador de numeros aleatorios
	srand((unsigned)time(0));

	// Tempo maximo do dia
	const double t_dia = 24.0;

	// Tempo maximo na fila
	const double t_fila = 10.0;

	// Limite maximo de alfa
	const double max_dia = 1.0/18.0;

	// Limite maximo de beta
	const double max_fila = 0.2;

	double * chegadas = poissonrej(t_dia, max_dia, alfa);
}
