/*
* Gustavo Campos
* 2005041291
* gucampos@dcc.ufmg.br
*/
import java.io.*;
import java.util.*;

public class vivo {

	static BufferedReader entrada = new BufferedReader (new InputStreamReader(System.in));
	static boolean maisTestes = true;	
	static vivoGame jogo;
	public static void main (String[] arg) throws IOException{
		String[] aux;
		while (maisTestes) {
			aux = entrada.readLine().split("\\s");
			if((Integer.parseInt(aux[0]) == 0) & (Integer.parseInt(aux[1]) == 0)) {
				maisTestes = false;
				System.exit(0);
			}
			jogo = new vivoGame (aux, entrada.readLine().split("\\s"));
			for (int i = 0;i<jogo.numRodadas;i++) {
				jogo.processaRodada(entrada.readLine().split("\\s"));
			}
			System.out.println("Teste "+jogo.getNumJogos());
			System.out.println(jogo.retornaVencedor());
			System.out.println("");
		}
	}
}	
class vivoGame {
	
	static int numJogos;

	int numPlayers;
	int numRodadas;
	Vector players = new Vector();

	vivoGame (String[] a, String[] b) {
		numPlayers = Integer.parseInt (a[0]);
		numRodadas = Integer.parseInt (a[1]);
		for (int i=0; i<b.length;i++) {
			players.addElement (new Integer (Integer.parseInt(b[i])));
		}
		numJogos++;
	}

	public void processaRodada(String[] a) {
		int playersNum = Integer.parseInt(a[0]);
		int comando = Integer.parseInt(a[1]);
		int j;
		int remov = 0;
		for (int i=2;i<a.length;i++) {
			if (Integer.parseInt(a[i]) != comando) {
				j = i-remov;
				players.remove (j-2);
				remov++;
			}
		}
	}
	public int retornaVencedor () {
		Integer winner = (Integer) this.players.get(0);
		return Integer.valueOf(winner);
	}
	public int getNumJogos() {
		return numJogos;	
	}
}
