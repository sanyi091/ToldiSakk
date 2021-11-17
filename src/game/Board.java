package game;

public class Board {
	private Tile[][] tiles;
	private Fen fen;
	
	public Board() {
		for(int y = 0; y <= 7; y++) {
			for(int x = 0; y <= 7 ; x++) {
				tiles[x][y] = new Tile(x, y);
			}
		}
		fen = new Fen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 1");
	}
	
	public void setBoard(Fen fen) {
		tiles = fen.getTiles();
	}
}
