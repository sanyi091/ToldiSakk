package swing;

import game.Board;
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

public class GameGUI extends JFrame{
    private SwTile[][] tiles = new SwTile[8][8];
    private GameState state = GameState.NotStarted;
    private Pos from, to;
    private Board board;
    private JLabel fen;
    private static final String COLS = "ABCDEFGH";
    private final HashMap<PieceKey, ImageIcon> icons = new HashMap<>();

    public GameGUI(){

        this.setLayout(new BorderLayout(4, 3));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800,800);

        JToolBar toolbar = new JToolBar();
        this.add(toolbar, BorderLayout.PAGE_START);

        Action newGameAction = new AbstractAction("Új játék") {
            @Override
            public void actionPerformed(ActionEvent e) {
                board = new Board();
                setupBoard(board);
               // state = GameState.Playing;
            }
        };

        Action newGameFen = new AbstractAction("FEN megadása") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fen = JOptionPane.showInputDialog("Adja meg a FEN-t");
                board = new Board(fen);
                setupBoard(board);
               // state = GameState.Playing;
            }
        };

        toolbar.add(newGameAction);
        toolbar.add(newGameFen);
        toolbar.add(new JSeparator());

        Color bg = new Color(185, 220, 199);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(bg);

        panel.add(new JLabel("FEN -> "), BorderLayout.LINE_START);
        fen = new JLabel("");
        panel.add(fen, BorderLayout.CENTER);

        this.add(panel, BorderLayout.PAGE_END);


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

        // sorok számozva
        for (int row = 7; row >= 0; row--) {
            for (int column = 0; column <= 7; column++) {
                if (column == 0) {
                    board.add(new JLabel("" + (row + 1), SwingConstants.CENTER));
                }
                board.add(tiles[row][column]);
            }
        }

        // sarok
        board.add(new JLabel(""));

        // betűk
        for (int i = 0; i <= 7; i++) {
            board.add(new JLabel(COLS.substring(i, i + 1), SwingConstants.CENTER));
        }

        initImages();

        this.setVisible(true);
        this.setResizable(true);
        this.setTitle("ToldiSakk");


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
                    else
                        System.out.println("from: " + from.X() + " " + from.Y());
                } else {
                    to = theTile.getPos();
                    System.out.println("to: " + to.X() + " " + to.Y());
                    if (board.validMove(from, to)) {
                        board.executeMove(from, to, board.castling(from, to));
                        setupBoard(board);
                    } else {
                        System.out.println("bad move");
                    }

                    to = null;
                    from = null;
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

    public static void main(String[] args){
        GameGUI game = new GameGUI();
    }
}
