package util;

import java.io.Serializable;

/**
 * Stores a position, x and y values.
 * Has equals and hashCode functions.
 * Serializable because of io storage.
 */
public class Pos implements Serializable {
	private final int x;
	private final int y;
	
	public Pos() {
		x = -1;
		y = -1;
	}
	
	public Pos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int X() {
		return x;
	}
	
	public int Y() {
		return y;
	}

	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(!(o instanceof Pos))
			return false;
		Pos pos = (Pos) o;
		return this.x == pos.x && this.y == pos.y;
	}

	@Override
	public int hashCode(){
		return x ^ y;
	}
}
