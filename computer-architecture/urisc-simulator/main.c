#include "inc.h"
	
	unsigned short int regs[REGS];
	unsigned short int mem[MEM];
	unsigned short int PC;
	unsigned short int IR;
	flag flags;
	ctrl controle;

int main (int argc, char **argv) {

	PC = 0;

	/* variaveis de execucao */
	
	int pause 		= 0;
	int screen		= 0;
	int dump		= 0;
	int opcoes		= 0;
	int last		= 0;
	int startdump	= 0;
	int dumpwords	= 0;

	FILE *arqentrada;

	/* Tratamento de Argumentos */

	if (argc <= 1 || argc > 8) {
		printErroDeChamada();
		exit(1);
	}

	while((opcoes = getopt(argc, argv, "i:d:b:sp")) != -1) switch (opcoes) {
		case 'i':
			/* abre o arquivo de entrada para leitura */
			if(( arqentrada = fopen(optarg, "r")) == NULL) {
				fprintf(stderr, "\nNao e possivel abrir %s para leitura\n",optarg);
				exit(1);
			}
		break;
		case 'd':
			/* seta o dump a partir do inicio */
			dump = 1;
			startdump = strtol(optarg, NULL, 16);
		break;
		case 'b':
			/* seta o inicio do dump */
			dumpwords = strtol(optarg, NULL, 10);
		case 's':
			/* seta para imprimir os estados */
			screen = 1;
		break;
		case 'p':
			/* seta para pauser a impressao */
			pause = 1;
		break;
	}

	/* Leitura do Arquivo de Instrucoes */
	fprintf(stdout, "\nLendo arquivo de instrucoes...\n\n");
	last =  readEntrada(arqentrada);

	/* Execucao */

	PC = 0;

	while (IR != 0x2FFF) {

		/* Instruction Fetch */	
		IR = mem[PC];
		PC++;

		executeMemory();

		/* imprime o estado atual caso necessario */

		if (screen) printState(); 
		if (pause)	pausa();
	
	}
	/* Faz o dump da memoria */

	if (dump) {
		fprintf(stdout, "\nRealizando o DUMP de memoria...");
		printMemory(startdump, dumpwords);		
	}

	return 0;
}
