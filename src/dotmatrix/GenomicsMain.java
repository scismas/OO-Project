//Parses data from a FASTQ file
//Can search for specific pattern sequence in a DNA strand
//Can create complementary strand from base strand
//Hope to do graphics interface for interaction with sequences


package dotmatrix;

import java.io.IOException;

public class GenomicsMain {
	public static void main(String[] args) throws IOException {
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
	}
}
