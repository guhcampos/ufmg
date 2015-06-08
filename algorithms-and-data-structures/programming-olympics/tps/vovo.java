/*
* Gustavo Campos
* 2005041291
* gucampos@dcc.ufmg.br
*/
import java.io.*;
public class vovo {
	
	static BufferedReader entrada = new BufferedReader (new InputStreamReader(System.in));
	static boolean maisTestes = true;
	public static void main (String[] arg) throws IOException {
		String[] linha = new String[2];
		while (maisTestes) {
			vovoCofrinho newCofre = new vovoCofrinho (Integer.parseInt(entrada.readLine()));
			if (newCofre.numDepositos == 0) { 			
				maisTestes = false;
				System.exit(0);
			}
			System.out.println("Teste "+vovoCofrinho.numCofres);
			for (int i=0;i<newCofre.numDepositos;i++) {
				linha = entrada.readLine().split("\\s");				
				newCofre.joao += Integer.parseInt (linha[0]);
				newCofre.jose += Integer.parseInt (linha[1]);	
				System.out.println(newCofre.retornaDif());
			}		
			System.out.println("");		
		}
	}
}
class vovoCofrinho {

	static int numCofres = 0;

	int numDepositos;
	int joao;
	int jose;

	vovoCofrinho (int a) {
		numDepositos = a;
		numCofres++;
		joao = 0;
		jose = 0;
	}

	public int retornaDif () throws IOException{
		return (this.joao - this.jose);
	}
}
