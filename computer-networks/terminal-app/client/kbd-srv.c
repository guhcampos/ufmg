#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <string.h>
#include "simplesockets.h"

extern int errno;
#define SERVER_PORT 6666

#define MAX_PENDING 10
#define  MAX_LINE 2000
int
main ()
{
    char str[MAX_LINE];
    char greetings[MAX_LINE];
    unsigned int len;
    int s, new_s;

    if ((s=passive_socket(SERVER_PORT)) < 0) {
        perror ("passive_socket");
        exit (1);
    }
    if ((new_s=simple_accept(s)) < 0) {
        perror ("simple_accept");
        exit (1);
    }

    if ((len=recv_str(new_s,str,sizeof(str)))<0) {
        fprintf(stderr,"failed to receive identification\n");
        exit(1);
    }
    fprintf(stderr,"received identification: \"%s\"\n",str);
    sprintf(greetings,"Welcome, %s",str);
    if (send_all(new_s,greetings,strlen(greetings)+1)<0) {
        fprintf(stderr,"failed to send greetings\n");
        exit(1);
    }
    fprintf(stderr,"waiting for lines\n");
    while ((len=recv_str(new_s,str,sizeof(str)))>=0) {
        fprintf(stderr,"%s\n",str);
    }

    close (new_s);
    return 0;
}

