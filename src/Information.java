public class Information {
	private char element;
	private int frequency;
	private String code;
	
	public Information() {}

	public Information( int f) {
		setFrequency(f);
	}
	public Information(char element , int frequency ) {
		this.element = element;
        this.frequency = frequency;
	}
	
	public char getElement () { return this.element; }
	public void setElement(char element){ this.element = element; }
	
	public int getFrequency () { return this.frequency; }
	public void setFrequency (int frequency) { this.frequency = frequency; }

	public String getCode () { return this.code ; }
	public void setCode (String code) { this.code = code ; }
}
