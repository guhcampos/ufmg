#include <stdio.h>

extern int contador;

extern const int ROWS;
extern int TABS;
extern int MINI;

int findNextZero(int *mtx, int atual) {
	int k;
	for (k=atual;k<TABS;k++) if (0==*(mtx+k)) return k;
	return 999;
}

int colocaNum(int *mtx, int off, int cand) {
	int i,j;

	int q, r, ll, cc;

	int line, colm, quad;

	q = off / ROWS; /* indice das linhas */
	r = off % ROWS; /* indice das colunas */

	ll = q/MINI; /* indice da linha do quadrante */
	cc = r/MINI; /* indice da coluna do quadrante */

	/* Abaixo o algoritmo varre a tabela em busca de numeros repetidos */

	/* Linhas e Colunas */
	for(i=0;i<ROWS;i++) {
		line = q*ROWS+i;
		colm = i*ROWS+r;
		if ( *(mtx+line)==cand || *(mtx+colm)==cand ) {
			return 1;
		}
	}
	/* Quadrantex 3x3 */

	for (i=0;i<MINI;i++) {
		for(j=0;j<MINI;j++) {
			/* formula q gera o quadrante */
			quad = ROWS*(i+(ll*MINI))+(MINI*cc)+j; 
			if(*(mtx+quad)==cand) return 1; 
		}
	}

	/* Se todos os testes estao ok, retorna 0 */
	return 0;
}
int solveRec(int *matriz, int offset) {

	int i, next;

	/* Tenta colocar um numero */

	for (i=1;i<=9;i++) {
		if (0==colocaNum(matriz, offset, i)) {

			*(matriz+offset)=i;
			contador++;
			
			/* Descobre a proxima casa nula */
			next = findNextZero(matriz, offset);
			/* Nao ha mais casas a testar */
			if (999==next) return 0;
			/* Continua para as proximas casas */
			else if(0==solveRec(matriz, next)) return 0;
			/* Em caso de erro, retorna e volta, fazendo backtracking */
			else *(matriz+offset)=0;
		}
	} return 1;
}
