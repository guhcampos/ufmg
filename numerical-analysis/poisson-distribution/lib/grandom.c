/*
 * grandom.c
 *
 *  Created on: 06/12/2009
 *      Author: Gustavo Campos
 */

#include <cstdlib>
#include <ctime>

double grandom()
{
	return 1.0 * rand() / (RAND_MAX);
}
