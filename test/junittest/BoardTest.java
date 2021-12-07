package junittest;

import game.Board;
import game.Fen;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.Pos;

public class BoardTest {
    Board b;
    @Before
    public void setUp(){
        b = new Board("r3kbnr/pppqpppp/2npb3/8/4P3/3B1N2/PPPP1PPP/RNBQK2R w KQkq - 9");
    }

    @Test
    public void validMoveTest() {
        Assert.assertTrue(b.validMove(new Pos(4, 0), new Pos(6, 0)));
        Assert.assertTrue(b.validMove(new Pos(0, 1), new Pos(0, 3)));
        Assert.assertFalse(b.validMove(new Pos(6, 1), new Pos(5, 2)));
    }

    @Test
    public void castlingTest() {
        Assert.assertEquals(0,  b.castling(new Pos(4, 0), new Pos(6, 0)));
        Assert.assertEquals(-1,  b.castling(new Pos(4, 0), new Pos(5, 0)));
    }

    @Test
    public void isStalemate() {
    }

    @Test
    public void isMate() {
    }

    @Test
    public void getFen() {
        Assert.assertSame(new Fen("r3kbnr/pppqpppp/2npb3/8/4P3/3B1N2/PPPP1PPP/RNBQK2R w KQkq - 9"), b.getFen());
    }
}