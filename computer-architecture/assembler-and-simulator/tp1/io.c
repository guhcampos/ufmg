#include "inc.h"

/* printError
 * 	imprime instrucoes de uso
 */
void printError() {
	fprintf(stderr,"\nNao foi possivel carregar o programa");
}
/* loadProgram
 * carrega na memoria o programa lendo de um arquivo de texto
 */
void loadProgram(FILE *programa, int *memoria, int start, int verbose) {
	int block = start;
	while (fscanf(programa,"%d",&memoria[block])!=EOF) {
		if(verbose)	fprintf(stderr,"\nLoaded %02d in %03d",memoria[block],block);
		block++;
	}
	if(verbose) fprintf(stderr,"\n");
}
/* printState
 * imprime a instrucao e o estado atual da maquina virtual */
void printState(regbank *regs, int *memoria) {
	char *name = malloc (6 * sizeof(char));
	
	switch (regs->IR) {
		case 1: 
			name = "LOAD";	
		break;
		case 2:
			name = "STORE";	
		break;
		case 3:
			name = "ADD";	
		break;
		case 4:
			name = "SUB";	
		break;
		case 5:
			name = "JMP";	
		break;
		case 6:
			name = "JPG";	
		break;
		case 7:
			name = "JPL";	
		break;
		case 8:
			name = "JPE";	
		break;
		case 9:
			name = "JPNE";	
		break;
		case 10:
			name = "PUSH";	
		break;
		case 11:
			name = "POP";	
		break;
		case 12:
			name = "READ";	
		break;
		case 13:
			name = "WRITE";	
		break;
		case 14:
			name = "CALL";	
		break;
		case 15:
			name = "RET";	
		break;
		case 16:
			name = "HALT";	
		break;
	}
	fprintf(stderr,"> Inst: %6s %3d | PC: %3d | SP: %3d | AC: %3d\n",
		name,regs->PR,regs->PC,regs->SP,regs->AC);
}		
