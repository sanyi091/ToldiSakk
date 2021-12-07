package util;

/**
 * Stores a type and a color, giving a unique identifier to a piece.
 * Has equals and hashCode functions.
 */
public class PieceKey {
    PieceType type;
    Team color;

    public PieceKey(PieceType type, Team color) {
        this.color = color;
        this.type = type;
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!(o instanceof PieceKey))
            return false;
        PieceKey key = (PieceKey) o;
        return this.type == key.type && this.color == key.color;
    }

    @Override
    public int hashCode(){
        return type.toString().hashCode() * color.toString().length();
    }
}
