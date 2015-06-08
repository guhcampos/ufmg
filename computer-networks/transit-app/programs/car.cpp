#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <signal.h>
#include <unistd.h>
#include "../headers/entidade.h"
#include "../headers/comunica.h"

void sigint(int sig);

int main(int argc, char **argv) {

	// Substitui comportamento do sinal de close
	signal(SIGINT, sigint);

	// Definicao de variaveis de inicio
	int param;
	int port = SERVER_PORT_W;
	char *end_ip = new char[16];

	// Interpreta parametros de linha de comando
	if (argc != 5 && argc != 3) {
		perror("\n\e[31;1m Chamada incorreta. Confira o readme.\e[m");
		exit(-1);
	}
	while ((param = getopt(argc,argv,"s:p:")) != -1) {
		switch(param) {
			case 's':
				if(16 < strlen(optarg)) {
					perror("\n\e[31:1m Formato do IP: XXX.XXX.XXX.XXX\e[m");
					exit(-1);
				}
				strcpy(end_ip,optarg);
				break;
			case 'p':
				port = atoi(optarg);
				break;
			default:
				perror("\n\e[31;1m O unico argumento aceito e -p porta.\e[m");
				exit(-1);
		}
	}

	printf("\nIniciando no endereco = %s:%d\n",end_ip,port);
	return 0;
}

void sigint (int sig) {
	printf ("\n Finalizando Carro \n");
	exit(0);
}
