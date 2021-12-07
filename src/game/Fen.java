package game;
import util.Team;
import util.Pos;
import pieces.*;

import java.io.Serializable;

/**
 * Fen class used to store the state of the board at any given moment.
 * Serializable because of io storage.
 */
public class Fen implements Serializable {
	private Tile[][] tiles = new Tile[8][8];
	private Team player;
	private boolean[] castling = {false, false, false, false};
	private Pos enpassan;
	private int turn;

	/**
	 * Creates a custom fen from a string.
	 * @param fen The string of the fen.
	 */
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

	/**
	 * Transforms the object into a readable fen.
	 * @return The object in String format.
	 */
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

	/**
	 * Return a given piece or null from a given position.
	 * @param pos The position.
	 * @return The piece at that position.
	 */
	public Piece getPiece(Pos pos) {
		return tiles[pos.Y()][pos.X()].getPiece();
	}

	/**
	 * Returns a 8x8 array of tiles.
	 * @return The array of tiles.
	 */
	public Tile[][] getTiles() {
		return tiles;
	}

	/**
	 * Returns a tile at a given position.
	 * @param pos The given position.
	 * @return The tile at the position.
	 */
	public Tile getTile(Pos pos) {
		return tiles[pos.Y()][pos.X()];
	}

	/**
	 * Sets a tile into its position in the array.
	 * @param tile The tile.
	 */
	public void setTile(Tile tile){
		tiles[tile.getPos().Y()][tile.getPos().X()] = tile;
	}

	/**
	 * Return the player currently on the move.
	 * @return The current player.
	 */
	public Team getPlayer() {
		return player;
	}

	/**
	 * Increments the turns by 1.
	 */
	public void addTurn(){
		turn++;
	}

	/**
	 * Sets the current player to a new one.
	 * @param player New players color.
	 */
	public void setPlayer(Team player) {
		this.player = player;
	}

	/**
	 * Return the EnPassan position on the board.
	 * @return EnPassan position.
	 */
	public Pos getEnpassan() {
		return enpassan;
	}

	/**
	 * Sets a new EnPassan position.
	 * @param enpassan The new position.
	 */
	public void setEnpassan(Pos enpassan) {
		this.enpassan = enpassan;
	}

	/**
	 * Sets the castling currently available.
	 * @param inx 0-WKingside, 1-WQueenside, 2-BKingside, 3-BQueenside.
	 * @param value New value of the given index.
	 */
	public void setCastling(int inx, boolean value) {
		castling[inx] = value;
	}

	/**
	 * Returns the castling availability at the current index.
	 * @param inx 0-WKingside, 1-WQueenside, 2-BKingside, 3-BQueenside.
	 * @return True if its available.
	 */
	public boolean getCastlingAt(int inx){
		return castling[inx];
	}

	/**
	 * Returns the array of castling booleans.
	 * @return Castling booleans.
	 */
	public boolean[] getCastling() {
		return castling;
	}
}
