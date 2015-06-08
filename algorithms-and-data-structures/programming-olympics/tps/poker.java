/*
* Gustavo Campos
* 2005041291
* gucampos@dcc.ufmg.br
*/
import java.io.*;
import java.util.Arrays;
public class poker {
	static BufferedReader entrada = new BufferedReader (new InputStreamReader(System.in));
	public static void main (String[] arg) throws IOException {
		pokerGame jogo = new pokerGame(Integer.parseInt(entrada.readLine()));
		for (int k = 0;k<jogo.numTestes;k++) {
			String[] s = entrada.readLine().split("\\s");
			for (int i = 0; i<s.length;i++) {
				jogo.hand[i] = Integer.parseInt(s[i]);
			}	
			System.out.println ("Teste "+pokerGame.contaTestes);
			System.out.println (jogo.calculaMao());
			System.out.println ("");
			pokerGame.contaTestes++;
		}
		System.exit(0);
	}
}
class pokerGame {
		
	static int contaTestes = 1;
	static int numTestes = 0;

	int[] hand = new int[5];
	int pontuacao = 0;

	pokerGame (int a) {
		this.numTestes = a;
	}
	public int calculaMao () {
		int score = this.ordenaEVerifSequencia();
		if (score == 0) {
			score = calculaRepeticoes(contaOcorrencias(this.hand));
		}
		return score;
	}

	/* Metodos de contagem
		Os metodos abaixo sao usados para fazer a verificacao e contagem dos pontos
	*/
	public int ordenaEVerifSequencia() {
		Arrays.sort(this.hand);
		boolean sequencia = true;
		for (int i = 0; i< this.hand.length-1;i++) {
			if (this.hand[i] != this.hand[i+1]-1) { sequencia = false; }
		}
		if (sequencia) {
			return this.hand[0]+200;
		} else return 0;
	}
	public int[] contaOcorrencias (int[] a) {
		//Conta quantas ocorrencias ha de cada carta na hand
		int[] b = new int [14];
		for (int i = 0;i<5;i++) {
			b[a[i]]++;
		}
		return b;
	}
	public int calculaRepeticoes(int[] a) {
		int duplasNum= 0;
		int duplas[] = new int[2];
		int trinca = 0;
		int quadra = 0;
		int pontos = 0;
		for (int i=1;i<14;i++) {
			//Verifica se tem quadras, trincas e duplas, e quantas sao
			if (a[i] == 4) {
				quadra=i;
				i = 13;
			}
			if (a[i] == 3) {
				trinca=i;
			}
			if (a[i] == 2) {
				duplasNum++;
				if (duplas[0] == 0) { duplas[0] = i;} 
				else {duplas[1] = i;}
			}
		}
		//Define os pontos para cada caso possivel
		if (quadra != 0) { pontos = 180+quadra; } //Quadra apenas
		else if (trinca != 0) {
			 if (duplasNum ==0) {
				 pontos = 140+trinca; //Trinca apenas
			 } else { pontos = 160+trinca; } //Full Hand
 		}
		else if ( (duplasNum >0) & (trinca == 0) ) {
			if (duplasNum == 1) { pontos = duplas[0]; } //1 Dupla
			else if (duplasNum == 2) { 
				if (duplas[0] > duplas[1]) { //2 Duplas
					pontos = (3*duplas[0] + 2*duplas[1] + 20);
				} else { pontos = (2*duplas[0] + 3*duplas[1] + 20);}
			} 	
		}
		return pontos;	
	}
}
