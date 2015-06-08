#define MAX_LINE_SIZE 		80	// tamanho maximo de linha de um programa
#define MAX_LABEL_SIZE 		16	// tamanho maximo de um label do assembly
#define MAX_OPERATOR_SIZE 	16	// tamanho maximo do mnemonico de um operador
#define MAX_OPERAND_SIZE 	16	// tamanho maximo em caracteres de um operando
#define MAX_NUM_INSTS 		19	// numero maximo de instrucoes da linguagem
#define PROGRAM_SIZE		256	// tamanho inicial do programa
#define SYMBOL_SIZE			32	// tamanho inicial da tabela de simbolos

typedef char* opcode;

typedef struct {
	char label[MAX_LABEL_SIZE];
	int  ilc;
} symbol;

typedef struct {
	char inst[MAX_OPERATOR_SIZE];
	char opnd[MAX_OPERAND_SIZE];
} inst;

/* Gera a tabela com todos os opcodes */
void geraTabelaOpcodes(opcode *tabela);
