import java.util.Random;

public class Primos {

	/* 
	 *	Funcao que gera o crivo de erastotenes, retorna um vetor com todos
	 *	 os primos menores que 'LIMITE'
	 *
	 *	Para isso, cria uma tabela com 'LIMITE' espacos e inicializa-a com os
	 *	 numeros dos indices, em seguida testa a divisao de todos o numeros,
	 *	 zerando as casas dos que ja foram excluidos, eliminando-os de futuros
	 *	 testes
	 */
	public static int[] crivoErast(int limit) {
	
		int[] tabela = new int[limit];
		int count = limit-2;

		//Cria a tabela de numeros
		for (int i=0; i<tabela.length; i++) tabela[i] = i;
		
		for (int j=2;j<limit;j++) {
			if (tabela[j] != 0) { //Exclui as casas com 0 dos testes
				for (int k=j+1;k<(limit-j+1); k++) {
					if (tabela[k] != 0 && tabela[k] % j == 0) { //Testa pela divisao
						tabela[k] = 0;
						count--;
					}
				}
			}
		}

		// Monta e retorna a tabela num array
		int[] crivo = new int[count];
		int k=0;

		for (int i=2;i<tabela.length;i++) {
			if(tabela[i] !=0) { //So escreve as casas nao zeradas
				crivo[k] = tabela[i];
				k++;
			}
		}
		return crivo;
	}
	/* 
	 *		A ideia e gerar numeros aleatorios usando a funcao random para cada
	 *		 casa de 'numero'e preencher com 0 ou 1 de acordo com um criterio
	 *		 arbitrario, no caso:
	 *
	 *		Se o numero gerado for PAR, preencheremos com 1
	 */
	public static int geraInteiroImpar(int bits) {
		Random gen = new Random();
	
		int[] numero = new int[bits];
		int result=0;

		for(int i=0;i<numero.length;i++) numero[i]=0; //Inicializa o vetor com zeros
		numero[0] = 1; //Garante que o numero e impar
		numero[bits-1]=1; //Garante a magnitude do numero

		 //Preenche aleatoria mente com 1 ou 0
		for (int j=1;j<bits;j++) numero[j] = gen.nextInt(9)%2;
		 //Transforma para decimal
		for (int k=0;k<numero.length;k++) result += Math.pow(2,k) * numero[k];
		return result;
	}
	/*	
	 *	Tenta dividir o numero por todos os primos menores que 'LIMITE' gerados
	 *	 pelo crivo
	 *
	 *	Retorna true se o numero passar em todos os testes
	 */
	public static boolean testaDivSuc(int num,int[] tab) {
		// Divide pelos primos do crivo, se for divisivel por algum retorna false
		for (int i=0;i<tab.length;i++) if (num!=tab[i] && num%tab[i]==0) return false;
		return true;
	}
	/*
	 *	Executa o teste de miller sobre o numero, com 'certeza' bases
	 *
	 *	Retorna true se o numero for aprovado em todas as bases
	*/
	public static boolean testaMiller(int num, int[] crivo , int certeza) {
		// Certifica-se que certeza nao e maior que o numero de primos do crivo 
		if (certeza > crivo.length) certeza = crivo.length;

		// Primeira etapa do teste de Miller: n-1=2^k*q

		int q=0;
		int k=0;
		int n=num-1;

		while (n%2==0) {
			n /= 2;
			k++;
		}
		q = num / (int)Math.pow(2, k);

		// Segunda etapa do teste de Miller

		int i, base;
		long r;

		for (int j=0;j<certeza;j++) {

			i=0;
			base = crivo[j];
			r = eleva(base,q,num);

			do {
				//Se cair aqui e pseudoprimo nesta base
				if((i==0 && r==1)||(i>=0 && r==num-1)) break;

				i++;
				r = eleva(r,2,num);

				if (i>k) return false; //Condicao de contorno
			} while (true);
		}
		return true; //Se chegar ate aqui e pseudoprimo para todas as bases
	}
	/* 
	 *	Essa funcao eleva dois numeros passo a passo para que nao estoure
	 *	a capacidade de long
	 */
	public static long eleva(long base, int expoente, long modulo) {	
		long result=base;
		for(int i=1;i<expoente;i++) {
			
			result*=base;
			result%=modulo;	
		}
		return result;
	}
	/* 	
	 * Executa o algoritmo da fatoracao sobre o numero
	 *
	 *	Retorna true caso o numero seja primo
	 */
	public static boolean fatora (long num) {
		// Executa o algoritmo da fatoracao no numero
		int fator = 2;

		while (fator < Math.sqrt(num)) {
			if(num%fator==0) {
				System.out.println(" Pela fatoracao: "+fator+" e fator de "+num);
				return false;
			}
			fator++;
		}
		System.out.println(" "+num+" e primo");
		return true;
	}
	/*
	 *	Executa a fatoracao de Fermat sobre o numero dado
	 *
	 *	Retorna true caso o numero seja primo
	 */
	public static boolean testaFermat(long chave) {

		int k=0;
		long y=0;
		long t=0;
		long x= (int) Math.sqrt(chave);

		// Primeiro passo da fatoracao de Fermat
		if (x*x==chave) {
			System.out.println(" Por Fermat: "+x+" e fator de "+chave);
			return false;
		}
		// segundo passo da fatoracao de Fermat
		do {
			x++;	
			t = x*x - chave;
			y = (long) Math.sqrt(t);
			if (y*y==t) {
				System.out.println(" Por Fermat: "+(x+y)+" e "+(x-y)+" sao fatores "+chave);
				return false;
			}
			k++;
		} while (x<=(chave+1)/2);
		return true;
	}
	/*
	 * Tenta gerar numeros pseudoprimos que passem nos testes de divisoes sucessivas
	 *  e Miller.
	 *
	 * Este metodo gera 2^BITS inteiros com BITS bits, caso nao consiga encontrar
	 *  nenhum pseudoprimo forte, incrementa o numero de bits e continua tentando
	 *
	 * O Numero maximo de bits eh 32, caso ultrapasse esse numero ele desiste
	 *
	 * Retorna o numero ao final da execucao
	 */
	public static int criaPseudoPrimo (int bits, int[] crivo, int certeza) {

		int n=0;
		int contador=0;
		int increm=0;

		while (true) {

			// Tenta gerar 2^bits numeros impares de cada magnitude
			if(contador >= Math.pow(2,bits)) {
				System.out.println("nenhum pseudoprimo gerado para 2^"+(bits+increm));
				contador = 0;
				increm++;
			}
			if(bits+increm>32) {
				System.out.println("Nao consegui gerar os primos");
				System.exit(1);
			}
			// Gera Inteiro
			n = geraInteiroImpar(bits+increm);
			// Testa contra o crivo e Miller
					if (testaDivSuc(n,crivo) && testaMiller(n, crivo, certeza)) return n;
			contador++;
		} 
	}
}
