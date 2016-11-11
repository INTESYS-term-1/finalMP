import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import net.miginfocom.swing.MigLayout;

/**********************************
 * This is the main class of a Java program to play a game based on hexagonal
 * tiles. The mechanism of handling hexes is in the file hexmech.java.
 * 
 * Written by: M.H. Date: December 2012
 * 
 ***********************************/

public class hexgame {
	public hexgame() {
		initGame();
		createAndShowGUI();
	}

	// constants and global variables
	private final static Color COLOURBACK = Color.WHITE;
	private final static Color COLOURCELL = Color.DARK_GRAY;
	private final static Color COLOURGRID = Color.BLACK;
	private final static Color COLOURONE = Color.green;
	private final static Color COLOURONETXT = Color.WHITE;

	private final static Color COLOURTWO = Color.RED;
	private final static Color COLOURTWOTXT = Color.WHITE;

	private final static Color COLOURTHREE = Color.lightGray;
	private final static Color COLOURTHREETXT = Color.WHITE;

	// final static Color COLOURTWO = new Color(0,0,0,200);

	private final static int EMPTY = 1;
	private final static int BSIZE = 8; // board size.
	private final static int HEXSIZE = 75; // hex size in pixels
	private final static int BORDERS = 15;
	private final static int SCRSIZE = HEXSIZE * (BSIZE + 1) + BORDERS * 3; // screen
	// size
	// (vertical
	// dimension).

	// variables natin
	private JSplitPane splitPane;
	private JPanel lblSheepsPerPlaye = new JPanel();

	private int numberOfSheepsPerPlayer = 0;
	private Boolean isHolding = false;
	private int holding = 0;

	private int player = -1;
	private int free = 0;
	private int ai = 1;

	private GuiCell[][] board = new GuiCell[BSIZE][BSIZE];
	private final JLabel lblBattlesheepDashboard = new JLabel("BattleSheep Dashboard");
	private final JLabel lblSheepsPlayer = new JLabel("Sheeps per player:");
	private final JLabel lblSheepsPerPlayer = new JLabel("n");
	private final JLabel lblSheepAtHand = new JLabel("Sheep at hand:");
	private final JLabel lblFromHexX = new JLabel("From hex x coord");
	private final JLabel lblFromHexY = new JLabel("From hex y coord");
	private final JLabel lblxcoord = new JLabel("New label");
	private final JLabel lblycoord = new JLabel("New label");
	private final JButton btnDone = new JButton("Done");

	void initGame() {

		setXYasVertex(false); // RECOMMENDED: leave this as FALSE.

		setHeight(HEXSIZE); // Either setHeight or setSize must be run
							// to initialize the hex
		setBorders(BORDERS);

		for (int i = 0; i < BSIZE; i++) {
			for (int j = 0; j < BSIZE; j++) {
				board[i][j] = new GuiCell(i, j, 0, free);
			}
		}

		initializeSheeps();
		
		board[2][2] = new GuiCell(2, 2, 5, ai);

		
		GuiCell[][] temp = new GuiCell[BSIZE][BSIZE];
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++)
				temp[i][j] = board[i][j];
	
		State state = new State(temp, null, ai, 0);

		System.out.println("doen");

		System.out.println("size: " + state.generateStates().size());

		// initialize player's sheeps

