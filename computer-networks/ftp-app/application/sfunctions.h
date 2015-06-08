#ifndef SFUNCTIONS_H
#define	SFUNCTIONS_H 1

#include "messages.h"

// Funcao GET do servidor
int serverGet(t_msg data, void *buffer);
// Funcao SEND do servidor
int serverSend(t_msg data, int mtu, void *buffer);

#endif
