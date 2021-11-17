package game;

import util.GameState;
import util.Color;
import util.Pos;

public class Board {
	private Fen fen;
	private GameState state;
	private Color player;
	
	public Board() {
		fen = new Fen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 1");
		state = GameState.Playing;
		player = fen.getPlayer();
	}
}
