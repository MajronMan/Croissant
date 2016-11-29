package Level;

import Level.Cell;
import javafx.scene.paint.Color;

public class Wall extends Cell {

	public Wall(){
        this(0, 0);
    }
    public Wall(int x, int y){
        super(x, y, Color.BURLYWOOD);
        this.walkable = false;
    }

    private boolean Solid;

	public void Destroy() {
		// TODO - implement Wall.Destroy
		throw new UnsupportedOperationException();
	}



}