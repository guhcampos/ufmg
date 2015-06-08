#include "inc.h"

extern unsigned short int mem[MEM];
extern unsigned short int PC;

int readEntrada(FILE *entrada) {
	
	char *tmp;

	tmp = malloc ( WORD * sizeof(char) );

	while (fgets(tmp, WORD, entrada)) {
		/* verifica a existencia da word 'adress' */
		if (*(tmp+3) == 'r') {
			/* seta o PC para o valor no adress */
			sscanf(tmp, "%*s %hx", &PC);
		} else {
			/* insere valor na memoria e incrementa o PC*/
			sscanf(tmp, "%hx", &mem[PC]);
			PC++;
		}		
	}
	return PC;
}
