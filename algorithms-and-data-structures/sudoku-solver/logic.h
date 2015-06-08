/* 
 findNextZero

	Encontra a próxima casa vazia na tabua do sudoku e a retorna
	Em caso de nao haver casas vazias, retorna o flag 999

	int *mtx: ponteiro para a estrutura base da matriz
	int atual: inteiro correspondente a casa atual do algoritmo na tabua

*/
int findNextZero(int *mtx, int atual);

/*
 colocaNum

	int *mtx: um ponteiro para a base da matriz
	int off: o deslocamento a ser feito da base
	int cand: o numero a ser inserido

	Verifica se o numero eh ilegal, caso seja ilegal retorna um erro, caso contrario, retorna 0
*/
int colocaNum(int *mtx, int off, int cand);

/*
 solveRec

	int *mtx: um ponteiro para a base da matriz
	int off: o deslocamento a partir da base

	Responsavel pelo backtracking

	retorna 1 em caso de insucesso
	retorna 0 em caso de sucesso ou quando alcancar o fim da matriz

	Se retornar sucesso, chama recursivamente para a proxima casa
*/
int solveRec(int *matriz, int off);
