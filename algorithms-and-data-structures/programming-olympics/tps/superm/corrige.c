/*
  OBI2003 - corretor para o problema Supermercado
  Guilherme Ottoni
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_SUPERS 1010

void erro(char * msg)
{
  fprintf(stderr,"%s\n",msg);
  printf("0\n");
  exit(0);
}

int compara(const void *p1, const void *p2)
{
  return *((int *)p1) - *((int *)p2);
}

int main(int argc, char *argv[])
{
  int s, x[MAX_SUPERS], y[MAX_SUPERS], i, teste=1, caso_aluno, x_aluno, y_aluno;
  char linha_aluno[10000];
  
  FILE * sol_aluno;

  sol_aluno = fopen(argv[1], "rt");

  while (scanf("%d", &s) == 1 && s > 0)
    {
      /* le solucao do aluno */
      if ((fscanf(sol_aluno,"%s",linha_aluno) != 1) ||
	  (strcmp(linha_aluno,"Teste")))
	erro("Palavra 'Teste' nao encontrada");
      if ((fscanf(sol_aluno,"%d",&caso_aluno) != 1) || (caso_aluno != teste++))
	erro("Nro do teste incorreto");
      if (fscanf(sol_aluno, "%d %d", &x_aluno, &y_aluno) != 2)
	erro("Final inesperado da resposta");

      /* calcula resposta correta */
      for (i = 0; i < s; i++)
	scanf("%d %d", &x[i], &y[i]);
      qsort(x, s, sizeof(int), compara);
      qsort(y, s, sizeof(int), compara);

      if (s % 2 == 1)
	{
	  if (x_aluno != x[s/2] || y_aluno != y[s/2])
	    erro("Resposta errada (localizacao nao eh otima)");
	}
      else
	{
	  if (x_aluno < x[(s-1)/2] || x_aluno > x[s/2] ||
	      y_aluno < y[(s-1)/2] || y_aluno > y[s/2])
	    erro( "Resposta errada (localizacao nao eh otima)");
	}
    }
  printf("10\n");
  fclose(sol_aluno);
  return 0;
}

