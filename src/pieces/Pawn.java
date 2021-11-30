package pieces;

import game.Fen;
import game.Tile;
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
		if(new Pos(from.X(), from.Y() + (color == Team.white ? 2 : -2)).equals(fen.getEnpassan()) && to.Y() - from.Y() == (color == Team.white ? 1:-1) && Math.abs(from.X()-to.X()) == 1) {
			fen.setTile(new Tile(new Pos(from.X(), to.Y())));
			return true;
		}

		// take
		else if(Math.abs(from.X()-to.X()) == 1 && to.Y() - from.Y() == (color == Team.white ? 1:-1) && fen.getPiece(to) != null)
			return true;

		// forward 1
		else if(from.X() == to.X() && to.Y() - from.Y() == (color == Team.white ? 1:-1) && fen.getPiece(to) == null)
			return true;

		// forward 2
		else if(from.X() == to.X() && from.Y() == (color == Team.white? 1:6) && fen.getPiece(new Pos(from.X(), from.Y() + (color == Team.white ? 1:-1))) == null && fen.getPiece(to) == null)
			return true;

		return false;
	}

}
