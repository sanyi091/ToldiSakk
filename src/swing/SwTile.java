package swing;

import util.Pos;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SwTile extends JButton {
    private Pos pos;

    public SwTile() {
        Insets buttonMargin = new Insets(0, 0, 0, 0);
        this.setMargin(buttonMargin);
        ImageIcon icon = new ImageIcon(
                new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB)
        );
        this.setIcon(icon);
    }

    public Pos getPos(){
        return pos;
    }

    public void setPos(Pos pos){
        this.pos = pos;
    }


}
