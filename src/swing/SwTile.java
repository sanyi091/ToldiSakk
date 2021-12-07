package swing;

import util.Pos;

import javax.swing.*;

public class SwTile extends JButton {
    private Pos pos;

    public Pos getPos(){
        return pos;
    }

    public void setPos(Pos pos){
        this.pos = pos;
    }


}
