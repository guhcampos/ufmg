#include "inc.h"

int main (int argc, char **argv) {
	
	int *memoria = malloc (1000 * sizeof(int));
	FILE *entrada;

	int verbose	= 0;
	int start	= 0;
	int opcoes;

	regbank regs;

	/* Tratamento de Argumentos */

	if(argc != 9 && argc != 10) {
		fprintf(stderr,"\nSintaxe: vmach -p PC -s SP -i START -f entrada [-v]\n");
		exit(1);
	}

	while((opcoes = getopt(argc,argv,"p:s:i:f:v")) != -1) switch (opcoes) {
		case 'p':
			sscanf(optarg,"%d",&regs.PC);
		break;
		case 's':
			sscanf(optarg,"%d",&regs.SP);
		break;
		case 'i':
			sscanf(optarg,"%d",&start);
		break;
		case 'f':
			entrada = fopen(optarg,"r");
			if(entrada==NULL) {
				fprintf(stderr,"\nImpossivel abrir %s para leitura",optarg);
				exit(1);
			}
		break;
		case 'v':
			verbose = 1;
		break;
	}

	regs.AC = 0;
	regs.IR = 0;
	regs.PR = 0;

	/* Leitura do Programa */

	fprintf(stdout,"\n Carregando programa na memoria...\n\n");
	loadProgram(entrada,memoria,start,verbose);

	/* Execucao */
	while(regs.IR != HALT) exec(memoria,&regs,verbose);

	free(memoria);
	return 0;
}
