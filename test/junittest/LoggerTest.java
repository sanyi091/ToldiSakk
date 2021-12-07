package junittest;

import game.Fen;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.Logger;

import static org.junit.Assert.*;

public class LoggerTest {

    Logger l;

    @Before
    public void setUp(){
        l = new Logger();
    }

    @Test
    public void putgetTest() {
        l.put(new Fen(""));
        Assert.assertSame(new Fen(""), l.getLast());
    }

    @Test
    public void dropTest() {
        l.put(new Fen(""));
        l.put(new Fen("rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 2"));
        l.drop();
        Assert.assertSame(new Fen(""), l.getLast());
    }

    @Test
    public void getLastTest() {
        l.put(new Fen(""));
        l.put(new Fen("rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPP2PPP/RNBQKBNR b Kkq c6 2"));
        l.put(new Fen("rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 2"));
        Assert.assertSame(new Fen("rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 2"), l.getLast());
    }
}