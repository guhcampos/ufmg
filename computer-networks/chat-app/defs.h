#ifndef DEFS_H
#define DEFS_H 1

#define FRAME_SIZE 1000					// Tamanho do Frame
#define PROTOCOLO 0x42					// Codigo de identificacao do meu protocolo
#define ACKMSG 69						// Identifica uma mensagem do tipo ack
#define TXTMSG 96						// Identifica uma mensagem do tipo texto
#define BUFF_SIZE 2048					// Define o tamanho dos buffers de recebimento

#define DEBUG 1

typedef struct {
	unsigned int prot;
	unsigned int type;
	unsigned int id;
	char texto[128];
} datagrama;

#endif
