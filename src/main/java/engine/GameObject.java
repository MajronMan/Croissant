package engine;

import immaterial.*;
import immaterial.enums.Visuals;
import immaterial.interfaces.IDrawable;
import immaterial.interfaces.IInteractable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import static engine.Constants.cellSize;

public abstract class GameObject implements IDrawable, IInteractable {
    protected VisualRepresentation visual;
    protected Vector position;

    public VisualRepresentation getVisual() {
        return visual;
    }

    public void setVisual(Color color, Visuals shape){
        this.visual = new VisualRepresentation(color, shape);
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getPosition(){
        return position;
    }

    public void draw(){
        draw(visual.getColor());
    }

	public void draw(Color color) {
        GraphicsContext graphicsContext = GameController.getGraphicsContext();
        graphicsContext.setFill(color);
        int bx, by, bsize;
        int size = bsize = cellSize;
        int x = bx = position.getX()*cellSize, y = by = position.getY()*cellSize;
        if (visual.hasOutline()) {
            graphicsContext.setStroke(visual.getOutline());
            size--;
            x++;
            y++;
        }

        switch(visual.getShape()){
            case Square:
                graphicsContext.fillRect(x, y, size, size);
                if(visual.hasOutline()){
                    graphicsContext.strokeRect(bx, by, bsize, bsize);
                }
                break;
            case Circle:
                graphicsContext.fillOval(x, y, size, size);
                if(visual.hasOutline()){
                    graphicsContext.strokeOval(bx, by, bsize, bsize);
                }
                break;
            case Diamond:
                double xPoints[] = {x+size/2, x+size, x+size/2, x};
                double yPoints[] = {y, y+size/2, y+size, y+size/2};
                graphicsContext.fillPolygon(xPoints, yPoints, 4);
                if(visual.hasOutline()){
                    double xPoints2[] = {bx+bsize/2, bx+bsize, bx+bsize/2, bx};
                    double yPoints2[] = {by, by+bsize/2, by+bsize, by+bsize/2};
                    graphicsContext.strokePolygon(xPoints2, yPoints2, 4);
                }
                break;
            case Arc:
                graphicsContext.fillArc(x, y, size, size, 135, 270, ArcType.CHORD);
                if(visual.hasOutline()){
                    graphicsContext.strokeArc(bx, by, bsize, bsize, 135, 270, ArcType.CHORD);
                }
                break;
            case Triangle:
                double xPoints2[] = {x+size/2, x+size, x};
                double yPoints2[] = {y, y+size, y+size};
                graphicsContext.fillPolygon(xPoints2, yPoints2, 3);
                if(visual.hasOutline()){
                    double xPoints3[] = {bx+bsize/2, bx+bsize, bx};
                    double yPoints3[] = {by, by+bsize, by+bsize};
                    graphicsContext.strokePolygon(xPoints3, yPoints3, 3);
                }
                break;
            case ArrowU:
                double xPoints3[] = {x+size/2, x+size, x+size/2, x};
                double yPoints3[] = {y, y+size, y+2*size/3, y+size};
                graphicsContext.fillPolygon(xPoints3, yPoints3, 4);
                break;
            case ArrowD:
                double xPoints4[] = {x+size/2, x+size, x+size/2, x};
                double yPoints4[] = {y+size, y, y+size/3, y};
                graphicsContext.fillPolygon(xPoints4, yPoints4, 4);
                break;
            case ArrowL:
                double xPoints5[] = {x, x+size, x+2*size/3, x+size};
                double yPoints5[] = {y+size/2, y, y+size/2, y+size};
                graphicsContext.fillPolygon(xPoints5, yPoints5, 4);
                break;
            case ArrowR:
                double xPoints6[] = {x+size, x, x+size/3, x};
                double yPoints6[] = {y+size/2, y, y+size/2, y+size};
                graphicsContext.fillPolygon(xPoints6, yPoints6, 4);
                break;
        }


    }

    @Override
    public void hide() {
        GraphicsContext graphicsContext = GameController.getGraphicsContext();
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(position.getX()*cellSize, position.getY()*cellSize, cellSize, cellSize);
    }
}