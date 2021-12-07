package game;
import util.Team;
import util.Pos;
import pieces.*;

import java.io.Serializable;


public class Fen implements Serializable {
	private Tile[][] tiles = new Tile[8][8];
	private Team player;
	private boolean[] castling = {false, false, false, false};
	private Pos enpassan;
	private int turn;

	public Fen(String fen) {
		if (fen == null)
			fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 1";
		String[] input = fen.split(" ");
		int iter = 0;
		while (iter <= input[0].length() - 1) {
			for (int row = 7; row >= 0; row--) {
				for (int column = 0; column <= 7; column++, iter++) {
					switch (Character.toLowerCase(input[0].charAt(iter))) {
						case 'p' -> tiles[row][column] = new Tile(new Pawn(input[0].charAt(iter) == 'P' ? Team.white : Team.black),
								new Pos(column, row));
						case 'k' -> tiles[row][column] = new Tile(new King(input[0].charAt(iter) == 'K' ? Team.white : Team.black),
								new Pos(column, row));
						case 'q' -> tiles[row][column] = new Tile(new Queen(input[0].charAt(iter) == 'Q' ? Team.white : Team.black),
								new Pos(column, row));
						case 'b' -> tiles[row][column] = new Tile(new Bishop(input[0].charAt(iter) == 'B' ? Team.white : Team.black),
								new Pos(column, row));
						case 'n' -> tiles[row][column] = new Tile(new Knight(input[0].charAt(iter) == 'N' ? Team.white : Team.black),
								new Pos(column, row));
						case 'r' -> tiles[row][column] = new Tile(new Rook(input[0].charAt(iter) == 'R' ? Team.white : Team.black),
								new Pos(column, row));
						case '/' -> column = -1;
						default -> {
							for (int i = 0; i <= Character.getNumericValue(input[0].charAt(iter)) - 1; i++, column++) {
								tiles[row][column] = new Tile(column, row);
							}
							column--; //for loop adds 1 more than needed
						}
					}
				}
			}
		}

		player = input[1].charAt(0) == 'w' ? Team.white : Team.black;

		for (int i = 0; i <= input[2].length() - 1; i++) {
			switch (input[2].charAt(i)) {
				case 'K' -> castling[0] = true;
				case 'Q' -> castling[1] = true;
				case 'k' -> castling[2] = true;
				case 'q' -> castling[3] = true;
			}
		}

		if (input[3].charAt(0) == '-') {
			enpassan = new Pos();
		} else {
			enpassan = new Pos(((int)Character.toLowerCase(input[3].charAt(0)) - (int)'a'),
					Character.getNumericValue(input[3].charAt(1)) - 1);
		}

		turn = Character.getNumericValue(input[4].charAt(0));
	}

	public String toString() {

		StringBuilder str = new StringBuilder();
		for (int row = 7; row >= 0; row--) {
			int rowfiller = 0;
			for (int column = 0; column <= 7; column++) {
				if (tiles[row][column].getPiece() == null) {
					rowfiller++;
				} else {
					if(rowfiller != 0) {
						str.append(rowfiller);
						rowfiller = 0;
					}
					switch (tiles[row][column].getPiece().getType()) {
						case pawn -> str.append(tiles[row][column].getPiece().getColor() == Team.white ? 'P' : 'p');
						case rook -> str.append(tiles[row][column].getPiece().getColor() == Team.white ? 'R' : 'r');
						case knight -> str.append(tiles[row][column].getPiece().getColor() == Team.white ? 'N' : 'n');
						case bishop -> str.append(tiles[row][column].getPiece().getColor() == Team.white ? 'B' : 'b');
						case queen -> str.append(tiles[row][column].getPiece().getColor() == Team.white ? 'Q' : 'q');
						case king -> str.append(tiles[row][column].getPiece().getColor() == Team.white ? 'K' : 'k');
					}
				}
				if(column == 7 && row != 0) {
					if(rowfiller != 0) {
						str.append(rowfiller);
						rowfiller = 0;
					}
					str.append('/');
				}
			}
		}
		if(str.charAt(str.length() - 1) == '/')
			str.deleteCharAt(str.length() - 1);

		str.append(" ");
		str.append(player == Team.white? 'w':'b');

		str.append(" ");
		if(castling[0])
			str.append('K');
		if(castling[1])
			str.append('Q');
		if(castling[2])
			str.append('k');
		if(castling[3])
			str.append('q');
		if(!castling[0] && !castling[1] && !castling[2] && !castling[3])
			str.append('-');

		str.append(" ");
		if(enpassan.X() != -1) {
			str.append((char) (enpassan.X() + (int)'a'));
			str.append((enpassan.Y() + 1));
		}
		else
			str.append("-");

		str.append(" ");
		str.append(turn);

		return str.toString();
	}

	public Piece getPiece(Pos pos) {
		return tiles[pos.Y()][pos.X()].getPiece();
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public Tile getTile(Pos pos) {
		return tiles[pos.Y()][pos.X()];
	}

	public void setTile(Tile tile){
		tiles[tile.getPos().Y()][tile.getPos().X()] = tile;
	}

	public Team getPlayer() {
		return player;
	}

	public void addTurn(){
		turn++;
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

	public void setCastling(int inx, boolean value) {
		castling[inx] = value;
	}

	public boolean getCastlingAt(int inx){
		return castling[inx];
	}

	public boolean[] getCastling() {
		return castling;
	}
}
