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
		window.importStrandArr(fqp.getStrandArray());
		
		window.drawStrand(0);
	}
	
	public static void startGUI() {
		window = new GraphicsInterface();
	}
}
