#ifndef CITYMAP_H
#define CITYMAP_H 1

#include "entidade.h"

class CityMap {

	private:
		int size;
		Entidade * table;
	
		int xytox(int xpos, int ypos);

	public:
		CityMap(int size);
		~CityMap();

		bool cellIsEmpty(int xpos, int ypos);
};
#endif
