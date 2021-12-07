package swing;

import game.Board;
import game.Fen;
import game.Tile;
import pieces.Piece;
import util.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import java.util.HashMap;

public class GameGUI extends JPanel{
    private State state = State.MENU;
    private SwTile[][] tiles = new SwTile[8][8];
    private Pos from, to;
    private Board board;
    private JTextField fen;
    private JLabel text;
    private static final String columns = "ABCDEFGH";
    private final HashMap<PieceKey, ImageIcon> icons = new HashMap<>();
    private Logger log;

    public GameGUI(){

        this.setLayout(new BorderLayout(4, 3));
        this.setSize(800,800);

        JToolBar toolbar = new JToolBar();
        this.add(toolbar, BorderLayout.PAGE_START);

        Action newGameAction = new AbstractAction("New Game") {
            @Override
            public void actionPerformed(ActionEvent e) {
                board = new Board();
                log = new Logger();
                log.put(new Fen(board.getFen().toString()));
                setupBoard(board);
            }
        };

        Action newGameFen = new AbstractAction("Start Game with FEN") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fen = JOptionPane.showInputDialog("Give staring VALID FEN (BETA)");
                board = new Board(fen);
                log = new Logger();
                log.put(new Fen(board.getFen().toString()));
                setupBoard(board);
            }
        };

        Action revert = new AbstractAction("Revert last move") {
            @Override
            public void actionPerformed(ActionEvent e) {
                log.drop();
                if(log.getLast() != null) {
                    board = new Board(log.getLast().toString());
                    setupBoard(board);
                }
            }
        };

        Action save = new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(board != null)
                    log.save(JOptionPane.showInputDialog("Save as (if not, generated name):"));
            }
        };

        Action backToMenu = new AbstractAction("Back to Menu") {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = State.MENU;
            }
        };

        toolbar.add(newGameAction);
        toolbar.add(newGameFen);
        toolbar.add(revert);
        toolbar.add(save);
        toolbar.add(backToMenu);
        toolbar.add(new JSeparator());

        Color bg = new Color(185, 220, 199);

        JPanel lent = new JPanel();
        lent.setLayout(new BorderLayout());
        lent.setBackground(bg);

        lent.add(new JLabel("FEN -> "), BorderLayout.LINE_START);
        fen = new JTextField("");
        fen.setEditable(false);
        fen.setBackground(null);
        fen.setBorder(null);
        text = new JLabel("");
        lent.add(fen, BorderLayout.CENTER);
        lent.add(text, BorderLayout.LINE_END);


        this.add(lent, BorderLayout.PAGE_END);


        JPanel board = new JPanel(new GridLayout(0, 9));

        board.setBorder(new CompoundBorder(
                new EmptyBorder(8,8,8,8),
                new LineBorder(Color.BLACK)
        ));


        board.setBackground(bg);
        JPanel boardConstraint = new JPanel(new GridLayout());
        boardConstraint.setBackground(bg);
        boardConstraint.add(board);
        this.add(boardConstraint);



        for(int row = 7; row >= 0; row--){
            for(int column = 0; column <= 7; column++){
                SwTile t = new SwTile();
                if((column % 2 == 1 && row % 2 == 0) || (column % 2 == 0 && row % 2 == 1))
                    t.setBackground(Color.WHITE);
                else
                    t.setBackground(new Color(53,155,95));

                t.addMouseListener(new ChessListener());
                t.setPos(new Pos(column, row));

                tiles[row][column] = t;
            }
        }

        // row nums
        for (int row = 7; row >= 0; row--) {
            for (int column = 0; column <= 7; column++) {
                if (column == 0) {
                    board.add(new JLabel("" + (row + 1), SwingConstants.CENTER));
                }
                board.add(tiles[row][column]);
            }
        }

        // corner
        board.add(new JLabel(""));

        // columns
        for (int i = 0; i <= 7; i++) {
            board.add(new JLabel(columns.substring(i, i + 1), SwingConstants.CENTER));
        }

        initImages();

        this.setVisible(true);
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState(){
        return state;
    }

    private void setupBoard(Board board){
        Tile[][] fenTiles = board.getFen().getTiles();

        for(int row = 7; row >= 0; row--){
            for(int column = 0; column <= 7; column++){
                Piece piece = fenTiles[row][column].getPiece();
                if(piece != null) {
                    ImageIcon ic = icons.get(new PieceKey(piece.getType(), piece.getColor()));
                    tiles[row][column].setIcon(new ImageIcon(imageScale(this.getHeight()/10, ic.getImage())));
                }
                else
                    tiles[row][column].setIcon(new ImageIcon());
            }

        }
        fen.setText(board.getFen().toString());

    }

    private Image imageScale(int s, Image scrImage){
        BufferedImage resImage = new BufferedImage(s, s, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resImage.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(scrImage, 0, 0, s, s, null);
        g2.dispose();

        return resImage;
    }

    private void initImages(){

        icons.put(new PieceKey(PieceType.pawn, Team.white), new ImageIcon("pieces/wpawn.png"));
        icons.put(new PieceKey(PieceType.rook, Team.white), new ImageIcon("pieces/wrook.png"));
        icons.put(new PieceKey(PieceType.knight, Team.white), new ImageIcon("pieces/wknight.png"));
        icons.put(new PieceKey(PieceType.bishop, Team.white), new ImageIcon("pieces/wbishop.png"));
        icons.put(new PieceKey(PieceType.king, Team.white), new ImageIcon("pieces/wking.png"));
        icons.put(new PieceKey(PieceType.queen, Team.white), new ImageIcon("pieces/wqueen.png"));

        icons.put(new PieceKey(PieceType.pawn, Team.black), new ImageIcon("pieces/bpawn.png"));
        icons.put(new PieceKey(PieceType.rook, Team.black), new ImageIcon("pieces/brook.png"));
        icons.put(new PieceKey(PieceType.knight, Team.black), new ImageIcon("pieces/bknight.png"));
        icons.put(new PieceKey(PieceType.bishop, Team.black), new ImageIcon("pieces/bbishop.png"));
        icons.put(new PieceKey(PieceType.king, Team.black), new ImageIcon("pieces/bking.png"));
        icons.put(new PieceKey(PieceType.queen, Team.black), new ImageIcon("pieces/bqueen.png"));

    }

    private class ChessListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            SwTile theTile = (SwTile) e.getComponent();

            if(board != null) {
                if (from == null) {
                    from = theTile.getPos();
                    if (board.getFen().getPiece(from) == null)
                        from = null;
                    else if(board.getFen().getPlayer() != board.getFen().getPiece(from).getColor())
                        from = null;
                    else {
                        theTile.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLoweredBevelBorder(),
                                BorderFactory.createLineBorder(Color.black)
                        ));
                    }
                } else {
                    to = theTile.getPos();
                    if(board.getFen().getPiece(to) != null){
                        if(board.getFen().getPiece(to).getColor() == board.getFen().getPlayer()) {
                            tiles[from.Y()][from.X()].setBorder(BorderFactory.createEmptyBorder());
                            from = to;
                            to = null;
                            theTile.setBorder(BorderFactory.createCompoundBorder(
                                    BorderFactory.createLoweredBevelBorder(),
                                    BorderFactory.createLineBorder(Color.black)
                            ));
                        }
                    }
                    if(to != null) {
                        if (board.validMove(from, to)) {
                            board.executeMove(from, to);
                            setupBoard(board);
                            log.put(new Fen(board.getFen().toString()));
                            checkmate();
                        }
                        tiles[from.Y()][from.X()].setBorder(BorderFactory.createEmptyBorder());
                        from = null;
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if(board != null)
                setupBoard(board);
        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private void checkmate(){
        Team color = board.getFen().getPlayer();
        if(board.isMate()) {
            text.setText("Mate");
            log.configEnd(0);
        }
        else if(board.isStalemate()) {
            text.setText("Stalemate");
            log.configEnd(1);
        }
        else if(board.inCheck(color)) {
            text.setText("Check");
        }
        else text.setText("");
    }
}
