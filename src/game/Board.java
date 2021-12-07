package game;

import pieces.King;
import pieces.Queen;
import pieces.Rook;
import util.PieceType;
import util.Team;
import util.Pos;

/**
 *  Board class, used in executing and checking of the moves.
 *  Also checks the Mate and Stalemate states.
 */
public class Board {
	private Fen fen;

	/**
	 * Default Board Constructor.
	 * Creates a Board with the standard chess setup.
	 */
	public Board() {
		fen = new Fen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 1");
	}

	/**
	 * Custom String Board Constructor.
	 * Creates a Board with the given FEN
	 * @param fen The fen which will be the state of the board, in String format.
	 */
	public Board(String fen){
		this.fen = new Fen(fen);
	}

	/**
	 * Board copy constructor.
	 * @param b The copyable board.
	 */
	public Board(Board b){
		this.fen = new Fen(b.getFen().toString());
	}

	/**
	 * Move validator with the default option of checking for castling and the moving player being the one currently moving.
	 * @param from From position.
	 * @param to To position.
	 * @return True if the move is valid.
	 */
	public boolean validMove(Pos from, Pos to){
		return validMove(from, to, true, fen.getPlayer());
	}

	/**
	 * Customisable move validator.
	 * @param from From position.
	 * @param to To position.
	 * @param checkcheck If true, will check not to move into checks.
	 * @param asTeam Color of the moving pieces.
	 * @return True if move is valid.
	 */
	public boolean validMove(Pos from, Pos to, boolean checkcheck, Team asTeam){
		if(fen.getPiece(from) == null)
			return false;
		if(fen.getPiece(from).getColor() != asTeam)
			return false;
		if(fen.getPiece(to) != null)
			if(fen.getPiece(to).getColor() == asTeam)
				return false;
		if(from == to)
			return false;
		if(from.X() > 7 || from.X() < 0 || to.X() > 7 || to.Y() < 0)
			return false;
		if(castling(from, to) != -1) {
			if(checkcheck) {
				Board checker = new Board(this);
				checker.executeMove(from, to);
				return !checker.inCheck(checker.getFen().getPlayer() == Team.white ? Team.black : Team.white);
			}
			return true;
		}
		if(!fen.getTile(from).getPiece().validMove(from, to, fen)) {
			return false;
		}
		else if(checkcheck){
			Board checker = new Board(this);
			checker.executeMove(from, to);
			return !checker.inCheck(checker.getFen().getPlayer() == Team.white ? Team.black : Team.white);
		}
		return true;
	}

	/**
	 * Returns which castling move a player is performing, and it's validity.
	 * @param from From position.
	 * @param to To Position.
	 * @return 0 - White Kingside, 1 - White Queenside, 2 - Black Kingside, 3 - Black Queenside, -1 - None.
	 *
	 */
	public int castling(Pos from, Pos to){
		if(fen.getPiece(from) == null)
			return -1;

		int notnull = 0;
		for (boolean c: fen.getCastling()) {
			if(c)
				notnull++;
		}
		if(notnull == 0)
			return -1;

		Pos defKing = new Pos(4, fen.getPlayer()==Team.white? 0:7);

		if(fen.getPiece(from).getType() == PieceType.king &&
				fen.getPiece(from).getColor() == fen.getPlayer() &&
				from.equals(defKing) &&
				to.Y() == from.Y()){
			if(to.X() == 2 && fen.getCastlingAt(fen.getPlayer() == Team.white? 1:3)){
				for(int i = 1; i <= 3; i++) {
					if (fen.getPiece(new Pos(from.X() - i, from.Y())) != null)
						return -1;
				}

				return fen.getPlayer() == Team.white? 1:3;
			}

			else if(to.X() == 6 && fen.getCastlingAt(fen.getPlayer() == Team.white? 0:2)){
				for(int i = 1; i <= 2; i++) {
					if (fen.getPiece(new Pos(from.X() + i, from.Y())) != null)
						return -1;
				}

				return fen.getPlayer() == Team.white? 0:2;
			}
		}

		return -1;
	}

