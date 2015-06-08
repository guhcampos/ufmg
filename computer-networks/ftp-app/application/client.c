/* client.c - cliente mínimo que utiliza a dll para enviar uma mensagem. */

#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>
#include <string.h>
#include "messages.h"
#include "cfunctions.h"
#include "../enlace/dll.h"

int main(int argc, char* argv[])
{
	
	char *entrada;
	char *comando;
	int aux;
	
	if (argc == 1) {
		printf ("Passe um argumento por favor\n");
     	exit (1);
	}
  /* Pega os parametros passados na linha de comando e atribui seus valores 
   * a variaveis	
   */
	while (( aux = getopt( argc, argv, "C:N:"  )) != -1) {
		switch (aux){
			case 'C':
				comando = optarg;      
				break;
      case 'N':
   	   	entrada = optarg;
				break;
			default:
				printf( "Uso de parametro invalido\n" );
				exit( 1 );
		}
	}
	
 /* Inicializa a camada de enlace */
  if (dll_init()<0) {
    perror("Nao foi possivel iniciar comunicacao com a camada de enlace");
    exit(1);
  }

  /* Verifica o MTU fo canal */
  int mtu = dll_mtu();
  if(OVERHEAD>=mtu) {
    perror("O MTU do canal e insuficiente para a transmissao neste protocolo");
    exit(1);
  }

  /* Inicializa o buffer de arquivo
   * Nao eh necessario haver um buffer de envio e um de recebimento, ja que 
   * somente uma acao sera executada por vez */
  void *buffer = calloc(mtu,1);

  /* Inicializa uma mensagem vazia para ser usada nas transacoes */
  t_msg mensagem;
  mensagem.command    = 0;
  mensagem.filesize   = 0;
  mensagem.filename   = calloc(NAMESIZE,1);
  mensagem.datasize   = 0;
  mensagem.dados      = calloc(mtu-OVERHEAD,1);

  strcpy(mensagem.filename,entrada);

  /* Chama a rotina correta dependendo do comando passado por parametro */
	if(strcmp(comando, "send") == 0 ) clientSend(mensagem,mtu,buffer);
	else if (strcmp(comando, "get") == 0)	clientGet(mensagem,buffer);
	else {
		printf("O comando nao existe\n");
		exit(1);
	}

  dll_shutdown();
  free(buffer);
  free(mensagem.filename);
  free(mensagem.dados);
  return 0;
}
