package swing;

import util.Pos;

import javax.swing.*;

/**
 * Class that extends JButton with the little extra of storing simple coordinates.
 */
public class SwTile extends JButton {
    private Pos pos;

    public Pos getPos(){
        return pos;
    }

    public void setPos(Pos pos){
        this.pos = pos;
    }


}
