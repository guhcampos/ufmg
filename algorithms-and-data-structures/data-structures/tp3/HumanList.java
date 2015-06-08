public class HumanList {
	//Campos base
	private HumanNode first;
	private HumanNode last;
	//Tamanho da lista
	int nodeCount = 0;
	//Construtor
	public HumanList () {
	}	
	/**	Insere um nodo por atributos	*/
	public void insere ( 	String  n,
				String	a,
				String	c,
				char	s,
				int	i 
			) {		

		HumanNode nod = new HumanNode ( n, a, c, s, i );
		
		if ( this.empty() ) {
			first = last = nod;			
		}
		else {
			last.next = nod;
			nod.prev = last;
			last = nod;
		}
		nodeCount++;
		
	}
	/**	Insere um nodo instanciado	*/
	public void insere (HumanNode nod) {
		if ( this.empty() ) {
			first = last = nod;			
		}
		else {
			last.next = nod;
			nod.prev = last;
			last = nod;
		}
		nodeCount++;
	}
	/**	Retorn um nodo na posicao x da lista */
	public HumanNode getNodo ( int x ) {

		HumanNode n;
		
		if ( x < this.size() ) {		
			n = first;		
			while ( x-- > 0) n = n.next;
		}
		else {
			n = last;
			while ( x++ < this.size() ) n = n.prev;
		}
		
		return n;				
	}
	/**	Pesquisa os nodos da lista em busca de um com o Campo CPF determinado */
	public HumanNode getByCPF ( String cpf ) {

		HumanNode atual = this.first;

		int x = 0;

		while ( x < this.size() ) {
			if ( cpf.equalsIgnoreCase (atual.getCPF()) ) return atual;
			else { 
				atual = atual.next; 
				x++;
			}
			
		}
		 
		return new HumanNode();
	}
	/**	Pesquisa os nodos da lista por todos com o campo name determinado */
	public HumanList getByName ( String name ) {
		HumanList results = new HumanList();
		int x = 0;
		HumanNode atual = this.first;

		while ( x < this.size() ) {
			if ( name.equalsIgnoreCase (atual.getName()) ) results.insere (atual);
			atual = atual.next; x++;
		}
		return results;
	}
	/**	Remove da lista um nodo instanciado	*/
	public void removeNodo ( HumanNode nod ) {		
		if ( this.empty() ) {
			first = last = null;
		}
		else {
			if ( nod == first ) {
				first = nod.next;
				nod.next.prev = null;
			}
			else if ( nod == last ) {
				last = nod.prev;
				nod.prev.next = null;
			}
			else {
				nod.next.prev = nod.prev;
				nod.prev.next = nod.next;
			}
				
		}
		nodeCount--;
	}
	/**	Remove o nodo na posicao x da lista	*/
	public void remove (int x) {
		HumanNode c = getNodo (x);
		this.removeNodo (c);
	}
	/**	Retorna  o tamanho, ou quantidade de nodos, da lista	*/
	public int size () {
		return nodeCount;
	}
	/** 	Retorna true caso a lista esteja vazia, caso contrario, retorna false	*/
	public boolean empty () {
		if ( this.size() == 0 ) return true;
		else return false;
	}
	/**	Retorna o primeiro elemento	*/
	public HumanNode getFirst() {
		return this.first;
	}
	/**	Retorna o Segundo elemento	*/
	public HumanNode getLast() {
		return this.last;
	}
	/**	Imprime todos os nodos da lista na tela	*/
	public void printList(){
		HumanNode obj = first;
		for (int i=0; i< this.size(); i++) {
			obj.printMe();
			obj = obj.next;
		}
	}
	/**	Retorna todos os nodos da lista numa String	*/
	public String printListG(){
		HumanNode obj = first;
		String retorno = "";
		for (int i=1; i< this.size()+1; i++) {
			retorno +="Ocorrencia "+i+"\n"+
			obj.printMeG();
			obj = obj.next;
		}
		return retorno;
	}
}
