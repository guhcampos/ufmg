#define IO_H 1
void printError();
void loadProgram(FILE *programa, int *memoria, int start, int verbose);
void printState(regbank *regs, int *memoria);
