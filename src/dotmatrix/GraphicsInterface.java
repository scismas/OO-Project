package dotmatrix;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class GraphicsInterface {
	private JFrame window;
	
	private JPanel drawingSheet;
	private JPanel topPanel;
	private JPanel buttonPanel;
	
	private BufferedImage bufferedImage;
	Graphics2D bufferedImageGraphics;
	
	private GridLayout curLayout;
	
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
		
		JButton button;
		JLabel label;
		JTextField textField;
		
		label = new JLabel("DNA Strand ID:");
		label.setFont(new Font("Times New Roman", Font.BOLD, 38));
		label.setBounds(30,  0, 300, 100);
		buttonPanel.add(label);
		
		textField = new JTextField(30);
		textField.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		textField.setBounds(320, 25, 450, 50);
		buttonPanel.add(textField);
		
		button = new JButton("Display");
		button.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		button.setBounds(780, 25, 140, 50);
		buttonPanel.add(button);
		
		button = new JButton("-");
		button.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		button.setBounds(870, 90, 50, 50);
		buttonPanel.add(button);
		
		button = new JButton("+");
		button.setFont(new Font("Times New Roman", Font.BOLD, 28));
		button.setBounds(810, 90, 50, 50);
		buttonPanel.add(button);
		
		button = new JButton("?");
		button.setFont(new Font("Times New Roman", Font.BOLD, 30));
		button.setBounds(750, 90, 50, 50);
		buttonPanel.add(button);

	}
	
	public void addMouseEventHandler() {	//To test coordinates and dynamic drawing
	    drawingSheet.addMouseListener(new MouseAdapter() {
	      public void mouseClicked(MouseEvent e) {
	        Point p = drawingSheet.getMousePosition();
	        System.out.println("(" +p.getX() +", " +p.getY() +")");
		  }
	    });
	}
	
	public void drawStrand(DNAStrand strand) { //Draws entire strand, both sides
		int baseX = 350;
		int baseY = 30;
		DNAStrand temp = strand.compStrand();
		for (int i = 0; i < strand.getLength(); ++i) {
			Rectangle2D line = new Rectangle2D.Float(baseX + 120, baseY + 20 + 60 * i, 10, 5);
			bufferedImageGraphics.fill(line);
			bufferedImageGraphics.draw(line);
			drawBase(baseX, baseY + 60 * i, strand.getData().charAt(i));
			drawBase(baseX + 130, baseY + 60 * i, temp.getData().charAt(i));
		}
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
}
