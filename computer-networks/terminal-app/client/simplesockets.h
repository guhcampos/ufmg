#ifndef _SIMPLESOCKETS_H_
#define _SIMPLESOCKETS_H_
/*
 * As funcoes passive_socket e active_socket nao fazem nada demais,
 * servem apenas para isolar as chamadas das funcoes para criacao de sockets
 * em um ponto so' do codigo, escondendo os detalhes de manipulacao dos
 * parametros necessarios. A primeira espera apenas o numero do porto a
 * ser usado pelo servidor, enquanto a segunda espera tambem um string
 * com o identificador da maquina onde ele executa.
 * Esse identificador pode ser tanto
 * o numero IP na notacao decimal com pontos ou um nome.
 */

int  passive_socket( int myport );
int  active_socket( int server_port, char* server_host_name );
int  simple_accept( int passive_socket );

/*
 * ATENÇÃO: as funções a seguir são blocantes e não devem ser usadas em
 * programas que utilizem select, pois teriam um comportamento indesejado.
 */

int recv_all( int s, void* buf, int buflen );
int send_all( int s, void* buf, int buflen );
int recv_str(int s, char* str,  int strlengh );

#endif /* _SIMPLESOCKETS_H_ */
