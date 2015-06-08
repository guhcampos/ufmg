/*
* Gustavo Campos
* 2005041291
* gucampos@dcc.ufmg.br
*/
import java.io.*;
public class bafo {
	
	static BufferedReader entrada = new BufferedReader (new InputStreamReader(System.in));
	static boolean maisTestes = true;
	public static void main (String[] arg) throws IOException {
		String[] linha = new String[2];
		while (maisTestes) {
			bafoGame newGame = new bafoGame (Integer.parseInt(entrada.readLine()));
			if (newGame.numRodadas == 0) { 
				System.exit(0);
			}
			for (int i=0;i< newGame.numRodadas;i++) {
				linha = entrada.readLine().split("\\s");
				newGame.aldo += Integer.parseInt (linha[0]);
				newGame.beto += Integer.parseInt (linha[1]);	
			}
		System.out.println("Teste "+bafoGame.numJogos);
		System.out.println(newGame.retornaVencedor());
		System.out.println("");
		}
	}
}
class bafoGame {

	static int numJogos = 0;

	int numRodadas;
	int aldo;
	int beto;

	bafoGame (int a) {
		numRodadas = a;
		numJogos++;
		aldo = 0;
		beto = 0;
	}

	public String retornaVencedor () throws IOException{
		if (this.aldo<this.beto) {
			return "Beto";
		}
		 else if (this.aldo>this.beto) {
			return "Aldo";
		}
		else throw new IOException();
	}
}
