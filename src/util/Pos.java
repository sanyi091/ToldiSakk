package util;

public class Pos{
	int x;
	int y;
	
	public Pos() {
		x = -1;
		y = -1;
	}
	
	public Pos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
		
	public void setX(int x) {
		this.x = x;
	}
		
	public void setY(int y) {
		this.y =  y;
	}
		
}
