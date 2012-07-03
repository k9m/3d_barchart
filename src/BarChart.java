import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.JComponent;



/**
 * Class for representing a 3D Barchart at user's request. 
 * The colours of the Bars are randomly generated whenever the object 
 * is created and the Chart can dynamically expand horizontally along with
 * the frame however the height of the bar is always 2 units taller than the 
 * tallest Bar itself.
 * @author 1000999m
 *
 */
public class BarChart extends JComponent{

	//******************************************************
	//*****************CONSTANTS****************************
	//******************************************************

	//Bars
	//////
	/**Proportion in pixels representing 1 unit*/
	private static final int	barProport = 1;

	/**Proportion in pixels representing 1 unit*/
	private int	barUnit;

	private int	adjust;

	/**Width of the Bar*/
	private static final int barWidth = 15;

	/**Gap between bars including barWidth*/
	private static final int	gapBars = 25;

	/** Diagonal Edge in 3D*/
	private static final int dia = 5;

	//Chart
	///////
	/** Gap between active Area and edge of Frame */
	private static final int edge = 40;

	/** Gap between Edge of the Frame and 1st Bar */
	private static final int gap1stBar = 45;

	//Color Constants
	/////////////////
	/** Color of edge of the bar */
	private static final Color edgeBar = new Color(50,50,50);

	/** Color of background of the Chart */
	private static final Color bgChart = new Color(245,222,179);

	/** Color of background of the Chart */
	private static final Color fontChart = new Color(0,0,0);

	/** Color of background of the Chart */
	private static final Color lineChart = new Color(0,0,0);

	/** Color of Scales on Chart */
	private static final Color scalesChart = new Color(90,90,90);

	/** Color Difference between Sides of Bar */ 
	private static final int diff = 30;

	//Fonts
	///////
	/**Font for JLabels */
	private static final Font fontBars = new Font("Calibiri", Font.BOLD, 16);
	/**Font for JTextFields */
	private static final Font fontScale = new Font("Calibiri", Font.BOLD, 12);		


	//########################################################
	//################# VARIABLES#############################
	//########################################################

	//Color
	///////
	/** Color of background of the Chart */
	private Color bgPan;

	//Bar Colors
	////////////
	/** Array of random colors for front of Bars */
	private Color[] rndColor;

	/** Array of random colors for side of Bars */
	private Color[] rndColorSide;

	/** Array of random colors for top of Bars */
	private Color[] rndColorTop;	

	//Data
	//////
	/** Values of each Bars */	
	private int[] value;

	/** Actual number of Elements */
	private int actNumElements;

	/** List of actual Bar Identifiers */
	private String[] idList;


	/**
	 * Default Constructor for Barchart sets up the arrays and
	 * and creates random colors for BarChart.
	 */
	public BarChart(int[] barValues, String[] listOfIDs)
	{			
		//bgPan = bg;
		this.actNumElements = listOfIDs.length;
		this.value = barValues;
		this.idList = listOfIDs;

		rndColor = new Color[actNumElements];
		rndColorSide = new Color[actNumElements];
		rndColorTop = new Color[actNumElements];	

		//RGB color Variables for randomising colors
		Random rnd = new Random();
		int rndColorR = 0; 
		int rndColorG = 0;
		int rndColorB = 0;

		//Setting up random colors for the bars
		for (int i=0; i<actNumElements; i++){			
			rndColorR = diff+Math.abs(rnd.nextInt())%(250-2*diff);			
			rndColorG = diff+Math.abs(rnd.nextInt())%(250-2*diff);			
			rndColorB = diff+Math.abs(rnd.nextInt())%(250-2*diff);			      

			//Colors for front
			rndColor[i] = new Color(rndColorR,rndColorG,rndColorB);
			//Colors for side
			rndColorSide[i] = new Color(rndColorR-diff,rndColorG-diff,rndColorB-diff);
			//Colors for top
			rndColorTop[i] = new Color(rndColorR+diff,rndColorG+diff,rndColorB+diff);
		}



	}

	@Override
	public void setBackground(Color bg)
	{
		//this();				
		bgPan = bg;
	}	

	public Color[] getBarColors(){
		return rndColor;
	}

	public void setUnit(int barUnit)
	{
		this.barUnit = barUnit;
	}

	public void setZoom(int zoom)
	{
		this.adjust = zoom;
		for (int i=0; i<actNumElements; i++)			
			value[i] = (value[i] / (adjust * adjust));
	}


	public int getMax(){
		//Length of the longest bar, initially 1 unit
		int maxBarLength = barProport;		
		//Setting up maximum scale for X axis
		for (int i=0; i<actNumElements; i++)
			if (barProport*value[i] > maxBarLength ){
				maxBarLength=barProport*value[i];				
			}

		return maxBarLength;

	}



