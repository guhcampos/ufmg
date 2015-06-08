/* tcp_socket.h - funções auxiliares para uso de sockets TCP
 */

#ifndef _TCP_SOCKET_H_
#define _TCP_SOCKET_H_

#include <netdb.h>

int passive_socket( u_short local_port, u_short remote_port,
                struct hostent* remote_host);

int active_socket(u_short local_port, u_short remote_port,
              struct hostent* remote_host);

int socket_read( int s, void* buf, int buflen );

int socket_write( int s, void* buf, int buflen );
#endif /* _TCP_SOCKET_H_ */
