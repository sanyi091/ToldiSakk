package game;
import pieces.Piece;
import util.Pos;

import java.io.Serializable;

/**
 * Describes a single tile, its position and what piece is on it.
 * Serializable because of io storage.
 */
public class Tile implements Serializable {
	private Piece piece;
	private Pos pos;

	/**
	 * Constructs a tile witch the given x, y values.
	 * @param x x value.
	 * @param y y value.
	 */
	public Tile(int x, int y) {
		this.piece = null;
		this.pos = new Pos(x, y);
	}

	/**
	 * Constructs a tile with a given position.
	 * @param pos The given position.
	 */
	public Tile(Pos pos) {
		this.piece = null;
		this.pos = pos;
	}

	/**
	 * Constructs a tile with a given position and a piece.
	 * @param piece The piece.
	 * @param pos The position.
	 */
	public Tile(Piece piece, Pos pos) {
		this.piece = piece;
		this.pos = pos;
	}

	/**
	 * Returns the piece on the tile.
	 * @return The piece on the tile.
	 */
	public Piece getPiece() {
		return piece;
	}

	/**
	 * Returns the position of the tile.
	 * @return Tile's position.
	 */
	public Pos getPos() {
		return pos;
	}

	/**
	 * Sets the tiles position.
	 * @param pos Wanted position.
	 */
	public void setPos(Pos pos) {
		this.pos = pos;
	}

}
