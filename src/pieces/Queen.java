package pieces;

import game.Fen;
import util.Color;
import util.Pos;
import util.Type;

public class Queen extends Piece{

	public Queen(Color color) {
		this.color = color;
		this.type = Type.queen;
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
