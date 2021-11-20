package pieces;

import game.Fen;
import util.Color;
import util.Pos;
import util.Type;

public class Knight extends Piece{

	public Knight(Color color) {
		this.color = color;
		this.type = Type.knight;
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
