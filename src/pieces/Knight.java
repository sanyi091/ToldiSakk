package pieces;

import game.Fen;
import util.Team;
import util.Pos;
import util.PieceType;

import java.util.ArrayList;

public class Knight extends Piece{

	public Knight(Team color) {
		this.color = color;
		this.type = PieceType.knight;
	}

	@Override
	public boolean validMove(Pos from, Pos to, Fen fen) {
		ArrayList<Pos> moves = new ArrayList<>();

		int x = to.X();
		int y = to.Y();

		moves.add(new Pos(x - 1, y + 2));
		moves.add(new Pos(x + 1, y + 2));

		moves.add(new Pos(x - 2, y + 1));
		moves.add(new Pos(x + 2, y + 1));

		moves.add(new Pos(x - 2, y - 1));
		moves.add(new Pos(x + 2, y - 1));

		moves.add(new Pos(x - 1, y - 2));
		moves.add(new Pos(x + 1, y - 2));

		return moves.contains(to);
	}

	@Override
	public boolean specialMove(Pos from, Pos to, Fen fen) {
		// TODO Auto-generated method stub
		return false;
	}

}
