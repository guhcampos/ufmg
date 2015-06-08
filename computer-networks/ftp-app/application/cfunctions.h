#ifndef CFUNCTIONS_H
#define	CFUNCTIONS_H 1

#include "messages.h"

int clientSend(t_msg data, int mtu, void *buffer);
int clientGet(t_msg data, void *buffer);

#endif
