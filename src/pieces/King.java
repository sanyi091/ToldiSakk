package pieces;

import game.Fen;
import util.Team;
import util.Pos;
import util.PieceType;

public class King extends Piece{

	public King(Team color) {
		this.color = color;
		this.type = PieceType.king;
	}

	@Override
	public boolean validMove(Pos from, Pos to, Fen fen) {
		return Math.abs(from.X()-to.X()) <= 1 && Math.abs(from.Y()-to.Y()) <= 1;
	}

	@Override
	public boolean specialMove(Pos from, Pos to, Fen fen) {
		// TODO Auto-generated method stub
		return false;
	}

}
