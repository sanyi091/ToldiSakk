package game;

import pieces.King;
import pieces.Piece;
import pieces.Rook;
import util.GameState;
import util.PieceType;
import util.Team;
import util.Pos;

import java.util.Objects;

public class Board {
	private Fen fen;
	private GameState state;
	
	public Board() {
		fen = new Fen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 1");
		state = GameState.Playing;
	}

	public Board(String fen){
		this.fen = new Fen(fen);
		state = GameState.Playing;
	}

	public boolean validMove(Pos from, Pos to){
		if(fen.getPiece(from) == null)
			return false;
		if(fen.getPiece(from).getColor() != fen.getPlayer())
			return false;
		if(from == to)
			return false;
		if(from.X() > 7 || from.X() < 0 || to.X() > 7 || to.Y() < 0)
			return false;
		if(!fen.getTile(from).getPiece().validMove(from, to, fen)){
			return false;
		}
		return true;
	}

	public int castling(Pos from, Pos to){
		int notnull = 0;
		for (boolean c: fen.getCastling()) {
			if(c)
				notnull++;
		}
		if(notnull == 0)
			return 0;

		Pos defKing = new Pos(4, getPlayer()==Team.white? 0:7);

		if(fen.getPiece(from).getType() == PieceType.king &&
				fen.getPiece(from).getColor() == fen.getPlayer() &&
				from.equals(defKing) &&
				to.Y() == from.Y()){
			if(to.X() == 0 && fen.getCastlingAt(fen.getPlayer() == Team.white? 1:3)){
				for(int i = 1; i <= 3; i++) {
					if (fen.getPiece(new Pos(from.Y(), from.X() - i)) != null)
						return 0;
				}

				return fen.getPlayer() == Team.white? 1:3;
			}

			else if(to.X() == 7 && fen.getCastlingAt(fen.getPlayer() == Team.white? 0:2)){
				for(int i = 1; i <= 2; i++) {
					if (fen.getPiece(new Pos(from.Y(), from.X() + i)) != null)
						return 0;
				}

				return fen.getPlayer() == Team.white? 0:2;
			}
		}

		return -1;
	}

	public void executeMove(Pos from, Pos to, int castlingInx){
		if(castlingInx != -1){
			fen.setTile(new Tile(from));
			fen.setTile(new Tile(new King(fen.getPlayer()),from));

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
			if(from.equals(new Pos(0, fen.getPlayer() == Team.white ? 0 : 7)) && fen.getPiece(from).getType() == PieceType.rook)
				fen.setCastling(fen.getPlayer() == Team.white? 1:3, false);

			else if(from.equals(new Pos(7, fen.getPlayer() == Team.white ? 0 : 7)) && fen.getPiece(from).getType() == PieceType.rook)
				fen.setCastling(fen.getPlayer() == Team.white? 0:2, false);

			if(Math.abs(from.Y()-to.Y()) == 2 && fen.getPiece(from).getType() == PieceType.pawn)
				fen.setEnpassan(new Pos(from.X() + (fen.getPlayer() == Team.white? 1:-1), from.Y()));

			else
				fen.setEnpassan(new Pos());

			fen.setTile(new Tile(fen.getPiece(from), to));
			fen.setTile(new Tile(from));
		}
	}

	public Team getPlayer(){
		return fen.getPlayer();
	}

	public Fen getFen(){
		return fen;
	}
}
