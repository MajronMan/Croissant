package Croissant.Level;

import javafx.scene.paint.Color;

public class Door extends Pavement {

	private boolean isOpen;
	private boolean hidden;

    public Door(){
        this(0, 0);
    }

    public Door(int x, int y) {
        this.x = x;
        this.y = y;
        this.walkable = true;
        this.color = Color.BLUEVIOLET;
    }

    public void Open() {
		// TODO - implement Door.Open
		throw new UnsupportedOperationException();
	}

}