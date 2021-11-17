package pieces;

import game.Board;
import util.Color;
import util.Pos;

public class Bishop extends Piece{

	public Bishop(Color color) {
		super(color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean validMove(Pos from, Pos to, Board board) {

		return false;
	}

}
