package Level;

import Abstract.Enums.Visuals;
import Abstract.Vector;
import javafx.scene.paint.Color;

public class Door extends Pavement {

	private boolean isOpen;
	private boolean hidden;
    private Color[] colors = {Color.BLUEVIOLET, Color.FIREBRICK};

    public Door(){
        this(0, 0);
    }

    public Door(int x, int y) {
        position = new Vector(x, y);
        walkable = true;
        setVisual(colors[1], Visuals.Square);
        mainColor = colors[1];
        visibility = new CellVisibility(false, true, this);
    }

    public void toggleOpen() {
		walkable = !walkable;
        visual.setColor(colors[walkable? 0:1]);
        mainColor = colors[walkable? 0:1];
        visibility.setOpaque(!walkable);
        draw();
	}

	@Override
    public void Interact(){
        toggleOpen();
    }

}