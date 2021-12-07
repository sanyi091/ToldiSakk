package pieces;

import game.Fen;
import util.Team;
import util.Pos;
import util.PieceType;

public class Queen extends Piece{

	public Queen(Team color) {
		this.color = color;
		this.type = PieceType.queen;
	}

	@Override
	public boolean validMove(Pos from, Pos to, Fen fen) {
		return new Rook(color).validMove(from, to, fen) || new Bishop(color).validMove(from, to, fen);
	}

}
