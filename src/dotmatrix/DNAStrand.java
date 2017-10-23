package dotmatrix;

public class DNAStrand {	//Stores all relevant info about a single DNA strand (does not yet support qualifiers)
	private String id;
	private String data;
	private int length;
	
	public DNAStrand() {
		this("", "");
	}
	
	public DNAStrand(DNAStrand original) {
		this.id = original.getID() + "Copy";
		this.data = original.getData();
		this.length = data.length();
	}
	
	public DNAStrand(String id, String data) {
		this.id = id;
		this.data = data;
		this.length = data.length();
	}
	
	public DNAStrand compStrand() {	//Creates DNAStrand with complementary values, A<->T and C<->G
		String newID = this.getID() + "Complementary";
		String newData = "";
		for (int i = 0; i < this.getLength(); ++i) {
			newData += compPair(this.getData().charAt(i));
		}
		DNAStrand newStrand = new DNAStrand(newID, newData);
		return newStrand;
	}
	
	public String compString(String baseString) {
		String newString = "";
		for (int i = 0; i < baseString.length(); ++i) {
			newString += compPair(baseString.charAt(i));
		}
		return newString;
	}
	
	public int patternSearch(String pattern) { //Returns index of where string is found, if not found returns -1
		return data.indexOf(pattern);
	}
	
	public int patternSearch(DNAStrand dna) { //Overloaded method to get index of a dna sequence if it is in another dna sequence
		return data.indexOf(dna.getData());
	}
	
	public static char compPair(char base) { //This seems like a hack and there must be some cleaner way to do this, but I guess it works and is technically O(4)?
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
	
	public String getID() {	//Ugh only reason to do this is because all the instances are private. Same with the next two methods
		return id;
	}
	public String getData() {
		return data;
	}
	
	public int getLength() {
		return length;
	}
}
