public class HufPar {
	private char character;
	private double freq;

	public HufPar (char c, double f) {
		character = c;
		freq = f;
	}
	public char obtemCaracter() {
		return character;
	}
	public double obtemFreq() {
		return freq;
	}
}

