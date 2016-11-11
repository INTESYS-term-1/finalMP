
public class GuiCell {

	private int x;
	private int y;
	private int value;
	private int owner; // 1 for AI, 0 for wala, -1 for player
	private Boolean legitCell; // pwede kasi na pader sya or <---> (non
								// existent)

	public GuiCell(int x, int y, int value, int owner, Boolean legitCell) {
		super();
		this.x = x;
		this.y = y;
		this.value = value;
		this.owner = owner;
		this.legitCell = legitCell;
	}

	public GuiCell(int x, int y, int value, int owner) {
		super();
		this.x = x;
		this.y = y;
		this.value = value;
		this.owner = owner;
		this.legitCell = true;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public Boolean getLegitCell() {
		return legitCell;
	}

	public void setLegitCell(Boolean legitCell) {
		this.legitCell = legitCell;
	}

	public void print() {
		System.out.println("x: " + x);
		System.out.println("y: " + y);
		System.out.println("Value: " + value);
		System.out.println("Owner: " + owner);
		System.out.println("Legit?: " + legitCell);
	}

}
