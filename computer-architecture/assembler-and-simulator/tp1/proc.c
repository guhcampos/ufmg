#include "inc.h"
void exec (int *memoria, regbank *regs, int verbose) {

	int pos;

	/* Faz a leitura da instrucao */
	regs->IR = memoria[regs->PC];
	regs->PC++;
	/* Faz a leitura do parametro, se houver */
	if (regs->IR != 15 && regs->IR != 16) {
		regs->PR = memoria[regs->PC];
		regs->PC++;
	}
	/* Imprime estado se solicitado */
	if(verbose) printState(regs,memoria);

	/* Interpreta e executa a instrucao */
	switch (regs->IR) {
		case LOAD:
			regs->AC = memoria[ regs->PR + regs->PC ];
		break;
		case STORE:
			memoria[ regs->PR + regs->PC ] = regs->AC;
		break;
		case ADD:
			regs->AC += memoria[ regs->PR + regs->PC ];
		break;
		case SUB:
			regs->AC -= memoria[ regs->PR + regs->PC ];
		break;
		case JMP:
			regs->PC += regs->PR;
		break;
		case JPG:
			if (regs->AC>0) regs->PC += regs->PR;
		break;
		case JPL:
			if (regs->AC<0) regs->PC += regs->PR;
		break;
		case JPE:
			if(regs->AC==0) regs->PC += regs->PR;
		break;
		case JPNE:
			if(regs->AC!=0) regs->PC += regs->PR;
		break;
		case PUSH:
			regs->SP -= 1; memoria[regs->SP] = memoria[ regs->PR + regs->PC ];
		break;
		case POP:
			memoria[ regs->PR + regs->PC ] = memoria[regs->SP]; regs->SP += 1;
		break;
		case READ:
			fprintf(stdout,"Entre com um numero: ");
			fscanf(stdin,"%d",&memoria[ regs->PR + regs->PC ]);
		break;
		case WRITE:
			pos = regs->PR+regs->PC;
			fprintf(stdout,"MEM [%d] : %d\n",pos,memoria[pos]);
		break;
		case CALL:
			regs->SP -= 1; memoria[regs->SP] = regs->PC; regs->PC += regs->PR;
		break;
		case RET:
			regs->PC = memoria[regs->SP]; regs->SP += 1;
		break;
		case HALT:
			return;
		break;
	}
	if(verbose) fprintf(stderr,">                  | PC: %3d | SP: %3d | AC: %3d\n",
		regs->PC,regs->SP,regs->AC);
	return;
}
