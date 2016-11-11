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
	private hexgame() {
		initGame();
		createAndShowGUI();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new hexgame();
			}
		});
	}

	// constants and global variables
	final static Color COLOURBACK = Color.WHITE;
	final static Color COLOURCELL = Color.DARK_GRAY;
	final static Color COLOURGRID = Color.BLACK;
	final static Color COLOURONE = Color.green;
	final static Color COLOURONETXT = Color.WHITE;

	final static Color COLOURTWO = Color.RED;
	final static Color COLOURTWOTXT = Color.WHITE;

	final static Color COLOURTHREE = Color.lightGray;
	final static Color COLOURTHREETXT = Color.WHITE;

	// final static Color COLOURTWO = new Color(0,0,0,200);

	final static int EMPTY = 1;
	final static int BSIZE = 8; // board size.
	final static int HEXSIZE = 75; // hex size in pixels
	final static int BORDERS = 15;
	final static int SCRSIZE = HEXSIZE * (BSIZE + 1) + BORDERS * 3; // screen
																	// size
																	// (vertical
																	// dimension).

	// variables natin
	JSplitPane splitPane;
	JPanel lblSheepsPerPlaye = new JPanel();

	int numberOfSheepsPerPlayer = 0;
	Boolean isHolding = false;
	int holding = 0;

	int player = -1;
	int free = 0;
	int ai = 1;

	static GuiCell[][] board = new GuiCell[BSIZE][BSIZE];
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

		hexmech.setXYasVertex(false); // RECOMMENDED: leave this as FALSE.

		hexmech.setHeight(HEXSIZE); // Either setHeight or setSize must be run
									// to initialize the hex
		hexmech.setBorders(BORDERS);

		for (int i = 0; i < BSIZE; i++) {
			for (int j = 0; j < BSIZE; j++) {
				board[i][j] = new GuiCell(i, j, 0, free, true);
			}
		}

		initializeSheeps();

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

		board[initialX][initialY] = new GuiCell(initialX, initialY, numberOfSheepsPerPlayer, player, true);
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
					hexmech.drawHex(i, j, g2);
				}
			}
			// fill in hexes
			for (int i = 0; i < BSIZE; i++) {
				for (int j = 0; j < BSIZE; j++) {
					// if (board[i][j] < 0)
					// hexmech.fillHex(i,j,COLOURONE,-board[i][j],g2);
					// if (board[i][j] > 0) hexmech.fillHex(i,j,COLOURTWO,
					// board[i][j],g2);

					hexmech.fillHex(i, j, Integer.toString(board[i][j].getValue()), g2);
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
				Point p = new Point(hexmech.pxtoHex(e.getX(), e.getY()));
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
						board[p.x][p.y] = new GuiCell(p.x, p.y, holding, player, true);
						isHolding = false;
						holding = 0;
						lblSheepAtHand.setText("none");
						lblxcoord.setText("none");
						lblycoord.setText("none");
					}
					
					else if (board[p.x][p.y].getOwner() == ai) {
						JOptionPane.showMessageDialog(null, "You cannot select sheeps that aren't yours");
					}
					
					else if (board[p.x][p.y].getOwner() == ai && isHolding==true) {
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
}