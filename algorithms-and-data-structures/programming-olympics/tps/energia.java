/*
* Gustavo Campos
* 2005041291
* gucampos@dcc.ufmg.br
*/
import java.io.*;

public class energia {
	public static void main (String args[]) throws IOException{
		
		BufferedReader entrada = new BufferedReader(new InputStreamReader (System.in) );
		
		boolean continua = true;

		String[] line = new String[2];
		
		energiaGrafo cemig;
		
		while (continua) {
			
			line = entrada.readLine().split("\\s");
			
			int e = Integer.parseInt(line[0]);
			int f = Integer.parseInt(line[1]);
			
			if (e == 0 || f == 0) {
				continua = false;
				System.exit(0);
			}

			cemig = new energiaGrafo(e,f);

				for (int i=0; i<cemig.numLigacoes;i++) {
					line = entrada.readLine().split("\\s");
					int x = Integer.parseInt (line[0]);
					int y = Integer.parseInt (line[1]);
					
					cemig.matrizLigacoes[y][x] = true;
					cemig.matrizLigacoes[x][y] = true;
				}
				//cemig.imprimeMatriz();
				cemig.verificaConexoes(1);
				cemig.verificaConexo();
				//cemig.imprimeVetor();
				cemig.imprimeResultado();
		}
	}
}
class energiaGrafo {
	
	static int numteste = 0;

	boolean[][] matrizLigacoes;

	boolean[] nodosVerificados;
	boolean[] nodosLigados;

	int numEstacoes;
	int numLigacoes;

	boolean conexo = true;

	energiaGrafo (int a, int b) {

		this.numteste++;

			this.matrizLigacoes= new boolean[a+1][a+1];
			this.nodosLigados = new boolean[a+1];
			this.nodosVerificados = new boolean[a+1];
			this.numEstacoes = a;
			this.numLigacoes = b;

			this.nodosLigados[1] = true;
	}
	public void imprimeResultado () {
		System.out.println ("Teste "+this.numteste);
		if (this.conexo) {
			System.out.println("normal");
		} else {
			System.out.println("falha");
		}
		System.out.println ("");
	}
	public void verificaConexoes(int a) {
		this.nodosVerificados[a] = true;
		this.nodosLigados[a] = true;
		for (int i=1;i<this.nodosLigados.length;i++) {
			if (this.matrizLigacoes[a][i] & !this.nodosVerificados[i]) {
				verificaConexoes(i);
			}
		}
	}
	public void verificaConexo() {
		for(int i=1;i<this.nodosLigados.length;i++) {
			if(!this.nodosLigados[i]) {
				this.conexo = false;
				i = this.nodosLigados.length;
			}
		}
	}
	//Debugger
	public void imprimeMatriz () {
		for (int i=0;i<=this.numEstacoes;i++){	
			for (int j=0;j<=this.numEstacoes;j++){
				if (matrizLigacoes[i][j]) { 
					System.out.print(1);
				} else System.out.print(0);
				System.out.print (" | ");
			}
			System.out.println();
		}
	}
	public void imprimeVetor () {
		System.out.println("Verificados:");
		for (int i=0;i<this.nodosVerificados.length;i++) {
			System.out.print(this.nodosVerificados[i]);
			System.out.print(" | ");
		}
		System.out.println();
		System.out.println("Ligados: ");
		for (int i=0;i<this.nodosVerificados.length;i++) {
			System.out.print(this.nodosVerificados[i]);
			System.out.print (" | ");
		}
	}
}
