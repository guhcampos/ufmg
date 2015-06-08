#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "io.h"
#include "logic.h"

int contador;

/*

 Para assegurar portabilidade todos os valores sao definidos com
	base no numero de linhas/colunas definido em uma constante

*/

const int ROWS = 9;
const int TABS = 81;
const int MINI = 3;

int main (void) {


	int start;
	int *matriz = malloc (TABS * sizeof (int));
	contador = 0;

	entrada(matriz);

	/* Encontra a primeira casa vazia  */
	
	start = findNextZero(matriz, 0);

	if (999==start) {
		printf ("\nSudoku ja completo\n");
		return 0;
	}

	/* Chama recursivamente o metodo q resolve o jogo  */
	if (!solveRec(matriz, start)){
		saidaSemFormato(matriz);
		printf ("\n\n%d tentativas\n", contador);
		return 0;
	}
	printf ("\n\n%d tentativas\n", contador);
	printf ("\nSolucao Impossivel\n");
	return 0;
}
