#ifndef SERVER_H
#define SERVER_H 1

#define DEFAULTPORT     10666
#define MAXCONNECTIONS  20
#define MAXMSGSIZE      2000

#include "chatcl.h"

#define COMMOMMSG       MAXCLIENTID+1
#define LLISTCMSG       MAXCLIENTID+2
#define DISCONMSG       MAXCLIENTID+3
#define FINALZMSG       MAXCLIENTID+4
#define ERRORNMSG       MAXCLIENTID+5
#define CONNECMSG       MAXCLIENTID+6

#endif
