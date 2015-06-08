import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

/* Esta classe contem os metodos usados para codificacao e decodificacao via RSA,
 * 	incluindo a quebra em blocos. 
 *
 * Para ficar mais facil, inclui os algoritmos numericos exclusivos do TP2 tambem
 *	neste arquivo separado, ao inves de mescla-los ao Primos.java
 */

public class RSA {

	/* Este metodo recebe o nome de um arquivo, faz a leitura e retorna uma array
	 *  de inteiros correspondentes aos blocos em que o texto foi dividido.
	 *
	 *  Na codificacao UTF-8, cada caractere possui 2 bytes, ou seja, o valor maximo
	 *   que um caractere pode ter e 2 elevado 16 = 65536.
	 *
	 *  Fazendo uma estimativa, podemos considerar que como cada caractere tem valor maximo
	 *   65536, podemos considerar que, como nosso texto nao utiliza caracteres incomuns,
	 *   como texto em arabe ou japones, 3 digitos sao suficientes para cada caractere.
	 *	 
	 * Caso seja necessario aumentar o tamanho do bloco, para incluir caracteres de outras
	 *	codificacoes, basta aumentar o valor de CHARMAX
	 *
	 *  Sendo assim, calculamos a quantidade de digitos 'd' de nossa chave publica, 
	 *   e asseguramos que cada bloco tem no maximo 'd-1' digitos.
	 *
	 *  Em seguida, para determinar o tamanho da array de blocos, basta dividir o numero
	 *   total de blocos pelo numero obtido acima. 
	 *
	 *	A constante TEXTMAX da o tamanho maximo do texto a ser codificado, para textos
	 * 	 maiores, basta aumentar seu valor.
	 *
	 * 	Obs.: Nao precisamos nos preocupar com blocos comecados em 0 pois nenhum caractere
	 * 	 tem seu codigo comecado em 0, a nao ser o caractere nulo, e sempre colocamos 
	 * 	 caracteres inteiros nos blocos.
	 */ 
	private static final int CHARMAX = 3;
	private static final int TEXTMAX = 100000;

	public static LinkedList brakeText (String filename, long pkey) throws IOException { 
		
		BufferedReader arquivo = new BufferedReader ( new FileReader (filename));
		arquivo.mark(100000);
		int car;

		/* Conta o numero de caracteres */
		int chars = 0;

		while ( ( car = arquivo.read()) != -1 ) ++chars;

		/* Tamanho do bloco = tamanho digitos da chave */
		int blockSize = 0;
		long aux = pkey;

		while (aux > 0) {
			aux /= 10;
			blockSize++;
		}

		LinkedList listaDeBlocos = new LinkedList();

		/* Faz a leitura do texto novamente, inserindo os valores na matriz */

		arquivo.reset();

		String bloco = "";
		int charCount = 0;
		int blockCount = 0;
		
		do {

			car = arquivo.read();

			if(charCount+CHARMAX > blockSize) {
				/* Se estourar o bloco, escreve na lista e zera o bloco */
				listaDeBlocos.add(Long.parseLong(bloco));
				bloco = "";
				charCount = 0;
			}
			/* Adiciona o codigo do caractere ao bloco */
			bloco+= car;
			charCount=charCount+CHARMAX;	
		} while ( car != -1 );
		return listaDeBlocos;
	}





	/* Retorna fiN */
	public static int calcFiN ( int p, int q ) {
		return ((p-1) * (q-1));
	}	
	/* Assumindo que a pessoa que vai codificar a mensagem tem sua chave
 	 *  privada, o calculo do fi de n e simples (p-1) & (q-1)
 	 *
 	 * O Algoritmo para calcular o numero E (mod (FI(n)) esta abaixo
 	 */
	public static int numeroE (int p, int q, int[] crivo) {

		/* Calcula o fi(n) */
		int fiN =  calcFiN(p, q);
	
		/* Encontra e = menor primo que nao divide fiN */
		for (int i=0;i<crivo.length;i++) {
			if ( fiN % crivo[i] != 0 ) return crivo[i];
		}
		return 0;
	}
	/* Este metodo apenas executa o algoritmo euclideano extendido */
	public static long[] euclidesExtendido( long a, long b) {
		
		long x = 0;
		long y = 1;

		long X = 1;
		long Y = 0;

		while (b != 0) {

			long tmp = b;
			long q = a / b;
			b = a % b;
			a = tmp;

			tmp = x;
			x = X - q * x;
			X = tmp;

			tmp = y;
			y = Y - q * y;
			Y = tmp;
		}

		 long resultado[] = new long[4];

		resultado[0] = x;
		resultado[1] = y;
		resultado[2] = X;
		resultado[3] = Y;

		return resultado;
	}
	/* Metodo que encontra a chave de decodificacao D */
	public static long numeroD (long N, int fiN) {

		long euclides[] = euclidesExtendido (N, fiN);
		long d = euclides[3] > 0 ? euclides[3] : euclides[3]+N;
		return d;
	}
		
	/* Este metodo codifica o texto, retornando em uma unica string o 
 	*   resultado da codificacao
 	*/
	public static String encrypt (LinkedList brokeText, int E, long N) {

		long codedBlock, block;
		String codedText = "";
		
		for(int i=0; i< brokeText.size();i++) {
			/* Recebe o valor do bloco e calcula seu valor codificado */			
			block = Long.valueOf( (Long) brokeText.get(i) );
			codedBlock = Primos.eleva(block, E, N);
			if (codedBlock < 0) codedBlock += N;

			/* Insere o novo bloco na String */
			codedText += codedBlock;

		}
		return codedText;
	}
	
	/* Este metodo facilita a impressao do resultado, tudo o que ele faz e receber 
 	 *  uma string enorme e quebra-la em linhas que cabem em N linhas
 	 */
	public static void printStringona(String stringona, int lineSize) {
		for(int i=0;i< stringona.length();i++) {
			if( i != 0 && (i % lineSize) == 0) System.out.print("\n");
			System.out.print(stringona.charAt(i));
		} System.out.print("\n");
	}
}
