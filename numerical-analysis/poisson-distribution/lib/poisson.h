/*
 * poisson.h
 *
 *  Biblioteca para a geracao de variaveis aleatorias de poisson
 *
 *  Created on: 05/12/2009
 *      Author: Gustavo Campos
 */

#ifndef POISSON_H_
#define POISSON_H_


#endif /* POISSON_H_ */

/*
 *   Gera uma variavel de poisson a partir dos parametros. Por usar um ponteiro
 * de funcao para executar o callback, pode ser utilizada para calcular vairave-
 * is aleatorias de poisson homogeneas ou heterogeneas, basta criar funcoes de
 * callback diferentes e passa-las como parametro
 *
 * double t - limite maximo do intervalo de tempo
 * double(*alfa)(double t) - uma funcao que retorne o valor de alfa para t
 *
 *   Retorna um ponteiro para um vetor de eventos onde a quantidade de eventos
 * ocorrida e dada pelo tamanho do vetor e os tempos de ocorrencia ocupam os
 * espacos deste
 *
 */
double *poisson(double t, double(*alfa)(double t) );
double *poissonrej(double t, double max, double(*alfa)(double t));
int poissonlen(double *eventos);
