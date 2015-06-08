public class NodoBinario {

	private NodoBinario esquerdo;
	private NodoBinario direito;
	private HufPar value;

	public NodoBinario (HufPar h) { value = h; }

	void insereEsquerda (NodoBinario n) { esquerdo = n; }
	void insereDireita (NodoBinario n) { direito = n; }

	NodoBinario getEsquerdo() { return this.esquerdo; }
	NodoBinario getDireito() { return this.direito; }

	HufPar getHufPar() {
		return value;
	}

	void printNodo() {
		System.out.print( this.getHufPar().obtemCaracter() );
		System.out.print( " - ");
		System.out.print( this.getHufPar().obtemFreq());
	}
}
		
