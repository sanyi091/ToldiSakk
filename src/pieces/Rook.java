package pieces;

import game.Board;
import util.Color;
import util.Pos;

public class Rook extends Piece{

	public Rook(Color color) {
		super(color);
	}

	@Override
	public boolean validMove(Pos from, Pos to, Board board) {
		if((from.X() == to.X()) || (from.Y() == to.Y()))
			return true;
		return false;
	}
	
}
