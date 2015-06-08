/*
 * random.c
 *
 *  Created on: 05/12/2009
 *      Author: Gustavo Campos
 */
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <ctime>
#include "../lib/poisson.h"
#include "../tp/callbacks.h"

int main()
{
	srand((unsigned)time(0));

	double *eventos = poissonrej(24.0,1.0/18.0,alfa);

	int i = poissonlen(eventos);

	printf("Eventos: %d\n",i);
	printf("Primeiro: %f\n",eventos[0]);
	printf("Ultimo: %f\n",eventos[i-1]);

	for (int j=0;j<i;j++) printf("t(%d) = %f\n",j,eventos[j]);
}
