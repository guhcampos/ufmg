/*
 * callbacks.c
 *
 *  Created on: 05/12/2009
 *      Author: Gustavo Campos
 */

#include <cstdio>
#include <cstdlib>
#include <float.h>

/*
 *   Quando alfa = 0, o algoritmo de poisson divide por 0, o que nao pode
 * ocorrer. Por isso introduzimos uma constante positiva que representa uma
 * probabilidade arbitrariamente pequena, mas ainda nao zero, para evitar
 * essa divisao por 0.
 */
const double LIM_MIN = 0.1;


/*
 * f(x) | 0 < x < 6 = x/108
 * f(x) | 6 <= x <= 18 = 18
 * f(x) | 18 < x < 24 = -3x + 72
 */
double alfa(double t)
{

	/*
	 * Para evitar a divisao por 0
	 */
	if ( t==0 || t==24) t = LIM_MIN;

	/*
	 * f(x) | 0 < x < 6 = 3x
	 */
	if( (0<=t) && (t<6)) return t / 108.0;
	/*
	 * 6 <= x <= 18 = 18
	 */
	else if ( (6<=t) && (t<=18) ) return 1.0/18.0;
	/*
	 * 18 < x < 24 = -3x + 72
	 */
	else if ( (18<t) && (t<=24) ) return (-1.0 / 432.0) * t + (24.0/108.0);

	else {
		printf("Valor %f fora do dominio de f(t)\n",t);
		return(0);
	}
}

/*
 * f(x) = -1/50 * x + 1/5
 */
double beta(double t)
{
	/*
	 * Para evitar a divisao por 0
	 */
	if ( t==10) return LIM_MIN;

	if ( (0<=t) && (t<=10) ) return -0.02 * t + 0.2;

	else {
		printf("Valor fora do dominio de f(t)\n");
		exit(1);
	}
}
