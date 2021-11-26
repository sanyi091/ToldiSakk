package swing;

import game.Board;
import game.Tile;
import pieces.Piece;
import util.PieceKey;
import util.Team;
import util.PieceType;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class GameGUI  extends JFrame {
    private JButton[][] tiles = new JButton[8][8];
    private JPanel board;
    private Board game = new Board();
    private static final String COLS = "ABCDEFGH";
    private final HashMap<PieceKey, ImageIcon> icons = new HashMap<>();

    public GameGUI(){
        this.setLayout(new BorderLayout(3, 3));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(800,800));
        this.setVisible(true);
        this.setResizable(true);
        this.setTitle("ToldiSakk");
        JToolBar toolbar = new JToolBar();
        this.add(toolbar, BorderLayout.PAGE_START);

        Action newGameAction = new AbstractAction("Új játék") {
            @Override
            public void actionPerformed(ActionEvent e) {
                game = new Board();
            }
        };

        toolbar.add(newGameAction);
        toolbar.add(new JSeparator());


        board = new JPanel(new GridLayout(0, 9)){
            @Override
            public Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                Dimension prefSize;
                Component c = getParent();
                if (c == null) {
                    prefSize = new Dimension(
                            (int)d.getWidth(),(int)d.getHeight());
                } else if (c!=null &&
                        c.getWidth()>d.getWidth() &&
                        c.getHeight()>d.getHeight()) {
                    prefSize = c.getSize();
                } else {
                    prefSize = d;
                }
                int w = (int) prefSize.getWidth();
                int h = (int) prefSize.getHeight();
                // the smaller of the two sizes
                int s = (Math.min(w, h));
                return new Dimension(s,s);
            }
        };

        board.setBorder(new CompoundBorder(
                new EmptyBorder(8,8,8,8),
                new LineBorder(Color.DARK_GRAY)
        ));

        board.setBackground(Color.cyan);
        JPanel boardConstraint = new JPanel(new GridLayout());
        boardConstraint.setBackground(Color.cyan);
        boardConstraint.add(board);
        this.add(boardConstraint);


        Insets buttonMargin = new Insets(0,0,0,0);
        for(int row = 7; row >= 0; row--){
            for(int column = 0; column <= 7; column++){
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(64,64,BufferedImage.TYPE_INT_ARGB)
                );
                b.setIcon(icon);
                if((column % 2 == 1 && row % 2 == 0) || (column % 2 == 0 && row % 2 == 1))
                    b.setBackground(Color.WHITE);
                else
                    b.setBackground(Color.BLACK);
                tiles[column][row] = b;
            }
        }

        board.add(new JLabel(""));
        // fill the top row
        for (int i = 0; i < 8; i++) {
            board.add(
                    new JLabel(COLS.substring(i, i + 1),
                            SwingConstants.CENTER));
        }
        // fill the black non-pawn piece row
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (j) {
                    case 0:
                        board.add(new JLabel("" + (9-(i + 1)), SwingConstants.CENTER));
                    default:
                        board.add(tiles[i][j]);
                }
            }
        }

        initImages();

        Tile[][] fenTiles = game.getFen().getTiles();
        for(int row = 7; row >= 0; row--){
            for(int column = 0; column <= 7; column++){
                Piece piece = fenTiles[row][column].getPiece();
                if(piece != null) {
                    int s = tiles[0][0].getWidth();
                    ImageIcon ic = icons.get(new PieceKey(piece.getType(), piece.getColor()));
                    tiles[row][column].setIcon(new ImageIcon(imageScale(s, s, ic.getImage())));
                }
            }
        }
    }

    private Image imageScale(int w, int h, Image scrImage){
        BufferedImage resImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resImage.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(scrImage, 0, 0, w, h, null);
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

    public static void main(String[] args){
        GameGUI asd = new GameGUI();
    }
}
