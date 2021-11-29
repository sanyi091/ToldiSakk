package pieces;

import game.Fen;
import util.Team;
import util.Pos;
import util.PieceType;

public abstract class Piece {
	protected Team color;
	protected PieceType type;
	
	public Piece() {
		this.color = null;
		this.type = null;
	}
	
	public Piece(Team color) {
		this.color = color;
		this.type = null;
	}
	
	public Team getColor() {
		return color;
	}
	
	public PieceType getType() {
		return type;
	}
	
	public abstract boolean validMove(Pos from, Pos to, Fen fen);

}
