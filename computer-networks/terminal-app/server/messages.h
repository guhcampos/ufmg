#ifndef MESSAGES_H
#define MESSAGES_H 1
/*
 * Imprime as mensagens de ajuda do programa e os valores padrões 
 * para a porta e o numero maximo de conexões
*/
void printHelp ();

/* 
 * Decodifica as mensagens de acordo com os comandos pré-estabelecidos
*/
int decodemsg(char * buffer);

/*
 * Retorna o identificador do cliente (tela ou teclado) que deseja se conectar ao servidor
*/
int getidfrommsg(char *buffer);

#define DEBUG 1

#endif
