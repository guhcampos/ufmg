public class TabelaHash {	
	//Estrutura
	private HumanList[] tableNOME; 
	private HumanList[] tableCPF;	
	//Construtor
	public TabelaHash (int x) {		
		tableNOME = new HumanList[x];
		tableCPF = new HumanList[x];

		for (int i=0; i<x; i++) {
			tableNOME[i] = new HumanList();
			tableCPF[i] = new HumanList();
		}
	}
	/** Insere nodos na HashTable a partir de seus dados	*/
	public void insertRecord ( 
			
			String  myName,
			String	myAdress,
			String	myCpf,
			char	mySexo,
			int	myAge ) {

		HumanNode record = new HumanNode ( myName, myAdress, myCpf, mySexo, myAge );

		int a = hashCodigo(record.getName());
		tableNOME[a].insere (record);

		int b = hashCodigo(record.getCPF());
		tableCPF[b].insere (record);	
	}	
	/**	Busca na tabela todos os registros com o nome especificado	*/
	public HumanList getRegistroNome ( String name ) {		
		int a = hashCodigo(name);
		return tableNOME[a].getByName ( name );
	}
	/**	Busca na tabela um registro com o CPF especificado	*/
	public HumanNode getRegistroCPF ( String cpf ) {		
		int b = hashCodigo(cpf);
		return tableCPF[b].getByCPF ( cpf );
	}
	/**	Gera um codigo hash para uma String que se encaixe na tabela	*/
	public static int hashCodigo (String stri) {

		String str = stri.toLowerCase();
				
		int primo1 = 11;
		int primo2 = 13;
		
		long hash = 0;
		
		for ( int i = 0; i < str.length(); i++ ) {
			hash += primo1 * str.charAt(i);
			primo1 *= primo2;
		}
		if ( hash >= 0 ) return (int) ( hash % 13 );	
		else return (int) -( hash % 13 );
	}
}