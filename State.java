import java.util.ArrayList;
import java.util.Arrays;

public class State {
	int bSize = 8;
	final GuiCell[][] boardz = new GuiCell[bSize][bSize];
	/* Static Variables */
	final static int player = -1;
	final static int free = 0;
	final static int ai = 1;
	// final static int MAX_SHEEP_TOKENS = 16;

	/* Essential Variables */
	State parentState;
	int currentTurn; // the current turn
	int score;

	int childrenLeft;

	int level;

	ArrayList<State> states;
	ArrayList<State> statesTemp = new ArrayList<State>();

	public static GuiCell[][] copyOf(GuiCell[][] original) {
		GuiCell[][] copy = new GuiCell[original.length][];
		for (int i = 0; i < original.length; i++) {
			copy[i] = Arrays.copyOf(original[i]);
		}
		return copy;
	}

	public State(GuiCell[][] boardz, State parent, int nextTurn, int level) {

		// for (int i = 0; i < bSize; i++) {
		// for (int j = 0; j < bSize; j++) {
		// this.board[i][j].setValue(board[i][j].getValue());
		// this.board[i][j].setX(i);
		// this.board[i][j].setY(j);
		// this.board[i][j].setOwner(board[i][j].getOwner());
		// }
		// }

		// lifesave etong copy array
		for (int i = 0; i < boardz.length; i++)
			for (int j = 0; j < boardz[i].length; j++)
				this.boardz[i][j] = boardz[i][j];

		this.parentState = parent;
		this.currentTurn = nextTurn;
		this.level = level;
		childrenLeft = level;

	}

	// @Override
	// public boolean equals(Object o) {
	// State s = (State) o;
	//
	// return true;
	// }

	// function to get board
	public GuiCell[][] getBoard() {
		return this.boardz;
	}

	public int getLevel() {
		return level;
	}

	@Override
	public boolean equals(Object o) {
		State s = (State) o;
		for (int i = 0; i < bSize; i++) {
			for (int j = 0; j < bSize; j++) {
				if (this.boardz[i][j].getValue() == s.getBoard()[i][j].getValue()) {
					return false;
				}

			}
		}

		return true;
	}

	// public boolean contains(ArrayList<GuiCell[][]> boards, GuiCell
	// currentState) {
	//
	// for (int a = 0; a < boards.size(); a++) {
	// if (!boards.get(a).equals(currentState)) {
	// return false;
	// }
	// }
	// return true;
	//
	// }

	public void computeScore() {

		System.out.println("nagcomputer score");

		int sum = 0;
		for (int i = 0; i < bSize; i++) {
			for (int j = 0; j < bSize; j++) {
				if (boardz[i][j].getOwner() == ai) {
					sum++;
				}
			}
		}

		score = sum;

		propagateScore();
	}

	public void propagateScore() {
		if (parentState != null && childrenLeft == 0)
			parentState.submit(this);
	}

	public void submit(State s) {
		if (currentTurn == player) {
			score = Math.min(score, s.getScore());
		} else {
			score = Math.max(score, s.getScore());
		}
		childrenLeft--;
		propagateScore();
	}

	public int getScore() {
		return score;
	}

	public void print(GuiCell[][] dummy) {

		System.out.println("tite");
		for (int j = 0; j < bSize; j++) {
			System.out.println();
			for (int z = 0; z < bSize; z++) {
				System.out.print(dummy[j][z].getValue());
				System.out.print(" | ");

			}

		}
	}

	public ArrayList<State> generateStates() {

		ArrayList<State> states = new ArrayList<State>();

		for (int i = 0; i < bSize; i++) {
			for (int j = 0; j < bSize; j++) {

				if (boardz[i][j].getOwner() == ai) {

					int origValue = boardz[i][j].getValue();
					for (int k = 0; k < bSize; k++) {
						for (int l = 0; l < bSize; l++) {
							// 1. left diagonal up 2. left diag down 3. right
							// diag
							// up 4. rright diag down 5. horitzontal

							if (boardz[k][l].getOwner() == free) {
								// nasa diagonal sya
								if (i - k == j - l) {
									for (int m = j; m > 0; m--) {
										for (int n = k; n > 0; n--) {
											if (boardz[m - 1][n - 1].getOwner() == free) {

												System.out.println("Orig value: " + origValue);

												if (m - 2 <= 0 || n - 2 <= 0) {
													for (int transfer = origValue - 1; transfer > 0; transfer--) {

														System.out.println("Transfer 1");

														GuiCell[][] tempBoard = new GuiCell[bSize][bSize];

														for (int copyi = 0; copyi < boardz.length; copyi++)
															for (int copyj = 0; copyj < boardz[copyi].length; copyj++)
																tempBoard[copyi][copyj] = boardz[copyi][copyj];

														tempBoard[m - 1][n - 1] = new GuiCell(m - 1, n - 1, transfer,
																ai);
														tempBoard[i][j].setValue(origValue - transfer);

														State newState = new State(tempBoard, this, player, level + 1);

														states.add(newState);
													}
												} /*
													 * else if (board[n - 2][m -
													 * 2].getOwner() == player
													 * || board[n - 2][n -
													 * 2].getOwner() == ai) {
													 * for (int transfer =
													 * origValue - 1; transfer >
													 * 0; transfer--) {
													 * System.out.
													 * println("Transfer 1");
													 * 
													 * GuiCell[][] tempBoard =
													 * new
													 * GuiCell[bSize][bSize];
													 * 
													 * for (int copyi = 0; copyi
													 * < board.length; copyi++)
													 * for (int copyj = 0; copyj
													 * < board[copyi].length;
													 * copyj++)
													 * tempBoard[copyi][copyj] =
													 * board[copyi][copyj];
													 * 
													 * tempBoard[m - 1][n - 1] =
													 * new GuiCell(m - 1, n - 1,
													 * transfer, ai);
													 * tempBoard[i][j].setValue(
													 * origValue - transfer);
													 * 
													 * State newState = new
													 * State(tempBoard, this,
													 * player, level + 1);
													 * 
													 * states.add(newState); } }
													 */
											}

										}
									}
								}
							}

							// if (i - k == j - l || k - i == j - l || i - k ==
							// l - j || k - i == l - j || i == k) {
							//
							// }

						}

					}

				}

			}

		}

		System.out.println("Done generating states");

		for (int i = 0; i < states.size(); i++) {
			this.print(states.get(i).getBoard());

		}

		return states;

	}

	public int calculateScore() {
		int score = 0;

		return score;
	}
}