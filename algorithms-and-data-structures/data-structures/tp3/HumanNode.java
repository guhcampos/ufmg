public class HumanNode {
	HumanNode prev, next;
	private String	name;
	private String	adress;
	private String	cpf;
	private char	sexo;
	private int	age;
	HumanNode () {
		prev = next = null;
	}
	HumanNode ( 
		
		String  myName,
		String	myAdress,
		String	myCpf,
		char	mySexo,
		int	myAge
				
		) {
		this.name	=	myName;
		this.adress	=	myAdress;
		this.cpf	=	myCpf;
		this.sexo	=	mySexo;
		this.age	=	myAge;
	}
	/**	Retorna o campo nome	*/
	public String getName () {
		return this.name;
	}
	/**	Retorna o campo Endereco	*/
	public String getAdress () {
		return this.adress;
	}
	/**	Retorna o campo cpf	*/
	public String getCPF () {
		return this.cpf;
	}
	/**	Retorna o campo sexo	*/
	public char getSexo () {
		return this.sexo;
	}
	/**	Retorna o campo idade	*/
	public int getAge () {
		return this.age;
	}
	/**	Seta o campo nome	*/
	public void setName (String n) {
		this.name = n;
	}
	/**	Seta o campo endereco	*/
	public void setAdress (String add) {
		this.adress = add;
	}
	/**	Seta o campo cpf	*/
	public void setCPF (String c) {
		this.cpf = c;
	}
	/**	Seta o campo sexo	*/
	public void setSexo (char sex) {
		this.sexo = sex;
	}
	/**	Seta o campo idade	*/
	public void setAge (int ag) {
		this.age = ag;
	}
	/**	Imprime dados completos da pessoa	*/
	public void printMe() {
		System.out.println ("-------------------------------");
		System.out.println ( this.getName() );
		System.out.println ( this.getCPF() );
		System.out.println ( this.getAdress() );
		System.out.println ( this.getSexo() );
		System.out.println ( this.getAge() );
		System.out.println ("-------------------------------");
	}
	/**	Retorna os dados completos da pessoa numa String	*/
	public String printMeG() {

		String retorno = 
				 "--------------------------------------------------------\n"+
				 "Nome:\t"+this.getName()+"\n"+
				 "CPF:\t"+this.getCPF()+"\n"+
				 "Idade:\t"+this.getAge()+"\n"+
				 "Sexo:\t"+this.getSexo()+"\n"+
				 "Endereço:\t"+this.getAdress()+"\n"+
				 "--------------------------------------------------------\n";
		return retorno;
	}
}
