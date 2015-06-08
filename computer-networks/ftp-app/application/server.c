#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "messages.h"
#include "sfunctions.h"
#include "../enlace/dll.h"

int main (int argc, char **argv)
{
  /* Tenta inicializar a camada de enlace */
  if (dll_init()<0) {
    perror("Nao foi possivel iniciar a interface de enlace");
    exit(1);
  }

  int mtu = dll_mtu();
  if(OVERHEAD>=mtu) {
    perror("O MTU do canal e insuficiente para a transmissao neste protocolo");
    exit(1);
  }

  /* Inicializa uma mensagem vazia para ser usada nas transacoes */
  t_msg mensagem;
  mensagem.command    = 0;
  mensagem.filesize   = 0;
  mensagem.filename   = calloc(NAMESIZE,1);
  mensagem.datasize   = 0;
  mensagem.dados      = calloc(mtu-OVERHEAD,1);

  /* Inicializa o buffer de arquivo
   * Nao eh necessario haver um buffer de envio e um de recebimento, ja que 
   * somente uma acao sera executada por vez */
  void *buffer = calloc(mtu,1);

	/* Recebe o comando do cliente */
	mensagem = recMessage (mensagem,buffer);

  /* Inicia a rotina para receber ou enviar arquivos, dependendo do comando
   * recebido do cliente
   */
  int success;
	if (mensagem.command == 'S') success = serverGet(mensagem, buffer);
	else if (mensagem.command == 'G') success = serverSend(mensagem,mtu,buffer);
	else {
  	perror("O comando nao existe, digite S para SEND e G para GET\n");
    exit(1);
	}
  free(mensagem.filename);
  free(mensagem.dados);
  free(buffer);
	return 0;
}
