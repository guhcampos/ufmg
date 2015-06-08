#ifndef MESSAGES_H
#define MESSAGES_H 1

#define NAMESIZE 64
#define OVERHEAD 74
#define DEBUG 1
#define BUFFERDEBUG 80

typedef struct 
{
  char   command;             // Comando da mensagem
  int    filesize;            // Tamanho do arquivo
  char * filename;            // Nome do arquivo
  int    datasize;            // Tamanho da area de dados
  void * dados;               // Area de dados
} t_msg;

/*
 *  Recebe uma mensagem e retorna uma estrutura separada de mensagens
 */
t_msg recMessage (t_msg msg, void *buffer);

/*
 *  Monta uma area de memoria generica contendo o conteudo de uma estrutura de
 *  mensagem do protocolo 
 */
int sendMessage (t_msg msg);

/* Envia arquivo */
int sendFile(t_msg msg);

/* Recebe o arquivo e o escreve o arquivo no disco */
int getFile(t_msg msg, void *buffer);

void printbuffer(int size, void *buffer);
#endif
