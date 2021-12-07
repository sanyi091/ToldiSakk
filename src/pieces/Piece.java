package pieces;

import game.Fen;
import util.Team;
import util.Pos;
import util.PieceType;

import java.io.Serializable;

/**
 * Abstract piece class. Describes a chess piece.
 * Serializable because of io saving.
 */
public abstract class Piece implements Serializable {
	protected Team color;
	protected PieceType type;
	
	public Piece() {
		this.color = null;
		this.type = null;
	}

	/**
	 * Equals override to compare pieces.
	 * @param o Other piece.
	 * @return True if they are equal.
	 */
	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(!(o instanceof Piece))
			return false;
		Piece piece = (Piece) o;
		return this.type == piece.type && this.color == piece.color;
	}

	/**
	 * Return the color of the piece.
	 * @return Piece's color.
	 */
	public Team getColor() {
		return color;
	}

	/**
	 * Return the type of the piece.
	 * @return Piece's type.
	 */
	public PieceType getType() {
		return type;
	}

	/**
	 * Abstract move validator.
	 * Each piece has a different move-set so its implemented differently.
	 * @param from From position.
	 * @param to To positon.
	 * @param fen The tiles.
	 * @return True if the move is in the pieces move-set.
	 */
	public abstract boolean validMove(Pos from, Pos to, Fen fen);

}
