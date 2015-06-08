/*
 * random.c
 *
 *  Created on: 05/12/2009
 *      Author: Gustavo Campos
 */
#include <math.h>
#include <cstdlib>
#include <cstdio>
#include <ctime>
#include "../lib/grandom.h"
#include "../tp/callbacks.h"

int main()
{
	srand((unsigned)time(0));

	int i;
	double b;
	double t;

	for (i=0;i<24;i++) {
		b = grandom();
		t = alfa(i);
		printf("rand - %f\n", b);
		printf("log  - %f\n", -1.0 * log(b));
		printf("alfa(%d) - %f\n",i, t);
		printf("tudo - %f\n\n", b / t);
	}
}
