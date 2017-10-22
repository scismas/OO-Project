//Parses data from a FASTQ file
//Can search for specific pattern sequence in a DNA strand
//Can create complementary strand from base strand
//Hope to do graphics interface for interaction with sequences


package dotmatrix;

import java.io.IOException;

public class GenomicsMain { //Uhh right now this is just for testing I guess, runs the window and stuff but like doesn't do much yet
	static GraphicsInterface window;
	public static void main(String[] args) throws IOException {
		startGUI();
		
		//Creating file path for file that needs to be parsed
		String fileLoc = "SP1.fq";
		FASTQParser fqp = new FASTQParser(fileLoc);
		fqp.parseFile();
		
		System.out.println(fqp.getID(249));
		System.out.println(fqp.getData(249));
		
		String testPattern = "TGGG";
		
		DNAStrand testDNA = new DNAStrand("TestPattern", testPattern);
		DNAStrand compDNA = testDNA.compStrand();
		
		System.out.println(fqp.patternSearch(testPattern, 249));
		System.out.println(fqp.patternSearch(testDNA, 249));
		
		System.out.println(testDNA.getData());
		System.out.println(compDNA.getData());
		window.drawStrand(fqp.getStrand(1));
	}
	
	public static void startGUI() {
		window = new GraphicsInterface();
	}
}
