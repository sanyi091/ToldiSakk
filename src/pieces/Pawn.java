package pieces;

import game.Fen;
import util.Team;
import util.Pos;
import util.PieceType;

public class Pawn extends Piece {

	public Pawn(Team color) {
		this.color = color;
		this.type = PieceType.pawn;
	}

	@Override
	public boolean validMove(Pos from, Pos to, Fen fen) {
		// en passan
		if(to == fen.getEnpassan() && to.Y() - from.Y() == (color == Team.white ? 1:-1)) {
			fen.setEnpassan(new Pos());
			return true;
		}

		// forward 1
		else if(from.X() == to.X() && to.X() - from.Y() == (color == Team.white ? 1:-1))
			return true;

		// forward 2
		else if(from.X() == to.X() && from.Y() == (color == Team.white? 1:6) && fen.getPiece(new Pos(from.X(), from.Y() + (color == Team.white ? 1:-1))) == null)
			return true;

		return false;
	}

	@Override
	public boolean specialMove(Pos from, Pos to, Fen fen) {
		// TODO Auto-generated method stub
		return false;
	}

}
