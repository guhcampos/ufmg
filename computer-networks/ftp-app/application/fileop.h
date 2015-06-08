#ifndef FILEOP_H
#define FILEOP_H 1

#include <stdio.h>
#include <string.h>

typedef struct
{
  int    size;            // Tamanho da area de dados
  void  *bloco;           // Area de dados
} t_chunck;

/* Procura pelo arquivo de nome fileName no diretorio corrente */
int fileExist(char *fileName);

/* Retorna o tamanho do arquivo fileName em bytes */
int fileSize(char *fileName);

/* Verifica se existe espaço em disco para o arquivo */
int freeSpace(long long fileSize);

#endif
