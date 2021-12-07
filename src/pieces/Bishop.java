package pieces;

import game.Fen;
import util.Team;
import util.Pos;
import util.PieceType;


/**
 * Classic chess Bishop.
 */
public class Bishop extends Piece{

	public Bishop(Team color) {
		this.color = color;
		this.type = PieceType.bishop;
	}

	@Override
	public boolean validMove(Pos from, Pos to, Fen fen) {
		if(Math.abs(from.X() - to.X()) == Math.abs(from.Y() - to.Y())) {
			Integer[] dir = new Integer[2];
			dir[0] = (to.X() - from.X()) / Math.abs(from.X() - to.X()); //calculate x direction
			dir[1] = (to.Y() - from.Y()) / Math.abs(from.Y() - to.Y()); //calculate y direction
			for(int i = 1; i <= Math.abs(from.X() - to.X()) - 1; i++) {
				if(fen.getPiece(new Pos(from.X() + i*dir[0], from.Y() + i*dir[1])) != null)
					return false;
			}
			return true;
		}
		
		return false;
	}

}
