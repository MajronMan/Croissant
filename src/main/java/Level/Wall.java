package Level;
import Interface.UIwriter;
import javafx.scene.paint.Color;

public class Wall extends Cell {

	public Wall(){
        this(0, 0);
    }
    public Wall(int x, int y){
        super(x, y, Color.BURLYWOOD);
        this.walkable = false;
        visibility = new CellVisibility(true, true, this);
    }

    private boolean Solid;

	public void Destroy() {
		// TODO - implement Wall.Destroy
		throw new UnsupportedOperationException();
	}

    @Override
    public void Interact() {
        UIwriter.consoleWrite(visibility.opaque? "yes":"no");
    }
}