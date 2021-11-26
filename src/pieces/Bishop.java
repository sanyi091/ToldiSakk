package pieces;

import game.Fen;
import util.Team;
import util.Pos;
import util.PieceType;

public class Bishop extends Piece{

	public Bishop(Team color) {
		this.color = color;
		this.type = PieceType.bishop;
	}

	@Override
	public boolean validMove(Pos from, Pos to, Fen fen) {
		if(Math.abs(from.X() - to.X()) == Math.abs(from.Y() - to.Y())) {
			Integer[] dir = new Integer[2];
			dir[0] = (from.X() - to.X()) / Math.abs(from.X() - to.X()); //calculate x direction
			dir[1] = (from.Y() - to.Y()) / Math.abs(from.Y() - to.Y()); //calculate y direction
			for(int i = 0; i <= Math.abs(from.X() - to.X()); i++) {
				if(fen.getPiece(new Pos(from.X() + i*dir[0], from.Y() + i*dir[1])) != null)
					return false;
			}
			return true;
		}
		
		return false;
	}

	@Override
	public boolean specialMove(Pos from, Pos to, Fen fen) {
		// TODO Auto-generated method stub
		return false;
	}

}
