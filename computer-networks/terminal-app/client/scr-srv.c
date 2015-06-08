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
    int s, cli_s;

    if ((s=passive_socket(SERVER_PORT)) < 0) {
        perror ("passive_socket");
        exit (1);
    }
    if ((cli_s=simple_accept(s)) < 0) {
        perror ("simple_accept");
        exit (1);
    }

    if ((len=recv_str(cli_s,str,sizeof(str)))<0) {
        fprintf(stderr,"failed to receive identification\n");
        exit(1);
    }
    fprintf(stderr,"received identification: \"%s\"\n",str);
    sprintf(greetings,"Welcome, %s",str);
    if (send_all(cli_s,greetings,strlen(greetings)+1)<0) {
        fprintf(stderr,"failed to send greetings\n");
        exit(1);
    }
    fprintf(stderr,"ready to transmit\n");
    while(fgets(str,sizeof(str),stdin)!=NULL){
        len=strlen(str);
        if (str[len-1]=='\n') { /* if the buffer contains a newline */
            str[len-1]='\0';    /* then strip it out (len includes the 0) */
        } else {                /* otherwise, the string is already ok   */
            len++;              /* but then we have to count the 0 in len */
        }
        if (send_all(cli_s,str,strlen(str)+1)<0) { /* no need to use sendall, usually */
            perror("send: conexão encerrada pelo servidor");
            exit(0);
        }
    }
    fprintf(stderr,"This is the end...\n");

    close (cli_s);
    return 0;
}

