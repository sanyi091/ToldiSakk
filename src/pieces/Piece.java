package pieces;
import util.Color;
import util.Pos;

public abstract class Piece {
	private boolean alive = true;
	private Color color;
	
	public Piece(Color color) {
		this.color = color;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public abstract boolean validMove(Pos from, Pos to);

}
