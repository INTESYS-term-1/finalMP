import java.util.ArrayList;

public class State {

	GuiCell[][] board = new GuiCell[hexgame.BSIZE][hexgame.BSIZE];
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

	public State(GuiCell[][] board, State parent, int nextTurn, int level) {

		for (int i = 0; i < hexgame.BSIZE; i++) {
			for (int j = 0; j < hexgame.BSIZE; j++) {
				this.board[i][j].setValue(board[i][j].getValue());
				this.board[i][j].setX(i);
				this.board[i][j].setY(j);
				this.board[i][j].setOwner(board[i][j].getOwner());
			}
		}

		this.parentState = parent;
		this.currentTurn = nextTurn;
		this.level = level;
		childrenLeft = level;

	}

	public State(GuiCell[][] guiCells) {
		board = guiCells;
		currentTurn = ai;
	}

	// @Override
	// public boolean equals(Object o) {
	// State s = (State) o;
	//
	// return true;
	// }

	// function to get board
	public GuiCell[][] getBoard() {
		return this.board;
	}

	public int getLevel() {
		return level;
	}

	@Override
	public boolean equals(Object o) {
		State s = (State) o;
		for (int i = 0; i < hexgame.BSIZE; i++) {
			for (int j = 0; j < hexgame.BSIZE; j++) {
				if (this.board[i][j].getValue() == s.getBoard()[i][j].getValue()) {
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
		for (int i = 0; i < hexgame.BSIZE; i++) {
			for (int j = 0; j < hexgame.BSIZE; j++) {
				if (board[i][j].getOwner() == ai) {
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
		for (int j = 0; j < hexgame.BSIZE; j++) {
			System.out.println();
			for (int z = 0; z < hexgame.BSIZE; z++) {
				System.out.print(dummy[j][z].getValue());
				System.out.print(" | ");

			}

		}
	}

	public ArrayList<State> generateStates() {

		ArrayList<State> states = new ArrayList<State>();

		for (int i = 0; i < hexgame.BSIZE; i++) {
			for (int j = 0; j < hexgame.BSIZE; j++) {

				if (board[i][j].getOwner() == ai) {

					for (int k = 0; k < hexgame.BSIZE; k++) {
						for (int l = 0; l < hexgame.BSIZE; l++) {
							// 1. left diagonal up 2. left diag down 3. right
							// diag
							// up 4. rright diag down 5. horitzontal

							if (board[k][l].getOwner() == free) {
								// nasa diagonal sya
								if (i - k == j - l) {
									for (int m = j; m > 0; m--) {
										for (int n = k; n > 0; n--) {
											if (board[m - 1][n - 1].getOwner() == free) {
												if (m - 2 <= 0 || n - 2 <= 0) {
													for (int transfer = board[n][m].getValue()
															- 1; transfer > 0; transfer--) {

														System.out.println("Transfer 1");
														State newState = new State(board, this, player, level + 1);
														newState.getBoard()[m - 1][n - 1] = new GuiCell(m - 1, n - 1,
																transfer, ai);

														states.add(newState);
													}
												} else if (board[n - 2][m - 2].getOwner() == player
														|| board[n - 2][n - 2].getOwner() == ai) {
													for (int transfer = board[n][m].getValue()
															- 1; transfer > 0; transfer--) {
														State newState = new State(board, this, player, level + 1);
														newState.getBoard()[m - 1][n - 1] = new GuiCell(m - 1, n - 1,
																transfer, ai);

														states.add(newState);
													}
												}
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