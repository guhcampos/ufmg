#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "cfunctions.h"
#include "messages.h"
#include "fileop.h"

int clientSend(t_msg data, int mtu, void *buffer){

	data.command  = 'S';
	data.filesize = fileSize(data.filename);

	/* Fase 1:  envia mensagem de requisicao 
   *          aguarda recebimento de confirmacao
   */
	sendMessage(data);
	data = recMessage(data,buffer);

  /* Fase 2:  se receber OK, inicia transmissao
   *          se receber outro comando:
   */
	if (data.command == 'O') {
	/*recebemos OK, enviamos o arquivo*/
		if(!sendFile(data)) return 0;
		else return 1;
	}
  /* Fase 2.2:  se receber um valor no tamanho do arquivo, pede confirmacao
   *  para sobrescreve-lo
   */
  	else if (data.filesize != 0){
		printf("O arquivo ja existe, deseja sobrescrever? [y/n]\n");
		char aux = (char) getchar();
    /* Confirmando, o arquivo eh enviado */
		if(aux == 'y') return sendFile(data);
  	}
  /* Fase 2.1:  se o tamanho do arquivo for zero, aborta */
	else{
   		 perror("Um erro desconhecido ocorreu no envio, consulte o server");
    		return 1;
  	}

 	return 0;
}

int clientGet(t_msg data, void *buffer){

	data.command  = 'G';
  data.datasize = 0;

	/* Fase 1: Manda mensagem com o comando e nome do arquivo 
   * e aguarda resposta*/
	sendMessage(data);
	data = recMessage(data,buffer);

  /* Fase 2:  Se receber 0 no tamanho do arquivo, aborta
   *          Se receber OK, verifica o espaco em disco
   *            aborta caso nao houver espaco
   *            envia OK caso houver
   */
	if(data.filesize == 0){
		perror("O arquivo nao existe\n");
		return 1;
	}
	else{
		if (freeSpace(data.filesize)){
	/*O arquivo existe e ha espaco, vamos receber*/
			data.command = 'O';
			sendMessage(data);
		}
		else{
	/*Nao ha espaco para receber, abortando*/
			data.command = 'E';
			sendMessage(data);
			return 1;
		}
	}
  /* Fase 3: Recebe o arquivo */
  return getFile(data,buffer);
}
