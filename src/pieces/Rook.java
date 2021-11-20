package pieces;

import game.Fen;
import util.Color;
import util.Pos;
import util.Type;

public class Rook extends Piece{

	public Rook(Color color) {
		this.color = color;
		this.type = Type.rook;
	}

	@Override
	public boolean validMove(Pos from, Pos to, Fen fen) {
		if(from.X() == to.X()){
			int tav = from.Y()-to.Y();
			for(int i = 1; i <= tav - 1; i++) {
				if(fen.getPiece(new Pos(from.X(), from.Y()+i)) != null)
					return false;
			}
		}
		else if(from.Y() == to.Y()){
		}
		
		return false;
	}

	@Override
	public boolean specialMove(Pos from, Pos to, Fen fen) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