	/**
	 * Draws the Bar Chart
	 */
	public void paintComponent(Graphics g) {

		//actNumRefs = refLBar.countRefs();
		//value = refLBar.getNrMatchesList();

		Graphics2D g2 = (Graphics2D) g;		

		int maxBarLength = getHeight();		
		int maxNrMatch = getHeight() - 50; 

		g2.setBackground(bgPan);		
		g2.clearRect(0, 0, getWidth(), getHeight());

		//Drawing Chart//
		/////////////////
		drawChart(g2, maxBarLength, maxNrMatch);

		//Drawing Bars//
		////////////////
		drawBars(g2, maxBarLength, maxNrMatch);

		//Drawing Bars//
		////////////////
		drawLabels(g2);
	}	

	/**
	 * Draws the labels for the Bars
	 * @param passg2  Graphics2D object for paintComponent method
	 */
	public void drawLabels(Graphics2D passg2)
	{
		/*Graphics2D g2 = passg2;



		//Bottom left corner of first label
		int lCornIDX = gap1stBar;
		int lCornIDY = this.getHeight()-edge;

		//Setting color and font
		g2.setColor(fontChart);
		g2.setFont(fontBars);

		//Drawing Labels
		for (int i=0; i<actNumElements; i++){
			g2.drawString(idList[i], lCornIDX+(i*gapBars), lCornIDY+20);
		}*/
	}

	/**
	 * Draws the BarChart
	 * @param passg2			Graphics2D object for paintComponent method
	 * @param maxBarLength		Length of the tallest Bar
	 * @param maxNrMatch		Maximum value
	 */
	public void drawChart(Graphics2D passg2, int maxBarLength, int maxNrMatch)
	{
		Graphics2D g2 = passg2;

		int xAxStart = this.getHeight()-edge; 	//Active height
		int yAxStart = this.getWidth()-edge;	//Active width	

		//Drawing Background
		////////////////////
		{
			//Co-ords of Shape of background
			int topxA = edge;
			int topyA = xAxStart;			
			int topxB = edge;
			int topyB = xAxStart-(barProport*(maxNrMatch+2));					
			int topxC = edge+dia;
			int topyC = (xAxStart-(barProport*(maxNrMatch+2)))-dia;			
			int topxD = yAxStart;			
			int topyD = xAxStart-barProport*(maxNrMatch+2)-dia;
			int topxE = yAxStart;			
			int topyE = xAxStart-dia;
			int topxF = yAxStart-dia;			
			int topyF = xAxStart;

			//Filling Background shape
			int topX6[] = {topxA, topxB, topxC, topxD, topxE, topxF};
			int topY6[] = {topyA, topyB, topyC, topyD, topyE, topyF};
			g2.setColor(bgChart);			
			g2.fillPolygon(topX6, topY6, topX6.length);
		}

		//Drawing Y axis
		////////////////
		g2.setColor(lineChart);
		g2.drawLine(edge, xAxStart, edge, (xAxStart-(barProport*(maxNrMatch+2))));
		g2.drawLine(edge-1, xAxStart, edge-1, (xAxStart-(barProport*(maxNrMatch+2))));

		//Drawing X axis
		////////////////
		g2.drawLine(edge, xAxStart, yAxStart-dia, xAxStart);
		g2.drawLine(edge-1, xAxStart+1, yAxStart-dia, xAxStart+1);

		//Drawing Shadow axis'
		//////////////////////

		//Drawing Shadow Y axis
		g2.setColor(scalesChart);
		g2.drawLine(edge+dia, xAxStart-dia, edge+dia, xAxStart-(barProport*(maxNrMatch+2))-dia);
		g2.drawLine(edge, xAxStart, edge+dia, xAxStart-dia);
		//Drawing Shadow X axis
		g2.drawLine(edge+dia, xAxStart-dia, yAxStart, xAxStart-dia);		

		//Drawing Scales
		////////////////		
		//Scales
		g2.setFont(fontScale);
		
		for (int i=1; i<=(maxNrMatch); i++){			
			//Every 2 unit
			if (i % (barUnit / adjust) == 0){
				//int r = (barUnit * exm) - (i * adjust);
				int r = 0;
				//Line
				g2.drawLine(edge+dia, xAxStart-barProport*i-dia + (r * adjust), yAxStart, xAxStart-barProport*i-dia + (r * adjust));
				g2.drawLine(edge, xAxStart-barProport*i + (r * adjust), edge+dia, xAxStart-barProport*i-dia + (r * adjust));			
				
				//Scale				
				//g2.drawString(String.format("%3d", i * adjust * 2), edge-18, xAxStart-barProport*i);
			}			
		}	

		//Drawing Edges of Chart
		////////////////////////		
		g2.setColor(lineChart);
		//Y axis Left Diagonal
		g2.drawLine(edge, (xAxStart-(barProport*(maxNrMatch+2))), edge+dia, (xAxStart-(barProport*(maxNrMatch+2)))-dia);
		g2.drawLine(edge, (xAxStart-(barProport*(maxNrMatch+2)))-1, edge+dia, (xAxStart-(barProport*(maxNrMatch+2)))-dia-1);		
		//X axis Horizontal
		g2.drawLine(edge+dia, xAxStart-barProport*(maxNrMatch+2)-dia, yAxStart, xAxStart-barProport*(maxNrMatch+2)-dia);
		g2.drawLine(edge+dia, xAxStart-barProport*(maxNrMatch+2)-dia-1, yAxStart, xAxStart-barProport*(maxNrMatch+2)-dia-1);		
		//Y axis Right Vertical		
		g2.drawLine(yAxStart, xAxStart-barProport*(maxNrMatch+2)-dia, yAxStart, xAxStart-dia);
		g2.drawLine(yAxStart-1, xAxStart-barProport*(maxNrMatch+2)-dia, yAxStart-1, xAxStart-dia);		
		//Y axis Right Diagonal
		g2.drawLine(yAxStart, xAxStart-dia, yAxStart-dia, xAxStart);
		g2.drawLine(yAxStart, xAxStart-dia+1, yAxStart-dia, xAxStart+1);


		//Drawing Scale
		////////////////
		g2.setColor(fontChart);			
		int exp = 1;
		for (int i=1; i <= maxNrMatch; i++){

			int q = i % (barUnit / adjust);
			if (q == 0){
				int r = (barUnit * exp) - (i * adjust);				
				g2.drawString(String.format("%3d",  i * adjust  * adjust + adjust * r), 5, xAxStart-barProport*i);
				exp++;

			}
		}
		//Drawing 0			
		g2.drawString(String.format("%3d", 0), 5, xAxStart);

		//Drawing TOP
		//g2.drawString(String.format("%3d", maxNrMatch * adjust * adjust), edge-18, xAxStart-barProport*maxNrMatch);


	}

