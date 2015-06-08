public class HufEncoder {

	private static final char GEN_CHAR = '#';

	private FilaDePrioridade workspace;	

	public HufEncoder ( HufPar[] p ) { workspace = new FilaDePrioridade(p);	}

	NodoBinario geraArvoreDeHuffman(){
		if (this.workspace.numNodos == 1) {
			return workspace.extraiMenor();
		} else {
			NodoBinario x,y;
			double f;
			while ( this.workspace.numNodos > 1) {

				x = workspace.extraiMenor();
				y = workspace.extraiMenor();
				f = x.getHufPar().obtemFreq() + y.getHufPar().obtemFreq();
				NodoBinario z = new NodoBinario ( new HufPar ( GEN_CHAR,f ) );
				z.insereEsquerda(x);
				z.insereDireita (y);

				workspace.insere(z);
			}
			return geraArvoreDeHuffman();
		}
	}	
}
