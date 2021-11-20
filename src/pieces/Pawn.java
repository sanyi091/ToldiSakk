package pieces;

import game.Fen;
import util.Color;
import util.Pos;
import util.Type;

public class Pawn extends Piece{

	public Pawn(Color color) {
		this.color = color;
		this.type = Type.pawn;
	}

	@Override
	public boolean validMove(Pos from, Pos to, Fen fen) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean specialMove(Pos from, Pos to, Fen fen) {
		// TODO Auto-generated method stub
		return false;
	}

}
