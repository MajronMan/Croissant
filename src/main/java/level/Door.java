package level;

import immaterial.enums.Visuals;
import immaterial.Vector;
import javafx.scene.paint.Color;

public class Door extends Pavement {

    private Color[] colors = {Color.BLUEVIOLET, Color.PURPLE};

    //Used for newInstance()
    public Door(){
        this(0, 0);
    }

    private Door(int x, int y) {
        position = new Vector(x, y);
        walkable = false;
        setVisual(colors[1], Visuals.Square);
        mainColor = colors[1];
        visibility = new CellVisibility(false, true, this);
    }

    private void toggleOpen() {
		walkable = !walkable;
        visual.setColor(colors[walkable? 0:1]);
        mainColor = colors[walkable? 0:1];
        visibility.setOpaque(!walkable);
        draw();
	}

	@Override
    public boolean interact(){
        if(pedestrian != null )
            return pedestrian.interact();
        if(content != null)
            return content.interact();
        toggleOpen();
        return true;
    }

}