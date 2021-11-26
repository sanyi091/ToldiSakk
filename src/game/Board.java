package game;

import util.GameState;
import util.Team;
import util.Pos;

public class Board {
	private Fen fen;
	private GameState state;
	
	public Board() {
		fen = new Fen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 1");
		state = GameState.Playing;
	}

	public boolean executeMove(Pos from, Pos to){
		if(from == to)
			return false;
		if(from.X() > 7 || from.X() < 0 || to.X() > 7 || to.Y() < 0)
			return false;
		if(fen.getTile(from).getPiece().validMove(from, to, fen)){
			//execute
			return true;
		}
		return true;
	}

	public Team getPlayer(){
		return fen.getPlayer();
	}

	public Fen getFen(){
		return fen;
	}
}
