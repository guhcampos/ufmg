import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;

public class PeopleIndexer {
	
	static HumanTree dataCPF;
	static HumanTree dataNome;
	static JOptionPane panel = new JOptionPane();

	public static void main (String arquivo[]) throws IOException {
	
		//Instancia as arvores com um nodo "dummy"
		dataCPF = new HumanTree("0","0","0",'0',0);
		dataNome = new HumanTree("0","0","0",'0',0);
	
		System.out.println("");
		System.out.println("PeopleIndexer(Arvores Binarias) - TPV - AEDSII");
		System.out.println("Aluno: Gustavo Campos Ferreira Guimaraes - 2005041291");
		System.out.println("Professor: Roberto Bigonha");
		System.out.println("-----------------------------------------------------");

		BufferedReader enArquivo = new BufferedReader ( new InputStreamReader ( System.in ) );
		BufferedReader enTeclado = new BufferedReader ( new InputStreamReader ( System.in ) );
		//Recebe as entradas
		try {
			if (arquivo.length == 0) {
				System.out.println("Voce deve passar o arquivo de entrada como parametro");
				System.exit(0);
			} else enArquivo = new BufferedReader ( new FileReader(arquivo[0]) );
		}
		catch (FileNotFoundException erro) {
			System.out.println("Arquivo nao encontrado, confira o nome do arquivo");
			System.exit(0);
		}
		
		boolean endOfFile = false;
		int	regNum = 0;

		String linha;
		String[] dados = new String[5];

		do {
			for (int i = 0; i<5; i++) {
				try {
					linha = enArquivo.readLine();
					if ( !linha.equals("endOfFile") ) {
						dados[i]=linha;
					} else {
						endOfFile = true;
						break;
					}
				}
				catch (IOException error) {
					System.out.println("Erro"+error);
				}
			} 
			if ( !endOfFile ) {
				dataCPF.insertByCPF ( new HumanTree (
						dados[0], dados[1], dados[2], dados[3].charAt(0), Integer.parseInt(dados[4])
					) 
				);
				dataNome.insertByNome (new HumanTree(
						dados[0], dados[1], dados[2], dados[3].charAt(0), Integer.parseInt(dados[4])
					)
				);
				regNum++;
				
			}
		} while (!endOfFile);
		System.out.println("");
		System.out.println("Inseridos "+regNum+" registros");
		System.out.println("");
		while (true) {
			System.out.println("Escolha uma opcao:");
			System.out.println("1 - Buscar um registro por CPF");
			System.out.println("2 - Buscar registros por nome");
			System.out.println("3 - Apagar um Registro");
			System.out.println("4 - Visualizar banco ordenado por CPF");
			System.out.println("5 - Visualizar banco ordenado por Nome");
			System.out.println("6 - Sair do Programa: ");
			System.out.println("");
			int option = 0;
			
			try { option = Integer.parseInt (enTeclado.readLine()); }
			catch (IOException e) { option = -1; }
			catch (NumberFormatException e) { option = -1; }
			
			switch (option) {
				case 1: buscaPorCPF();
				break;
				case 2: buscaPorNome();
				break;
				case 3: deletaRegistro();
				break;
				case 4: dataCPF.caminhaPreOrdem();
				break;
				case 5: dataNome.caminhaPreOrdem();
				break;
				case 6: System.exit(0);				
				break;
				case -1: System.out.println("\nOpcao invalida, tente novamente\n");
				break;
				default: System.exit(0);
			}
		}
	}
	static void buscaPorCPF() throws IOException {
		String cpf = panel.showInputDialog("Digite o CPF desejado:");
		HumanTree result = dataCPF.pesquisaCPF (cpf);
		if (result != null) result.imprime();
		else System.out.println("\nNenhuma ocorrencia para "+cpf+"\n");
	}
	static void buscaPorNome() throws IOException {
		String nome = panel.showInputDialog("Digite o Nome desejado:");
		HumanTree[] result = dataNome.pesquisaNome (nome);
		if (result[0] == null) {
			System.out.println ("\nNao foi encontrada nenhuma ocorrencia para "+nome+"\n");
		}
		else {
			for (HumanTree x : result) {
				if (x == null) break;
				else x.imprime();
			}
		}
	}
	static void deletaRegistro() throws IOException {		
		String cpf = panel.showInputDialog("Digite o CPF que voce deseja remover");
		//Obtem o nodo a ser removido
		HumanTree deletar = dataCPF.pesquisaCPF(cpf);		
		String nome = deletar.getName();
		//Remove o nodo da arvore de cpf
		dataCPF.remove(deletar);		
		//Pesquisa os possiveis equivalentes na arvore de nomes
		HumanTree[] candidatos = dataNome.pesquisaNome(nome);
		for (int i=0; i< candidatos.length;i++) {
		//Remove o equivalente
			if ( candidatos[i].getCPF().equals(cpf) ) dataNome.remove(candidatos[i]);
			break;
		}
	}
}
