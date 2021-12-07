package junittest;

import game.Fen;
import util.Pos;
import util.Team;
import pieces.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class FenTest {
	Fen fen;
	
	@Before
	public void setUp() {
		fen = new Fen("");
	}
	
	
	@Test
	public void testFenString() {
		Fen fen1 = new Fen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 1");
		Assert.assertEquals(fen.toString(), fen1.toString());
	}
	
	@Test
	public void pawnTest() {
		Assert.assertEquals(fen.getPiece(new Pos(1, 1)), new Pawn(Team.white));
	}
	
	@Test
	public void tilesTest() {
		Fen fen1 = new Fen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 1");
		Assert.assertSame(fen.getTiles(), fen1.getTiles());
	}

	@Test
	public void playerTest(){
		Fen fen1 = new Fen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 1");
		Assert.assertNotEquals(fen.getPlayer(), fen1.getPlayer());
		Assert.assertEquals(Team.black, fen1.getPlayer());
	}

	@Test
	public void allQueenside(){
		fen.setCastling(0, false);
		fen.setCastling(2, false);
		boolean[] same = new boolean[4];
		same = new boolean[]{false, true, false, true};
		Assert.assertSame(same, fen.getCastling());
	}
}
