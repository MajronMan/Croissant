package Abstract;

import Abstract.Enums.Visuals;
import javafx.scene.paint.Color;

/**
 * Created by MajronMan on 30.11.2016.
 */
public class VisualRepresentation {
    private Color color;
    private Visuals shape;
    private Color outline;

    public VisualRepresentation(Color color, Visuals shape){
        this(color, shape, null);
    }

    public VisualRepresentation(Color color, Visuals shape, Color outline) {
        this.color = color;
        this.shape = shape;
        this.outline = outline;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color c){ color = c; }

    public Visuals getShape() {
        return shape;
    }

    public boolean hasOutline() {
        return outline != null;
    }

    public Color getOutline() {
        return outline;
    }

    public void setOutline(Color color) {
        outline = color;
    }

    public void setShape(Visuals shape) {
        this.shape = shape;
    }
}

