#ifndef NASOCKET_H
#define NASOCKET_H 1
// glibc
#include <arpa/inet.h>
// nossas
#define LISTENTIMEOUT 10

class nasocket
{
  private:
    fd_set master; // master file descriptors
    fd_set reader; // reader file descriptors

    struct sockaddr_in srvaddr; // server address

    int maxfd;      // maior file descriptor
    int srvsocket;  // socket de escuta do servidor

    /*
     * Abre um socket e retorna o descritor de arquivos do mesmo
     */
    bool _socket();

    /*
     * Associa o socket a uma porta
     */
    bool _bind(int port);

    /*
     * Instrui o socket para ouvir conexoes
     */
    bool _listen();


  public:

    /* Construtor padrao
     *
     * recebe como parametro:
     *  port: numero da porta a escutar
     *
     *  Excecoes:
     *  int = 1: nao conseguiu abrir o socket
     *  int = 2: nao conseguiu fazer o bind
     *  int = 3: nao conseguiu ouvir a porta
     */
    nasocket(int port);

    /*
     * Destrutor
     */
    ~nasocket();

    /*
     * Fecha conexao com um socket
     */
    void _close(int n);

    /*
     * Retorna se existem conexoes chegando no socket servidor
     */
    bool incomming(int fd);

    /*
     * Retorna se ha data a ler no file descriptor n
     */
    bool data(int fd);

    /*
     * Retorna o maior descritor de arquivos
     */
    int max();
    
    /*
     * Aceita uma conexao em um socket, retorna o fd do socket novo
     */
    bool naaccept();

    /*
    * Faz a chamada da select() System Call
    */
    bool naselect();

    /*
     * DEBUG: Imprime status dos file-descriptors
     */
    void printfds();

    /* 
     * Envia uma mensagem de erro a um socket
     */

    void senderror(const char *msg, int fd);

};
#endif
