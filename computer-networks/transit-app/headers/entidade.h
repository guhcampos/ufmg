#ifndef ENTIDADE_H
#define ENTIDADE_H 1

// Possiveis tipos de entidade
enum kind {car, building, empty};

// Possiveis direcoes de orientacao
enum dir {north,south,east,west};

// Par ordenado de coordenadas
struct coord {
	int x,y;
};

class Entidade {

	private:
		int id;
		int waittime;
		coord posicao;
		kind tipo;
		dir direct;

	public:
		Entidade(int i, coord pos, kind type, dir to);

		kind getKind();
		coord getPosition();
		void moveTo(int x, int y);
};
#endif
