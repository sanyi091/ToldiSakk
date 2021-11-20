package pieces;

import game.Fen;
import util.Color;
import util.Pos;
import util.Type;

public abstract class Piece {
	protected Color color;
	protected Type type;
	
	public Piece() {
		this.color = null;
		this.type = null;
	}
	
	public Piece(Color color) {
		this.color = color;
		this.type = null;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Type getType() {
		return type;
	}
	
	public abstract boolean validMove(Pos from, Pos to, Fen fen);
	
	public abstract boolean specialMove(Pos from, Pos to, Fen fen);

}
