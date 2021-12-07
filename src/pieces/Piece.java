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

	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(!(o instanceof Piece))
			return false;
		Piece piece = (Piece) o;
		return this.type == piece.type && this.color == piece.color;
	}
	
	public Team getColor() {
		return color;
	}
	
	public PieceType getType() {
		return type;
	}
	
	public abstract boolean validMove(Pos from, Pos to, Fen fen);

}
