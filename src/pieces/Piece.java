package pieces;
import game.Board;
import util.Color;
import util.Pos;

public abstract class Piece {
	private Color color;
	
	public Piece(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public abstract boolean validMove(Pos from, Pos to, Board board);

}
