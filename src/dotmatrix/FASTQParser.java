package dotmatrix;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FASTQParser {
	private DNAStrand[] parsedDNA;
	private Path filePath;
	private BufferedReader rawData;
	private int total;
	
	public FASTQParser(String fileLoc) throws IOException {
		this.filePath = Paths.get(fileLoc);
		
		Charset charset = Charset.forName("US-ASCII");
		rawData = Files.newBufferedReader(filePath, charset); //Saves raw data to object
		
		rawData.mark(100000000); //Unsure if this is efficient, couldn't do it another way. This is done to set marker at beginning to reset to
		while ((rawData.readLine()) != null) { //Count total lines
			this.total += 1;
		}
		this.total /= 4; //Divide by 4 to find total entries
		parsedDNA = new DNAStrand[this.total]; //Initialize array with set total
		rawData.reset(); //reset to beginning for parsing later
	}
	
	public void parseFile() throws IOException {
		rawData.mark(100000000); //Unsure if this is efficient, couldn't do it another way
		for (int i = 0; i < this.total; ++i) {
			parsedDNA[i] = new DNAStrand(rawData.readLine(), rawData.readLine());
			rawData.readLine();//These are to skip the lines not needed, "+" line and quality line
			rawData.readLine();
		}
		rawData.reset();
	}
	
	public int patternSearch(String pattern, int i) {
		return parsedDNA[i].patternSearch(pattern);
	}
	
	public int patternSearch(DNAStrand dna, int i) {
		return parsedDNA[i].patternSearch(dna);
	}
	
	public String getID(int i) {
		return parsedDNA[i].getID();
	}
	
	public String getData(int i) {
		return parsedDNA[i].getData();
	}
	
	public int getTotal() {
		return total;
	}
}
