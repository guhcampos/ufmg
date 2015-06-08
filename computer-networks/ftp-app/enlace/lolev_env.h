/* lolev_env.h - funções de configuração da camada de baixo nível
 * responsável pelo canal virtual.
 */

#ifndef _LOLEV_ENV_H_
#define _LOLEV_ENV_H_

/* Variáveis de ambiente que devem ser definidas para lolev:
 *
 * LOLEV_MODE:
 *    Modo de criação do socket, depende da ordem de disparo dos programas.
 *    O servidor deve ser sempre disparado primeiro e seu modo é PASSIVE.
 *    Já o cliente deve ser disparado posteriormente e seu modo é ACTIVE.
 *    
 * LOLEV_LOCAL_PORT
 *    Porto local a ser usado pelo socket.
 *
 * LOLEV_REMOTE_HOST
 *    Identificação da máquina onde se encontra a outra ponta do link virtual.
 *
 * LOLEV_REMOTE_PORT
 *    Porto a ser conectado na máquina remota.
 *
 * Apesar de não se exigido do ponto de vista de operação de sockets,
 * para simplificar a configuração neste trabalho o procedimento de
 * inicialização exige que todas essas informações sejam fornecidas 
 * para os dois lados do canal virtual.
 */

#define TRUE  1
#define FALSE 0

#define ENV_MODE        "LOLEV_MODE"
#define ENV_MODE_ACTIVE  "ACTIVE"
#define ENV_MODE_PASSIVE "PASSIVE"
#define ENV_MTU          "LOLEV_MTU"
#define ENV_LOCAL_PORT   "LOLEV_LOCAL_PORT"
#define ENV_REMOTE_HOST  "LOLEV_REMOTE_HOST"
#define ENV_REMOTE_PORT  "LOLEV_REMOTE_PORT"
#define MINPORT  2000
#define MAXPORT 20000
#define MINMTU    500
#define MAXMTU   1500

/* Variáveis globais disponíveis  */
extern int endpoint_is_active;
extern int endpoint_is_passive;
extern int lolev_env_mtu;

extern unsigned int   local_port;
extern unsigned int   remote_port;
extern char*          remote_host_name;
extern struct hostent remote_host;

/* Funções auxiliares usadas para extrair a configuração das variáveis de
 * ambiente (environment)
 */
void getenv_vars(void);

#endif /* _LOLEV_ENV_H_ */
