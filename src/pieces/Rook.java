package pieces;

import game.Fen;
import util.Team;
import util.Pos;
import util.PieceType;

/**
 * Classic chess Rook.
 */
public class Rook extends Piece{

	public Rook(Team color) {
		this.color = color;
		this.type = PieceType.rook;
	}

	@Override
	public boolean validMove(Pos from, Pos to, Fen fen) {
		if(from.X() == to.X()){
			int tav = to.Y() - from.Y();
			for(int i = 1; i <= Math.abs(tav) - 1; i++) {
				if(fen.getPiece(new Pos(from.X(), from.Y() + i*(Math.abs(tav) / tav))) != null)
					return false;
			}
			return true;
		}
		
		else if(from.Y() == to.Y()){
			int tav = to.X() - from.X();
			for(int i = 1; i <= Math.abs(tav) - 1; i++) {
				if(fen.getPiece(new Pos(from.X() + i*(Math.abs(tav) / tav), from.Y())) != null)
					return false;
			}
			return true;
		}
		
		return false;
	}
	
}
