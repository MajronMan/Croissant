package Level;

import Engine.GameObject;
import Interface.UIwriter;
import javafx.scene.paint.Color;

public class Pavement extends Cell {

	private GameObject content;

    public Pavement(){
        this(0, 0);
    }

    public Pavement(int x, int y){
        this(x, y, null);
    }

    public Pavement(int x, int y, GameObject content) {
        super(x, y, Color.DARKORANGE);
        this.walkable = true;
        this.content = content;
    }

    public GameObject getContent() {
        return content;
    }

    public void removeContent(){
        content = null;
        draw();
    }

    public void addContent(GameObject content) {
        this.content = content;
        content.setPosition(this.position);
    }

    @Override
    public void draw(){
        super.draw();
        if(content != null)
            content.draw();
    }
    @Override
    public void Interact(){
        if(content!= null)
            content.Interact();
        else{
            UIwriter.consoleWrite(getClass().getName());
        }
    }
}