	/**
	 * Executes a move, updating the fen in the process.
	 * @param from From position.
	 * @param to To position.
	 */
	public void executeMove(Pos from, Pos to){
		if(fen.getPiece(from) == null)
			return;

		int castlingInx = castling(from, to);
		if(castlingInx != -1){
			fen.setTile(new Tile(from));
			fen.setTile(new Tile(new King(fen.getPlayer()), to));

			fen.setCastling(fen.getPlayer()==Team.white? 0:2, false);
			fen.setCastling(fen.getPlayer()==Team.white? 1:3, false);

			switch (castlingInx) {
				case 0 -> {

					fen.setTile(new Tile(7, 0));
					fen.setTile(new Tile(new Rook(fen.getPlayer()), new Pos(5, 0)));
				}
				case 1 -> {
					fen.setTile(new Tile(0, 0));
					fen.setTile(new Tile(new Rook(fen.getPlayer()), new Pos(3, 0)));
				}
				case 2 -> {
					fen.setTile(new Tile(7, 7));
					fen.setTile(new Tile(new Rook(fen.getPlayer()), new Pos(5, 7)));
				}
				case 3 -> {
					fen.setTile(new Tile(0, 7));
					fen.setTile(new Tile(new Rook(fen.getPlayer()), new Pos(3, 7)));
				}
			}
		}

		else{
			if(from.equals(new Pos(0, fen.getPlayer() == Team.white ? 0 : 7)) &&
					fen.getPiece(from).getType() == PieceType.rook)
				fen.setCastling(fen.getPlayer() == Team.white? 1:3, false);

			else if(from.equals(new Pos(7, fen.getPlayer() == Team.white ? 0 : 7)) &&
					fen.getPiece(from).getType() == PieceType.rook)
				fen.setCastling(fen.getPlayer() == Team.white? 0:2, false);

			else if(from.equals(new Pos(4, fen.getPlayer() == Team.white? 0:7)) &&
					fen.getPiece(from).getType() == PieceType.king){
				fen.setCastling(fen.getPlayer() == Team.white? 0:2, false);
				fen.setCastling(fen.getPlayer() == Team.white? 1:3, false);
			}

			if(Math.abs(from.Y()-to.Y()) == 2 &&
					fen.getPiece(from).getType() == PieceType.pawn)
				fen.setEnpassan(new Pos(to.X(), to.Y() + (fen.getPlayer() == Team.white? -1:1)));

			else
				fen.setEnpassan(new Pos());

			if(fen.getPiece(from).getType() == PieceType.pawn && to.Y() == (fen.getPlayer() == Team.white? 7:0))
				fen.setTile(new Tile(new Queen(fen.getPlayer()), from));

			fen.setTile(new Tile(fen.getPiece(from), to));
			fen.setTile(new Tile(from));
		}

		fen.setPlayer(fen.getPlayer()==Team.white? Team.black:Team.white);
		fen.addTurn();
	}

	/**
	 * Checks if there is a Check on the board for a given player.
	 * @param color The players color which will be checked.
	 * @return True if in check.
	 */
	public boolean inCheck(Team color){
		Pos king = null;
		for(int sor = 0; sor <= 7; sor++){
			for(int oszlop = 0; oszlop <= 7; oszlop++){
				if(fen.getPiece(new Pos(oszlop, sor)) != null)
					if(fen.getPiece(new Pos(oszlop, sor)).equals(new King(color)))
						king = new Pos(oszlop, sor);
			}
		}

		if(king == null)
			throw new IllegalStateException();

		for(int sor = 0; sor <= 7; sor++){
			for(int oszlop = 0; oszlop <= 7; oszlop++){
				Pos checkker = new Pos(oszlop, sor);
				if(fen.getPiece(checkker) != null)
					if(fen.getPiece(checkker).getColor() != color &&
							validMove(checkker, king, false, color == Team.white? Team.black:Team.white))
						return true;
			}
		}

		return false;
	}

	/**
	 * Checks if a stalemate has occurred.
	 * @return True if it has.
	 */
	public boolean isStalemate(){
		for(int frow = 0; frow <= 7; frow++){
			for(int fcolumn = 0; fcolumn <= 7; fcolumn++){
				Pos from = new Pos(fcolumn, frow);
				if(fen.getPiece(from) != null)
					if(fen.getPiece(from).getColor() == fen.getPlayer())
						for(int trow = 0; trow <= 7; trow++)
							for(int tcolumn = 0; tcolumn <= 7; tcolumn++)
								if(validMove(from, new Pos(tcolumn, trow)))
									return false;
			}
		}
		return true;
	}

	/**
	 * Checks if a mate has occurred for the current player.
	 * @return True if it has.
	 */
	public boolean isMate(){
		if(inCheck(fen.getPlayer()))
			return isStalemate();
		else
			return false;
	}

	/**
	 * Returns the board's fen.
	 * @return The fen of the Board.
	 */
	public Fen getFen(){
		return fen;
	}
}
