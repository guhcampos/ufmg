int enviaACK(
				int rsocket,
				int msgid,
				unsigned char *destino,
				char *iface
);
int sendFrame(
				int sockid,
				datagrama dados,
				unsigned char *destmac,
				char *ifname,
				struct ifreq *iface,
				struct sockaddr_ll *sockadd
);
