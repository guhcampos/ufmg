#ifndef DEFS_H
#define DEFS_H 1
#endif

#define MAX_LINE_SIZE 		80	// tamanho maximo de linha de um programa
#define MAX_LABEL_SIZE 		16	// tamanho maximo de um label do assembly
#define MAX_NUM_INSTS 		19	// numero maximo de instrucoes da linguagem
#define PROGRAM_SIZE		256	// tamanho inicial do programa
#define SYMBOL_SIZE			32	// tamanho inicial da tabela de simbolos
#define MACRO_SIZE			32	// tamanho inicial de uma macro

typedef char inst[MAX_LINE_SIZE];

typedef struct {
	char label[MAX_LABEL_SIZE];
	char param[MAX_LABEL_SIZE];
	char codigo[MACRO_SIZE][MAX_LINE_SIZE];
	int size;
} macro;