		// set up board here
		// even x point of reference
		// board[0][1] = 'B';
		// board[1][0] = 'A';
		// board[1][1] = 'C';
		// board[2][1] = 'D';
		// //odd
		// board[1][2] = 'B';
		// board[2][3] = 'A';
		// board[2][2] = 'C';
		// board[3][2] = 'D';

	}

	public void initializeSheeps() {
		// initialize sheep
		numberOfSheepsPerPlayer = Integer
				.parseInt(JOptionPane.showInputDialog(null, "Total number of sheeps per player", "Welcome", 2));
		lblSheepsPerPlayer.setText(Integer.toString(numberOfSheepsPerPlayer));
		initializePlayerSheeps();
	}

	public void initializePlayerSheeps() {
		int initialX = Integer.parseInt(
				JOptionPane.showInputDialog(null, "X coordinate:", "Where do you want to put all your sheep?", 2));
		int initialY = Integer.parseInt(
				JOptionPane.showInputDialog(null, "Y coordinate:", "Where do you want to put all your sheep?", 2));

		board[initialX][initialY] = new GuiCell(initialX, initialY, numberOfSheepsPerPlayer, player);
	}

	private void createAndShowGUI() {
		DrawingPanel panel = new DrawingPanel();

		// JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Hex Testing 4");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(panel),
				new JScrollPane(lblSheepsPerPlaye));
		lblSheepsPerPlaye.setLayout(new MigLayout("", "[][][]", "[][][][][][][][][][]"));
		lblBattlesheepDashboard.setFont(new Font("Tahoma", Font.BOLD, 13));

		lblSheepsPerPlaye.add(lblBattlesheepDashboard, "cell 0 0");

		lblSheepsPerPlaye.add(lblSheepsPlayer, "cell 0 1");

		lblSheepsPerPlaye.add(lblSheepsPerPlayer, "cell 1 1");

		lblSheepsPerPlaye.add(lblSheepAtHand, "cell 0 3");

		lblSheepsPerPlaye.add(lblFromHexX, "cell 0 4");

		lblSheepsPerPlaye.add(lblxcoord, "cell 1 4");

		lblSheepsPerPlaye.add(lblFromHexY, "cell 0 5");

		lblSheepsPerPlaye.add(lblycoord, "cell 1 5,alignx left");

		lblSheepsPerPlaye.add(btnDone, "cell 0 9,alignx center");
		splitPane.setDividerLocation(550);

		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("click");
			}
		});

		Container content = frame.getContentPane();
		content.add(splitPane);
		// this.add(panel); -- cannot be done in a static context
		// for hexes in the FLAT orientation, the height of a 10x10 grid is
		// 1.1764 * the width. (from h / (s+t))
		frame.setSize((int) (SCRSIZE / .9), SCRSIZE);

		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	class DrawingPanel extends JPanel {
		// mouse variables here
		// Point mPt = new Point(0,0);

		public DrawingPanel() {
			setBackground(COLOURBACK);

			MyMouseListener ml = new MyMouseListener();
			addMouseListener(ml);
		}

		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setFont(new Font("Consolas", Font.BOLD, 20));
			super.paintComponent(g2);
			// draw grid
			for (int i = 0; i < BSIZE; i++) {
				for (int j = 0; j < BSIZE; j++) {
					drawHex(i, j, g2);
				}
			}
			// fill in hexes
			for (int i = 0; i < BSIZE; i++) {
				for (int j = 0; j < BSIZE; j++) {
					// if (board[i][j] < 0)
					// hexmech.fillHex(i,j,COLOURONE,-board[i][j],g2);
					// if (board[i][j] > 0) hexmech.fillHex(i,j,COLOURTWO,
					// board[i][j],g2);

					fillHex(i, j, Integer.toString(board[i][j].getValue()), g2);
				}
			}

			// g2.setColor(Color.RED);
			// g.drawLine(mPt.x,mPt.y, mPt.x,mPt.y);
		}

		class MyMouseListener extends MouseAdapter { // inner class inside
														// DrawingPanel
			public void mouseClicked(MouseEvent e) {
				// mPt.x = x;
				// mPt.y = y;
				Point p = new Point(pxtoHex(e.getX(), e.getY()));
				if (p.x < 0 || p.y < 0 || p.x >= BSIZE || p.y >= BSIZE)
					return;

				// DEBUG: colour in the hex which is supposedly the one clicked
				// on
				// clear the whole screen first.
				/*
				 * for (int i=0;i<BSIZE;i++) { for (int j=0;j<BSIZE;j++) {
				 * board[i][j]=EMPTY; } }
				 */

				// What do you want to do when a hexagon is clicked?

				// //even x point of reference
				// board[0][1] = 'A';
				// board[1][0] = 'B';
				// board[1][1] = 'C';
				// board[2][1] = 'D';
				// //odd
				// board[1][2] = 'A';
				// board[2][3] = 'B';
				// board[2][2] = 'C';
				// board[3][2] = 'D';
				try {
					// if(p.x>BSIZE-2) {

					if (board[p.x][p.y].getOwner() == player && isHolding == false) {

						holding = Integer.parseInt(
								JOptionPane.showInputDialog("How many sheeps would you like to get from here?", "0"));

						if (board[p.x][p.y].getValue() - holding < 1) {
							JOptionPane.showMessageDialog(null, "You should leave at least one 1 sheep");
							holding = 0;
						} else {// free cell
							isHolding = true;
							lblxcoord.setText(Integer.toString(p.x));
							lblycoord.setText(Integer.toString(p.y));
							board[p.x][p.y].setValue(board[p.x][p.y].getValue() - holding);
							lblSheepAtHand.setText(Integer.toString(holding));
						}

					} else if ((board[p.x][p.y].getOwner() == player || board[p.x][p.y].getOwner() == ai)
							&& isHolding == true) {
						JOptionPane.showMessageDialog(null, "Please select a hex that is free");
					} else if (isHolding == false) {
						JOptionPane.showMessageDialog(null, "Please select sheeps first");

					}

					else if (board[p.x][p.y].getOwner() == free && isHolding == true) {
						board[p.x][p.y] = new GuiCell(p.x, p.y, holding, player);
						isHolding = false;
						holding = 0;
						lblSheepAtHand.setText("none");
						lblxcoord.setText("none");
						lblycoord.setText("none");
					}

					else if (board[p.x][p.y].getOwner() == ai) {
						JOptionPane.showMessageDialog(null, "You cannot select sheeps that aren't yours");
					}

					else if (board[p.x][p.y].getOwner() == ai && isHolding == true) {
						JOptionPane.showMessageDialog(null, "You cannot add sheeps to hex that are not yours.");
					}

					// board[p.x][p.y] = "X";
					// board[p.x + 1][p.y] = "X";
					// board[p.x + 1][p.y - 1] = "X";
					// board[p.x + 2][p.y] = "X";
					// }

				} catch (IndexOutOfBoundsException e1) {
					System.out.println("caught exception at hexagon: " + p.x + " " + p.y + "value: " + board[p.x][p.y]);
				}

				repaint();
			}

		} // end of MyMouseListener class
	} // end of DrawingPanel class

	///////////////////////

	public final static boolean orFLAT = true;
	public final static boolean orPOINT = false;
	public static boolean ORIENT = orFLAT; // this is not used. We're never
											// going to do pointy orientation

	public static boolean XYVertex = true; // true: x,y are the co-ords of the
											// first vertex.
	// false: x,y are the co-ords of the top left rect. co-ord.

	private static int BORDERS1 = 50; // default number of pixels for the
										// border.

	private static int s = 0; // length of one side
	private static int t = 0; // short side of 30o triangle outside of each hex
	private static int r = 0; // radius of inscribed circle (centre to middle of
								// each side). r= h/2
	private static int h = 0; // height. Distance between centres of two
								// adjacent hexes. Distance between two opposite
								// sides in a hex.

	public static void setXYasVertex(boolean b) {
		XYVertex = b;
	}

	public static void setBorders(int b) {
		BORDERS1 = b;
	}

	/**
	 * This functions takes the Side length in pixels and uses that as the basic
	 * dimension of the hex. It calculates all other needed constants from this
	 * dimension.
	 */
	public static void setSide(int side) {
		s = side;
		t = (int) (s / 2); // t = s sin(30) = (int) CalculateH(s);
		r = (int) (s * 0.8660254037844); // r = s cos(30) = (int) CalculateR(s);
		h = 2 * r;
	}

	public static void setHeight(int height) {
		h = height; // h = basic dimension: height (distance between two adj
					// centresr aka size)
		r = h / 2; // r = radius of inscribed circle
		s = (int) (h / 1.73205); // s = (h/2)/cos(30)= (h/2) / (sqrt(3)/2) = h /
									// sqrt(3)
		t = (int) (r / 1.73205); // t = (h/2) tan30 = (h/2) 1/sqrt(3) = h / (2
									// sqrt(3)) = r / sqrt(3)
	}

	/*********************************************************
	 * Name: hex() Parameters: (x0,y0) This point is normally the top left
	 * corner of the rectangle enclosing the hexagon. However, if XYVertex is
	 * true then (x0,y0) is the vertex of the top left corner of the hexagon.
	 * Returns: a polygon containing the six points. Called from: drawHex(),
	 * fillhex() Purpose: This function takes two points that describe a hexagon
	 * and calculates all six of the points in the hexagon.
	 *********************************************************/
	public static Polygon hex(int x0, int y0) {

		int y = y0 + BORDERS1;
		int x = x0 + BORDERS1; // + (XYVertex ? t : 0); //Fix added for XYVertex
								// = true.
		// NO! Done below in cx= section
		if (s == 0 || h == 0) {
			System.out.println("ERROR: size of hex has not been set");
			return new Polygon();
		}

		int[] cx, cy;

		// I think that this XYvertex stuff is taken care of in the int x line
		// above. Why is it here twice?
		if (XYVertex)
			cx = new int[] { x, x + s, x + s + t, x + s, x, x - t }; // this is
																		// for
																		// the
																		// top
																		// left
																		// vertex
																		// being
																		// at
																		// x,y.
																		// Which
																		// means
																		// that
																		// some
																		// of
																		// the
																		// hex
																		// is
																		// cutoff.
		else
			cx = new int[] { x + t, x + s + t, x + s + t + t, x + s + t, x + t, x }; // this
																						// is
																						// for
																						// the
																						// whole
																						// hexagon
																						// to
																						// be
																						// below
																						// and
																						// to
																						// the
																						// right
																						// of
																						// this
																						// point

		cy = new int[] { y, y, y + r, y + r + r, y + r + r, y + r };
		return new Polygon(cx, cy, 6);

		/*
		 * x=200; poly = new Polygon(); poly.addPoint(x,y);
		 * poly.addPoint(x+s,y); poly.addPoint(x+s+t,y+r);
		 * poly.addPoint(x+s,y+r+r); poly.addPoint(x,y+r+r);
		 * poly.addPoint(x-t,y+r);
		 */
	}

	/********************************************************************
	 * Name: drawHex() Parameters: (i,j) : the x,y coordinates of the inital
	 * point of the hexagon g2: the Graphics2D object to draw on. Returns: void
	 * Calls: hex() Purpose: This function draws a hexagon based on the initial
	 * point (x,y). The hexagon is drawn in the colour specified in
	 * hexgame.COLOURELL.
	 *********************************************************************/
	public void drawHex(int i, int j, Graphics2D g2) {
		int x = i * (s + t);
		int y = j * h + (i % 2) * h / 2;
		Polygon poly = hex(x, y);
		g2.setColor(hexgame.COLOURCELL);
		// g2.fillPolygon(hexmech.hex(x,y));
		g2.fillPolygon(poly);
		g2.setColor(hexgame.COLOURGRID);
		g2.drawPolygon(poly);
	}

	/***************************************************************************
	 * Name: fillHex() Parameters: (i,j) : the x,y coordinates of the initial
	 * point of the hexagon n : an integer number to indicate a letter to draw
	 * in the hex g2 : the graphics context to draw on Return: void Called from:
	 * Calls: hex() Purpose: This draws a filled in polygon based on the
	 * coordinates of the hexagon. The colour depends on whether n is negative
	 * or positive. The colour is set by hexgame.COLOURONE and
	 * hexgame.COLOURTWO. The value of n is converted to letter and drawn in the
	 * hexagon.
	 *****************************************************************************/
	public void fillHex(int i, int j, String n, Graphics2D g2) {
		String c;
		int x = i * (s + t);
		int y = j * h + (i % 2) * h / 2;
		if (board[i][j].getOwner() == -1) {
			g2.setColor(hexgame.COLOURONE);
			g2.fillPolygon(hex(x, y));
			g2.setColor(hexgame.COLOURONETXT);
			c = n;
			g2.drawString(c, x + r + BORDERS1, y + r + BORDERS1 + 4); // FIXME:
																		// handle
																		// XYVertex
			// g2.drawString(x+","+y, x+r+BORDERS1, y+r+BORDERS1+4);
		}

		else if (board[i][j].getOwner() == 1) {
			g2.setColor(hexgame.COLOURTWO);
			g2.fillPolygon(hex(x, y));
			g2.setColor(hexgame.COLOURTWOTXT);
			c = n;
			g2.drawString(c, x + r + BORDERS1, y + r + BORDERS1 + 4); // FIXME:
																		// handle
																		// XYVertex
			// g2.drawString(x+","+y, x+r+BORDERS1, y+r+BORDERS1+4);
		}

		else if (board[i][j].getOwner() == 0) {
			g2.setColor(hexgame.COLOURTHREE);
			g2.fillPolygon(hex(x, y));
			g2.setColor(hexgame.COLOURTHREETXT);
			c = n;
			g2.drawString(c, x + r + BORDERS1, y + r + BORDERS1 + 4); // FIXME:
																		// handle
																		// XYVertex
			// g2.drawString(x+","+y, x+r+BORDERS1, y+r+BORDERS1+4);
		}
		/*
		 * if (n > 0) { g2.setColor(hexgame.COLOURTWO);
		 * g2.fillPolygon(hex(x,y)); g2.setColor(hexgame.COLOURTWOTXT); c =
		 * (char)n; g2.drawString(""+c, x+r+BORDERS1, y+r+BORDERS1+4); //FIXME
		 * handle XYVertex //g2.drawString(i+","+j, x+r+BORDERS1,
		 * y+r+BORDERS+4); }
		 */
	}

	// This function changes pixel location from a mouse click to a hex grid
	// location
	/*****************************************************************************
	 * Name: pxtoHex (pixel to hex) Parameters: mx, my. These are the
	 * co-ordinates of mouse click. Returns: point. A point containing the
	 * coordinates of the hex that is clicked in. If the point clicked is not a
	 * valid hex (ie. on the borders of the board, (-1,-1) is returned.
	 * Function: This only works for hexes in the FLAT orientation. The POINTY
	 * orientation would require a whole other function (different math). It
	 * takes into account the size of BORDERS1. It also works with XYVertex
	 * being True or False.
	 *****************************************************************************/
	public static Point pxtoHex(int mx, int my) {
		Point p = new Point(-1, -1);

		// correction for BORDERS1 and XYVertex
		mx -= BORDERS1;
		my -= BORDERS1;
		if (XYVertex)
			mx += t;

		int x = (int) (mx / (s + t)); // this gives a quick value for x. It
										// works only on odd cols and doesn't
										// handle the triangle sections. It
										// assumes that the hexagon is a
										// rectangle with width s+t (=1.5*s).
		int y = (int) ((my - (x % 2) * r) / h); // this gives the row easily. It
												// needs to be offset by h/2
												// (=r)if it is in an even
												// column

		/******
		 * FIX for clicking in the triangle spaces (on the left side only)
		 *******/
		// dx,dy are the number of pixels from the hex boundary. (ie. relative
		// to the hex clicked in)
		int dx = mx - x * (s + t);
		int dy = my - y * h;

		if (my - (x % 2) * r < 0)
			return p; // prevent clicking in the open halfhexes at the top of
						// the screen

		// System.out.println("dx=" + dx + " dy=" + dy + " > " + dx*r/t + " <");

		// even columns
		if (x % 2 == 0) {
			if (dy > r) { // bottom half of hexes
				if (dx * r / t < dy - r) {
					x--;
				}
			}
			if (dy < r) { // top half of hexes
				if ((t - dx) * r / t > dy) {
					x--;
					y--;
				}
			}
		} else { // odd columns
			if (dy > h) { // bottom half of hexes
				if (dx * r / t < dy - h) {
					x--;
					y++;
				}
			}
			if (dy < h) { // top half of hexes
				// System.out.println("" + (t- dx)*r/t + " " + (dy - r));
				if ((t - dx) * r / t > dy - r) {
					x--;
				}
			}
		}
		p.x = x;
		p.y = y;
		return p;
	}
}
