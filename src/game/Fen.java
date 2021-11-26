package game;
import util.Team;
import util.Pos;
import pieces.*;

import java.util.HashMap;

public class Fen {
	private Tile tiles[][] = new Tile[8][8];
	private Team player;
	private boolean wKingside = false, wQueenside = false, bKingside = false, bQueenside = false;
	private Pos enpassan;
	private int turn;

	public Fen(String fen) {
		if(fen.isBlank() || fen.isEmpty() || fen == null)
			fen = new String("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 1");
		String[] input = fen.split(" ");
		int iter = 0;
		while(iter <= input[0].length() - 1) {
			for(int row = 7; row >=0; row--) {
				for(int column = 0; column <= 7 ; column++, iter++) {
					switch(Character.toLowerCase(input[0].charAt(iter))){
					case 'p':
						tiles[row][column] = new Tile(new Pawn(input[0].charAt(iter)=='P'? Team.white: Team.black),
													  new Pos(column, row));
						break;
						
					case 'k':
						tiles[row][column] = new Tile(new King(input[0].charAt(iter)=='K'? Team.white: Team.black),
													  new Pos(column, row));
						break;
						
					case 'q':
						tiles[row][column] = new Tile(new Queen(input[0].charAt(iter)=='Q'? Team.white: Team.black),
													  new Pos(column, row));
						break;
						
					case 'b':
						tiles[row][column] = new Tile(new Bishop(input[0].charAt(iter)=='B'? Team.white: Team.black),
													  new Pos(column, row));
						break;
						
					case 'n':
						tiles[row][column] = new Tile(new Knight(input[0].charAt(iter)=='N'? Team.white: Team.black),
								  					  new Pos(column, row));
						break;
						
					case 'r':
						tiles[row][column] = new Tile(new Rook(input[0].charAt(iter)=='R'? Team.white: Team.black),
													  new Pos(column, row));
						break;
						
					case '/':
						column = -1;
						break;
						
					default:
						for(int i = 0; i <= Character.getNumericValue(input[0].charAt(iter)) - 1; i++, column++) {
							tiles[row][column] = new Tile(column, row);
						}																					//for loop eggyel többet ad hozzá a végén
						break;
					}
				}
			}
		}
		
		player = input[1].charAt(0)=='w'? Team.white: Team.black;
		
		for(int i = 0; i <= input[2].length() - 1; i++) {
			switch(input[2].charAt(i)) {
			case 'K':
				wKingside = true;
				break;

			case 'Q':
				wQueenside = true;
				break;

			case 'k':
				bKingside = true;
				break;

			default:
				bQueenside = true;
				break;
			}
		}
		
		if(input[3].charAt(0) == '-') {
			enpassan = new Pos(0,0);
		}
		else {
			enpassan = new Pos((int) input[3].charAt(0) - 41, input[3].charAt(1));
		}
		
		turn = Character.getNumericValue(input[4].charAt(0));
	}
	
	public String toString() {

		StringBuilder str = new StringBuilder();
		for(int row = 7; row >= 0; row--) {
			int sorfiller = 0;
			for(int column = 0; column <= 7; column++) {
				if(tiles[row][column].getPiece() == null) {
					sorfiller++;
				}
				else {
					str.append(sorfiller);
					sorfiller = 0;
				}
			}
		}
		
		return str.toString();
	}
	
	public Piece getPiece(Pos pos) {
		return tiles[pos.Y()][pos.X()].getPiece();
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public Tile getTile(Pos pos){
		return tiles[pos.Y()][pos.X()];
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}

	public Team getPlayer() {
		return player;
	}

	public void setPlayer(Team player) {
		this.player = player;
	}

	public Pos getEnpassan() {
		return enpassan;
	}

	public void setEnpassan(Pos enpassan) {
		this.enpassan = enpassan;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public boolean iswKingside() {
		return wKingside;
	}

	public void setwKingside(boolean wKingside) {
		this.wKingside = wKingside;
	}

	public boolean iswQueenside() {
		return wQueenside;
	}

	public void setwQueenside(boolean wQueenside) {
		this.wQueenside = wQueenside;
	}

	public boolean isbKingside() {
		return bKingside;
	}

	public void setbKingside(boolean bKingside) {
		this.bKingside = bKingside;
	}

	public boolean isbQueenside() {
		return bQueenside;
	}

	public void setbQueenside(boolean bQueenside) {
		this.bQueenside = bQueenside;
	}
}
