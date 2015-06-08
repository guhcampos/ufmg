import java.io.*;
import java.util.LinkedList;
public class topsort {
	
	static BufferedReader ent = new BufferedReader (
		 new InputStreamReader (System.in) 
	);

	static String linha[];
	
	static int intervalo, entradas, master, slave;

	public static void main (String cmdline[]) throws IOException {
		
		linha = ent.readLine().split("\\s");
		
		intervalo = Integer.parseInt( linha[0] );
		entradas = Integer.parseInt ( linha[1] );

		LinkedList[] varal = new LinkedList[intervalo+1];	
		
		for (int i=1; i<=intervalo; i++) {
			varal[i] = new LinkedList();	
		}
		for (int e=0; e<entradas; e++) {
			linha = ent.readLine().split("\\s");

			slave = Integer.parseInt( linha[0] );
			master = Integer.parseInt( linha[1] );
			
			varal[master].add (slave);
		} 

		LinkedList resultado = new LinkedList();
		
		imprimeVaral (varal);
		
		ordenaTop (varal, resultado);

		imprimeResult (resultado);
	}

	public static void imprimeVaral (LinkedList[] myVaral) {
		
		System.out.println("---------------------------------");
		for (int i=1; i< myVaral.length; i++) {
			System.out.print ("Master "+i+"("+myVaral[i].size()+")"+"-> ");
			for (int j=0;j< myVaral[i].size(); j++) {
				System.out.print (myVaral[i].get(j)+" ");
			}
			System.out.println("");
		}
		System.out.println("");
	}
	public static void ordenaTop (LinkedList[] myVaral, LinkedList resultado) {
		
		boolean[] visitados = new boolean[myVaral.length];

		visitados[0] = true;

		for (int k=1; k< myVaral.length; k++) {
			if ( myVaral[k].size() == 0 && !visitados[k] ) {
				for (int z=1;z<myVaral.length; z++) {
					myVaral[z].remove(new Integer(k));
				}
				visitados[k] = true;
				resultado.add(k);
				
				k=1;
			}
		}
	}
	public static void imprimeResult (LinkedList result) {
		System.out.println("---------------------------------");
		for (int i=0; i< result.size(); i++ ) {
			System.out.print( result.get(i) + " " );
		}
		System.out.println("");
		System.out.println("---------------------------------");
	}	

}	
