#include <stdlib.h>

#include "../headers/citymap.h"

using namespace std;

// Construtor
CityMap::CityMap (int sz) {
	size = sz;
	table = (Entidade*) calloc(size*size,sizeof(Entidade));
}
// Destrutor
CityMap::~CityMap() { delete table; }

// objetivo:	converter as coordenadas (x,y) em coordenada linear
// recebe:		coordenadas x e y de uma célula do mapa
// retorna:		a posicao real dessa coordenada no mapa linearizado
int CityMap::xytox(int xpos, int ypos) {
	return ((size*xpos)+ypos)%ypos;
}

// objetivo:	verificar se uma celula esta vazia
// recebe:		nada
// retorna:		true se a celula estiver vazia, false caso contrario
bool CityMap::cellIsEmpty(int xpos, int ypos) {

	int rpos = xytox(xpos,ypos);

	if( empty == table[rpos].getKind() ) return true;
	else return false;
}
