/*
* Gustavo Campos
* 2005041291
* gucampos@dcc.ufmg.br
*/
import java.io.*;
import java.util.Vector;
import java.util.Arrays;


public class superm {
	
	static BufferedReader entrada = new BufferedReader (new InputStreamReader (System.in));
	static boolean continua = true;

	public static void main (String[] args) throws Exception {

		String[] linha = new String[2];

		while (continua) {
			int n = Integer.parseInt (entrada.readLine());
			if (n==0) {
				continua = false;
				System.exit(0);
			} else {
			
				supermRede planalto = new supermRede (n);
	
				for (int i = 0; i<n;i++) {
					linha = entrada.readLine().split("\\s");
					planalto.addLoja(Integer.parseInt(linha[0]),Integer.parseInt(linha[1])); 
				}
	
				planalto.montaArranjos();
				planalto.calculaDeposito();
	
				System.out.println ("Teste "+supermRede.numTestes);
				System.out.println (supermRede.deposX+" "+supermRede.deposY);
				System.out.println ("");
			}
		}
	}
}

class supermLoja {

	int _x;
	int _y;

	supermLoja (int a, int b) {
		this._x = a;
		this._y = b;
	}

}

class supermRede {

	static int numTestes = 0;
	
	static int deposX = 0;
	static int deposY = 0;
	
	Vector redeSuper = new Vector();

	int[] xcoords;
	int[] ycoords;

	int numLojas = 0;

	supermRede (int a) {
		this.numLojas = a;
		this.numTestes++;
	}

	public supermLoja retornaLoja (int a) {
		return (supermLoja) this.redeSuper.get(a);
	}

	public void addLoja (int a, int b) {
		this.redeSuper.addElement (new supermLoja (a, b));
	}

	public void montaArranjos () {
		
		xcoords = new int[this.numLojas];
		ycoords = new int[this.numLojas];

		for (int i = 0; i < this.redeSuper.size() ; i++) {
			xcoords[i] = retornaLoja(i)._x;
			ycoords[i] = retornaLoja(i)._y;
		}
	}
	public void calculaDeposito() {
		Arrays.sort (this.xcoords);
		Arrays.sort (this.ycoords);
		
		int laux = this.xcoords.length;
		
		this.deposX = this.xcoords[(int) Math.ceil(laux/2)];
		this.deposY = this.ycoords[(int) Math.ceil(laux/2)];
	}
}
