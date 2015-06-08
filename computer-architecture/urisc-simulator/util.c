#include "inc.h"

extern unsigned short int IR;
extern ctrl controle;
/*
 *  Pausa a execucao e mostra mensagem de espera
 *
 *  espera que o usuario pressione ENTER para contrinuar a execucao
 */
void pausa() {
	fprintf(stdout, "\n%35sPressione ENTER para Continuar%34s\n", VERMELHO, NORMAL);
	char x;
	fflush(stdin);
	x = fgetc(stdin);
	fflush(stdin);
}
/* 
 * Extende o sinal de uma constante
 *
 */
unsigned short int extendeSinal(unsigned short int entrada, int size) {

	unsigned short int saida;

	switch (size) {
		case 12:
			if ( entrada >> 11 ) saida = entrada | 0xF000;
			else saida = entrada;
		break;
		case 11:
			if ( entrada >> 10 ) saida = entrada | 0xF800;
			else saida = entrada;
		break;
		case 8:
			if ( entrada >> 7 ) saida = entrada | 0xFF00;
			else saida = entrada;
		break;
	}
	return saida;
}
