import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class PeopleIndexer {
	
	static TabelaHash data = new TabelaHash(13);

	public static void main (String arg[]) throws IOException {
		System.out.println("");
		System.out.println("PeopleIndexer - TPIII - AEDSII");
		System.out.println("Aluno: Gustavo Campos Ferreira Guimaraes - 2005041291");
		System.out.println("Professor: Roberto Bigonha");
		System.out.println("-----------------------------------------------------");

		BufferedReader enTeclado = new BufferedReader ( new InputStreamReader ( System.in ) );
		BufferedReader enArquivo = new BufferedReader ( new InputStreamReader ( System.in ) );		


		boolean fracasso = true;
		
		while (fracasso) {
			try {			
				System.out.println("Digite o nome do arquivo a ser lido:");
				enArquivo = new BufferedReader ( new FileReader( enTeclado.readLine() ) );
				fracasso = false;
			} 
			catch (FileNotFoundException erro) {
				System.out.println ("Erro: "+erro);
				System.out.println ("Arquivo nao encontrado, confira o nome do arquivo e tente novamente");
			}
			System.out.println("-----------------------------------------------------");
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
				data.insertRecord (dados[0], dados[1], dados[2], dados[3].charAt(0), Integer.parseInt(dados[4]) );
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
			System.out.println("3 - sair do Programa");

			int option = Integer.parseInt (enTeclado.readLine());

			switch (option) {
				case 1: buscaPorCPF();
				break;
				case 2: buscaPorNome();
				break;
				case 3: System.exit(0);
				break;
				default: System.exit(0);
			}
		}
	}
	static void buscaPorCPF() throws IOException {
		BufferedReader enTeclado = new BufferedReader ( new InputStreamReader ( System.in ) );
		System.out.println("Digite o CPF desejado:");
		String cpf = enTeclado.readLine();
		HumanNode result = data.getRegistroCPF (cpf);
		result.printMe();
	}
	static void buscaPorNome() throws IOException {
		BufferedReader enTeclado = new BufferedReader ( new InputStreamReader ( System.in ) );
		System.out.println("Digite o Nome desejado:");
		String nome = enTeclado.readLine();
		HumanList result = data.getRegistroNome (nome);
		if (result.empty()) System.out.println ("Não foi encontrada nenhuma ocorrencia para "+nome);
		result.printList();
	}
}
