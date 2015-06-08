#include <stdio.h>
#include "../headers/entidade.h"

// Construtor
Entidade::Entidade(int i, coord pos, kind type, dir to) {
	id = i;
	posicao = pos;
	tipo = type;
	direct = to;
	waittime = 0;
}

using namespace std;

// objetivo:	retornar o tipo da entidade
// recebe:		nada
// retorna:		o tipo do objeto
kind Entidade::getKind() {
	return tipo;
}

// objetivo:	retornar a posicao de uma entidade
// recebe: 		nada
// retorna:		 a posicao do veiculo em uma struc do tipo coord
coord Entidade::getPosition() {
	coord pos;
	pos.x = posicao.x;
	pos.y = posicao.y;
	return pos;
}
// objetivo: 	setar a posicao de um objeto o em um mapa
// recebe: 		dois inteiros representando coordenadas x e y
// retorna: 	nada
void Entidade::moveTo(int x, int y) {
	if(tipo == car) {
		posicao.x = x;
		posicao.y = y;
	} else printf ("\n\e[31;1mERRO: apenas carros podem ser movidos\e[m");
}
