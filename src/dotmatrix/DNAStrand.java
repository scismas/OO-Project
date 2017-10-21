package dotmatrix;

public class DNAStrand {
	private String id;
	private String data;
	private int length;
	
	public DNAStrand() {
		this("", "");
	}
	
	public DNAStrand(String id, String data) {
		this.id = id;
		this.data = data;
		this.length = data.length();
	}
	
	public DNAStrand compStrand() {
		String newID = this.getID() + "Complementary";
		String newData = "";
		for (int i = 0; i < this.getLength(); ++i) {
			newData += compPair(this.getData().charAt(i));
		}
		DNAStrand newStrand = new DNAStrand(newID, newData);
		return newStrand;
	}
	
	public int patternSearch(String pattern) {
		return data.indexOf(pattern);
	}
	
	public int patternSearch(DNAStrand dna) { //Overloaded method to get index of a dna sequence if it is in another dna sequence
		return data.indexOf(dna.getData());
	}
	
	public static char compPair(char base) { //This seems like a hack, but I guess it works and is technically O(4)?
		if (base == 'G') {
			return 'C';
		}
		else if (base == 'C') {
			return 'G';
		}
		else if (base == 'A') {
			return 'T';
		}
		else if (base == 'T') {
			return 'A';
		}
		else {
			return '\u0000'; //Null character I think?
		}
	}
	
	public String getID() {
		return id;
	}
	public String getData() {
		return data;
	}
	
	public int getLength() {
		return length;
	}
}
