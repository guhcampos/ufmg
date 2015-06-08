#include <stdio.h>

void entrada (int *mtx) {

	int i;

	for (i=0;i<81;i++){
		 scanf("%d", &*(mtx+i));
	}

}

void saidaSemFormato(int *mtx){

	int x, y=0;

	printf("\n");
	for (x=0;x<81;x++) {
		y++;
		printf ("%d ", *(mtx+x) );
		if(y==9) {
			printf("\n");
			y=0;
		}
	}
	printf("\n");
}
