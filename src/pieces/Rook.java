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
				if(fen.getPiece(new Pos(from.X(), from.Y()+i*(Math.abs(tav)/tav))) != null)
					return false;
			}
			return true;
		}
		
		else if(from.Y() == to.Y()){
			int tav = from.X()-to.X();
			for(int i = 1; i <= tav - 1; i++) {
				if(fen.getPiece(new Pos(from.Y(), from.X()+i*(Math.abs(tav)/tav))) != null)
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
