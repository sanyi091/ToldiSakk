package game;
import pieces.Piece;
import util.Pos;

public class Tile {
	private Piece piece;
	private Pos pos;
	
	public Tile(int x, int y) {
		this.piece = null;
		this.pos = new Pos(x, y);
	}
	
	public Tile(Pos pos) {
		this.piece = null;
		this.pos = pos;
	}
	
	public Tile(Piece piece, Pos pos) {
		this.piece = piece;
		this.pos = pos;
	}
	
	public Piece getPiece() {
		return piece;
	}
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	public Pos getPos() {
		return pos;
	}
	public void setPos(Pos pos) {
		this.pos = pos;
	}
}
