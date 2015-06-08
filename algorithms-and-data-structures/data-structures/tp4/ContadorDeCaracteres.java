import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ContadorDeCaracteres {

	//Funciona apenas para letras por comodidade

	static BufferedReader arquivo;
	public static final int ALFA_SIZE = 26;
	private static final int TO_FIRST_CHAR = -97;
	private static final char FIRST_CHAR = 'a';

	private static char[] alfabeto = new char[ALFA_SIZE]; 

	public static void main (String[] arquivosDeEntrada) throws IOException {

		for ( String str : arquivosDeEntrada ) {
			arquivo = new BufferedReader ( new FileReader (str) );
			double[] lista = fazContagem ();
			escreveNoArquivo (lista, str+".out");
		}
	}

	static double[] fazContagem() throws IOException{
		
		int[] ocorr = new int[ALFA_SIZE];

		System.out.println("\nFazendo Contagem...\n");

		//Le as linhas do arquivo
		while (true) {
			String linha = arquivo.readLine();
			if (linha != null) {			
				linha = linha.toLowerCase();
				//Extrai os caracteres das linhas			
				for(int i=0; i<linha.length();i++) {
					char c = linha.charAt(i);
					//Incrementa as ocorrencias
					ocorr[c+TO_FIRST_CHAR]++;
				}

			} else break;
		} 	
		
		int soma=0;
		double[] percents = new double[ALFA_SIZE];
		
		for (int i=0;i<ALFA_SIZE;i++) { soma+= ocorr[i]; }
		for (int i=0;i<ALFA_SIZE;i++) {
			percents[i] = 100 * ( (double)ocorr[i] / (double)soma );
		}		
		return percents;
	}
	static void escreveNoArquivo(double[] stat, String filename ) throws IOException {
		alfabeto[0] = FIRST_CHAR;
		for (int i=1;i<ALFA_SIZE;i++) {
			alfabeto[i] = ++alfabeto[0];
		}
		//corrige a primeira letra
		alfabeto[0] = FIRST_CHAR;
		
		BufferedWriter pencil = new BufferedWriter ( new FileWriter (filename) );
		
		pencil.write(ALFA_SIZE+"\n");
		for ( int i=0; i<ALFA_SIZE; i++ ) {
			pencil.write( alfabeto[i]+" "+stat[i]+"\n");
		}		
		pencil.flush();
	}
}
