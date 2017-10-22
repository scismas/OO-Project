package dotmatrix;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class GraphicsInterface {
	private DNAStrand[] strandArr;
	private int curStrand = 0;
	private int totalStrands = 0;
	
	private JFrame window;
	
	private JPanel drawingSheet;
	private JPanel topPanel;
	private JPanel buttonPanel;
	
	private BufferedImage bufferedImage;
	Graphics2D bufferedImageGraphics;
	
	private GridLayout curLayout;
	
	private JLabel strandNum;
	private JLabel curStrandNum;
	private JFormattedTextField curField;
	private JTextField idField;
	private JButton displayButton;
	private JButton plusButton;
	private JButton minusButton;
	private JButton randButton;
	
	private final Color colA = new Color(237, 97, 97);
	private final Color colT = new Color(255, 243, 117);
	private final Color colC = new Color(66, 134, 244);
	private final Color colG = new Color(104, 226, 122);
	
	public GraphicsInterface() { //Creates window and adds all components to window
		window = new JFrame("DNA Sequence Analyzer");
		//Drawing/Images
	    bufferedImage = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);
	    bufferedImageGraphics = bufferedImage.createGraphics();
	    bufferedImageGraphics.setPaint(Color.black);
	    
	    createPanels();
	    
	    addComponents();

	    addMouseEventHandler();
	    
	    combinePanels();
	    
	    setUpWindow();
	}
	
	public void setUpWindow() { //Finalize window setup
		 window.setContentPane(topPanel);
		 drawingSheet.setOpaque(true);
		 window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 window.setSize(1920, 1080);
		 window.setResizable(true);
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 window.setLocation((screenSize.width - 1920)/2, (screenSize.height - 1080)/2);
		 window.setVisible(true);
	}
	
	public void createPanels() { //Initializing panels
		buttonPanel = new JPanel();
	    topPanel = new JPanel();
	    
	    drawingSheet = new JPanel() {
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
	      super.paintComponent(g);
	      Graphics2D myGraphics = (Graphics2D) g;
	      myGraphics.drawImage(bufferedImage, 0, 0, this);
	      }
	    }; 
	}
	
	public void combinePanels() {	//Combining all panels
		//Layouts
		curLayout = new GridLayout(1, 2);
	    topPanel.setLayout(curLayout);
	    
	    //Borders
		Border black = BorderFactory.createLineBorder(Color.black, 5);
		Border blue = BorderFactory.createLineBorder(Color.blue, 9);
	    drawingSheet.setBorder(black);
	    buttonPanel.setBorder(blue);
	    
	    //Adding components
	    topPanel.add(drawingSheet);
	    topPanel.add(buttonPanel);
	}
	
	public void addComponents() {	//Adds buttons etc to panels
		//I HATE LAYOUT MANAGERS THEY ALL SUCK UGH
		//I'm gonna forego layout managers and just do absolute positioning, makes 10000x more sense
		//I've spent an embarrassing amount of time trying to use gridlayout and gridbaglayout so rip me
		//thank god for spotify otherwise this would be the most boring thing in the history of ever. more boring than coen 160
		buttonPanel = new JPanel();
		buttonPanel.setLayout(null);
		
		JLabel label;
		//LABELS
		label = new JLabel("DNA Strand ID:");
		label.setFont(new Font("Times New Roman", Font.BOLD, 38));
		label.setBounds(30, 0, 300, 100);
		buttonPanel.add(label);
		
		strandNum = new JLabel("Total Strands: " + totalStrands);
		strandNum.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		strandNum.setBounds(30, 50, 300, 100);
		buttonPanel.add(strandNum);
		
		curStrandNum = new JLabel("Current Strand: ");
		curStrandNum.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		curStrandNum.setBounds(270, 50, 300, 100);
		buttonPanel.add(curStrandNum);
		
		//TEXTFIELDS
		idField = new JTextField(30);
		idField.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		idField.setBounds(320, 25, 450, 50);
		buttonPanel.add(idField);
		
		curField = new JFormattedTextField();
		curField.setColumns(3);
		curField.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		curField.setBounds(470, 80, 60, 40);
		buttonPanel.add(curField);
		
		//BUTTONS
		displayButton = new JButton("Display");
		displayButton.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		displayButton.setBounds(780, 25, 140, 50);
		displayButton.addActionListener(new ActionListener()
		{
			  public void actionPerformed(ActionEvent e)
			  {
			    System.out.println("Display");
			    String tempText = curField.getText();
			    int tempInt = Integer.parseInt(tempText);
			    if (tempInt > 0 && tempInt < strandArr.length) {
			    	drawStrand(tempInt);
			    }
			  }
			});
		buttonPanel.add(displayButton);
		
		minusButton = new JButton("-");
		minusButton.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		minusButton.setBounds(870, 90, 50, 50);
		minusButton.addActionListener(new ActionListener()
		{
			  public void actionPerformed(ActionEvent e)
			  {
			    if (curStrand != 0) {
			    	drawStrand(--curStrand);
			    	System.out.println("Previous Strand");
			    }
			  }
			});
		buttonPanel.add(minusButton);
		
		plusButton = new JButton("+");
		plusButton.setFont(new Font("Times New Roman", Font.BOLD, 28));
		plusButton.setBounds(810, 90, 50, 50);
		plusButton.addActionListener(new ActionListener()
		{
			  public void actionPerformed(ActionEvent e)
			  {
				if (curStrand != strandArr.length - 1) {
					 System.out.println("Next Strand");
					 drawStrand(++curStrand);
				}
			  }
			});
		buttonPanel.add(plusButton);
		
		randButton = new JButton("?");
		randButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
		randButton.setBounds(750, 90, 50, 50);
		randButton.addActionListener(new ActionListener()
		{
			  public void actionPerformed(ActionEvent e)
			  {
				Random rand = new Random();
			    System.out.println("Random Strand");
			    curStrand = rand.nextInt(strandArr.length);
			    drawStrand(curStrand);
			  }
			});
		buttonPanel.add(randButton);
	}
	
	public void updateUI() {
		strandNum.setText("Total Strands: " + totalStrands);
		curField.setText(Integer.toString(curStrand));
	}
	
	public void addMouseEventHandler() {	//To test coordinates and dynamic drawing
	    drawingSheet.addMouseListener(new MouseAdapter() {
	      public void mouseClicked(MouseEvent e) {
	        Point p = drawingSheet.getMousePosition();
	        System.out.println("(" +p.getX() +", " +p.getY() +")");
		  }
	    });
	}
	
	public void drawStrand(int i) { //Draws strand from the strand array inside this class
		drawStrand(strandArr[i]);
		curStrand = i;
	}
	
	public void drawStrand(DNAStrand strand) { //Draws entire strand, both sides
		int baseX = 350;
		int baseY = 30;
		DNAStrand temp = strand.compStrand();
		idField.setText(strand.getID());
		for (int i = 0; i < strand.getLength(); ++i) {
			Rectangle2D line = new Rectangle2D.Float(baseX + 120, baseY + 20 + 60 * i, 10, 5);
			bufferedImageGraphics.fill(line);
			bufferedImageGraphics.draw(line);
			drawBase(baseX, baseY + 60 * i, strand.getData().charAt(i));
			drawBase(baseX + 130, baseY + 60 * i, temp.getData().charAt(i));
		}
		updateUI();
	}
	
	public void drawBase(int x, int y, char base) { //Draws a single base with correct color
		Rectangle2D rect1 = new Rectangle2D.Float(x, y, 120, 40);
		
		switch (base) {
			case 'A':
				bufferedImageGraphics.setPaint(colA);
				break;
			case 'T':
				bufferedImageGraphics.setPaint(colT);
				break;
			case 'C':
				bufferedImageGraphics.setPaint(colC);
				break;
			case 'G':
				bufferedImageGraphics.setPaint(colG);
				break;
			default:
				bufferedImageGraphics.setPaint(Color.white);
				break;
		}
		
		bufferedImageGraphics.draw(rect1);
		bufferedImageGraphics.fill(rect1);
		
		bufferedImageGraphics.setFont(new Font("Times New Roman", Font.BOLD, 40));
		bufferedImageGraphics.setPaint(new Color(0, 0, 0));
		String temp = "" + base;
		bufferedImageGraphics.drawString(temp, x + 48, y + 34);
		window.repaint();
	}
	
	public void importStrandArr(DNAStrand[] strandArr) {
		this.strandArr = strandArr;
		this.totalStrands = strandArr.length;
	}
}
