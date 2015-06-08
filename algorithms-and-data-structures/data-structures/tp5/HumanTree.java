public class HumanTree {

	//Campos de estrutura
	private HumanTree rightChild, leftChild, parent;
	private static final int MAX_NAME_RESULTS = 40;
	
	//Campos de informacao
	private String name, cpf, adress;
	private int age;
	private char sex;
	public HumanTree () {
	}
	public HumanTree ( String n, String c, String a, char sexo, int id ) {
		name = n; cpf = c; adress = a; sex = sexo; age = id; 
	}
	
	//Metodos de tratamento de informacoes
	/** Retorna o campo nome */
	public String getName () { return name; }
	/** Retorna o campo Endereco */
	public String getAdress () { return adress; }
	/** Retorna o campo cpf */
	public String getCPF () { return cpf; }
	/** Retorna o campo sexo */
	public char getSex () { return sex; }
	/** Retorna o campo idade */
	public int getAge () { return age; }
	/** Seta o campo nome */
	public void setName (String n) { name = n; }
	/** Seta o campo endereco */
	public void setAdress (String add) { adress = add; }
	/** Seta o campo cpf */
	public void setCPF (String c) { cpf = c; }
	/** Seta o campo sexo */
	public void setSex (char sexo) { sex = sexo; }
	/** Seta o campo idade */
	public void setAge (int ag) { age = ag; }
	/** Copia as informacoes de um nodo para este nodo */
	public void transferInfoFrom( HumanTree nodo ) {
		this.name = nodo.name;
		this.cpf = nodo.cpf;
		this.adress = nodo.adress;
		this.age = nodo.age;
		this.sex = nodo.sex;
	}
	/** Imprime os dados contidos no nodo */
	public void imprime () {
		if ( !name.equals("0") ) { //Impede que seja impresso o nodo "dummy"
			System.out.println("\n##########################################################");
			System.out.println("Nome: "+name);
			System.out.println("Endereco: "+adress);
			System.out.println("Sexo: "+sex);
			System.out.println("Idade: "+age);
			System.out.println("CPF: "+cpf);
			System.out.println("##########################################################\n");
		}
	}
	//Metodos de arvore	
	/** Pesquisa um CPF na arvore e retorna o nodo correspondente */
	HumanTree pesquisaCPF ( String CPF ) {
		HumanTree raiz = this;
		while ( raiz != null ) {
			if (raiz.cpf.compareToIgnoreCase(CPF) == 0) return raiz;
			else if (raiz.cpf.compareToIgnoreCase(CPF) < 0) raiz = raiz.rightChild;
			else raiz = raiz.leftChild;
		} return null;
	}
	/** Pesquisa um nome na arvore e retorna um vetor de resultados */
	HumanTree[] pesquisaNome( String Nome ) {		
		
		HumanTree[] results = new HumanTree [MAX_NAME_RESULTS];
		int itera = 0;		
		HumanTree raiz = this;
		
		while ( raiz != null ) {
			if (raiz.name.compareToIgnoreCase(Nome) == 0) {
				results[itera] = raiz;
				itera++;
				raiz = raiz.rightChild;
			}
			else if (raiz.name.compareToIgnoreCase(Nome) < 0) raiz = raiz.rightChild;
			else raiz = raiz.leftChild;
		} return results;
	}
	/** Insere um nodo na arvore usando o CPF como chave */
	void insertByCPF (HumanTree nodo) {
		HumanTree folha = this;
		while ( true ) {
			if ( folha.cpf.compareToIgnoreCase(nodo.cpf) > 0) {
				if (folha.leftChild == null) {
					folha.leftChild = nodo;
					nodo.parent = folha;
					break;
				} else folha = folha.leftChild;
			} else {
				if (folha.rightChild == null) {
					folha.rightChild = nodo;
					nodo.parent = folha;
					break;
				} else folha = folha.rightChild;
			}
		}				
	}
	/** Insere um nodo na arvore usando o nome com chave */
	void insertByNome (HumanTree nodo) {
		HumanTree folha = this;
		while ( true ) {
			if ( folha.name.compareToIgnoreCase(nodo.name) > 0) {
				if (folha.leftChild == null) {
					folha.leftChild = nodo;
					nodo.parent = folha;
					break;
				} else folha = folha.leftChild;
			} else {
				if (folha.rightChild == null) {
					folha.rightChild = nodo;
					nodo.parent = folha;
					break;
				} else folha = folha.rightChild;
			}
		}	
	}
	/** Remove um nodo da arvore usando o CPF como chave */
	void remove( HumanTree nodo ) {
		//Se tiver dois filhos remove o antecessor
		if (nodo.leftChild != null && nodo.rightChild != null) nodo.removeAntecessor();
		//Se nao tiver nenhum filho, simplesmente remove o nodo
		else if (nodo.leftChild == null && nodo.rightChild == null) {
			if (nodo.parent.leftChild == nodo) {
				nodo.parent.leftChild = null;
				nodo.parent = null;
			} else {
				nodo.parent.rightChild = null;
				nodo.parent = null;
			}
		//Se tiver so o filho direito, o avo o adota
		} else if ( nodo.leftChild == null && nodo.rightChild != null) {
			if (nodo.parent.leftChild == nodo) {
				nodo.parent.leftChild = nodo.rightChild;
				nodo.rightChild.parent = nodo.parent;
				nodo.parent = null;
			} else {				
				nodo.parent.rightChild = nodo.rightChild;
				nodo.rightChild.parent = nodo.parent;
				nodo.parent = null;
			}
		}
		//Se tiver so o filho esquerdo, o avo o adota
		else {
			if (nodo.parent.leftChild == nodo) {
				nodo.parent.leftChild = nodo.leftChild;
				nodo.leftChild.parent = nodo.parent;
				nodo.parent = null;
			} else {				
				nodo.parent.rightChild = nodo.leftChild;
				nodo.leftChild.parent = nodo.parent;
				nodo.parent = null;
			}
		}			
	}
	/** Remove o nodo quando e preciso trocar de lugar com o antecessor */
	void removeAntecessor() {		
		//Busca antecessor
		HumanTree antecessor = this.leftChild;
		while ( antecessor.rightChild != null ) antecessor = antecessor.rightChild;
		//Faz a troca
		this.transferInfoFrom(antecessor);
		this.remove(antecessor);
	}
	/** Caminha a arvore em pre ordem para retornar os nodos em ordem alfabetica */
	void caminhaPreOrdem() {
		if ( this.leftChild != null ) this.leftChild.caminhaPreOrdem();
		this.imprime();
		if ( this.rightChild != null ) this.rightChild.caminhaPreOrdem();
	}
}