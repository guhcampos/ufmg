/* Funcao que faz a primeira passada
 * 
 * Recebe opcodes com a lista de opcodes
 * Coloca em simbolos os simbolos encontrados, com seus respectivos ILC's
 * Coloca em codigo o codigo pre-gerado, livre de labels
 *
 * Retorna um inteiro = quantidade de labels encontrada
 */
void firstPass(symbol *simbolos, inst *codigo, FILE *arquivo, int *sLb, int *sCod);
void secondPass(opcode *opcodes,symbol *simbolos,inst *codigo,FILE *arquivo,int sS,int cS);
