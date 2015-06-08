import java.io.IOException;
import java.util.LinkedList;

public class Crypt {

	/* Constantes */

	public static final int LIMITE	= 5000;	//Numero de primos a gerar com o crivo
	public static final int BITS_A	= 20;	//Magnitude inicial do primeiro numero
	public static final int BITS_B	= 25;	//Magnitude inicial do segundo numero
	public static final int CERTEZA	= 10;	//Numero de bases a testar em Miller
	public static final int SCREEN	= 80;	//Numero de colunas da tela

	public static void main (String args[]) throws IOException {

		if ( args[0] == "" ) {
			System.out.println("Faltou o nome do arquivo");
			System.exit(1);
		}
	
		/* Gera o Crivo */
		int[] crivo = Primos.crivoErast(LIMITE);
		/* Gera as duas chaves privadas */
		int pvtKey1 = Primos.criaPseudoPrimo(BITS_A, crivo, CERTEZA);
		int pvtKey2 = Primos.criaPseudoPrimo(BITS_B, crivo, CERTEZA);
		/* Gera a chave publica, o E e o D */
		long publicKey = (long) pvtKey1 * pvtKey2;
		int E = RSA.numeroE (pvtKey1, pvtKey2, crivo);
		long D = RSA.numeroD ( publicKey, RSA.calcFiN(pvtKey1, pvtKey2) );
	
		System.out.println("\n\n   ### Chaves ###   \n");

		System.out.println("Chave Privada = "+pvtKey1);
		System.out.println("Chave Privada = "+pvtKey2);
		System.out.println("Chave Publica = "+publicKey);
		System.out.println("Numero E	  = "+E);
		System.out.println("Numero D	  = "+D);
	
		/* 	Codificando texto */
		LinkedList blockList = RSA.brakeText(args[0],publicKey);

		System.out.println("\n\n   ### Blocos ###   \n");
		for (int i=0; i<blockList.size(); i++) {
			if( (i!=0) && (i%(SCREEN/16)==0) ) System.out.print("\n");
			System.out.format( ":%12d:", Long.valueOf( (Long) blockList.get(i) ));
		}

		System.out.println("\n\n   ### Texto Codificado ### \n");

		String textoCodificado = RSA.encrypt(blockList, E, publicKey);
		RSA.printStringona(textoCodificado, SCREEN);

		System.out.println("\n\n   ### Tempos ### \n");
	}
}