	/**
	 * Draws the Bars in the BarChart
	 * @param passg2			Graphics2D object for paintComponent method
	 * @param maxBarLength		Length of the tallest Bar
	 * @param maxNrMatch		Maximum value
	 */
	public void drawBars(Graphics2D passg2, int maxBarLength, int maxNrMatch)
	{


		Graphics2D g2 = passg2;

		Rectangle[] bars = new Rectangle[actNumElements];	//Array for Bars

		//Bottom left corner of first Bar
		int lCornBarX = gap1stBar;
		int lCornBarY = this.getHeight()-edge;

		for (int i=0; i<actNumElements; i++){		

			//Drawing front of Bar
			//////////////////////
			//Ceating rectangle for Bar			
			bars[i] = new Rectangle(lCornBarX+(i*gapBars), lCornBarY-(barProport*value[i]), barWidth, barProport*value[i]);
			//Filling Rectangle with color
			g2.setColor(rndColor[i]);
			g2.fill(bars[i]);
			g2.setColor(edgeBar);
			g2.draw(bars[i]);

			//Drawing Top of Bar
			////////////////////
			//Coordinates of 4 corners of the Top
			int topxA = lCornBarX+(i*gapBars);
			int topyA = lCornBarY-(barProport*value[i]);			
			int topxB = topxA+barWidth;
			int topyB = topyA;					
			int topxC = topxB+dia;
			int topyC = topyB-dia;			
			int topxD = topxC-barWidth;			
			int topyD = topyC;

			//Drawing Top
			int topX4[] = {topxA, topxB, topxC, topxD};
			int topY4[] = {topyA, topyB, topyC, topyD};

			//Filling Top with colors
			g2.setColor(rndColorTop[i]);			
			g2.fillPolygon(topX4, topY4, topX4.length);
			g2.setColor(edgeBar);
			g2.drawPolygon(topX4, topY4, topX4.length);

			//Drawing Side of Bar
			/////////////////////
			int sidexA = topxB;
			int sideyA = topyB;

			int sidexB = sidexA+dia;
			int sideyB = sideyA-dia;

			int sidexC = sidexB;
			int sideyC = sideyA+((barProport*value[i])-dia);

			int sidexD = sidexC-dia;			
			int sideyD = sideyC+dia;

			//Drawing Top
			int sideX4[] = {sidexA, sidexB, sidexC, sidexD};
			int sideY4[] = {sideyA, sideyB, sideyC, sideyD};

			//Filling Side with colors			
			g2.setColor(rndColorSide[i]);			
			g2.fillPolygon(sideX4, sideY4, sideX4.length);
			g2.setColor(edgeBar);
			g2.drawPolygon(sideX4, sideY4, sideX4.length);
		}
	}
}
