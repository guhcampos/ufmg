typedef struct {
	int PC;	/* Program Counter */
	int SP;	/* Stack Pointer */
	int	AC;	/* Acumulator */
	int IR;	/* Instruction Register */
	int PR;	/* Parameter Register */
} regbank;

#define LOAD	1
#define STORE	2
#define ADD		3
#define SUB		4
#define JMP		5
#define JPG		6
#define JPL		7
#define JPE		8
#define JPNE	9
#define PUSH	10
#define POP		11
#define READ	12
#define WRITE	13
#define CALL	14
#define RET		15
#define HALT	16
