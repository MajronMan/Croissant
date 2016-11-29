package Engine;

import Abstract.Vector;
import Characters.Player;
import Level.Cell;
import Level.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class GameController {

	private ArrayList<GameObject> Actors = new ArrayList<>();
	private ArrayList<Map> Maps = new ArrayList<>();
    private GraphicsContext graphicsContext;
    public Map currentMap;
	public Player thePlayer;

    public GameController(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    public void HandleInput(KeyEvent e) {
        String code = e.getCode().toString();
        thePlayer.Move(code);
        int delta = currentMap.isExit(thePlayer.getPosition());
        int index = delta + currentMap.depth;
        if(delta != 0 && index >= 0) {
            try {
                currentMap = Maps.get(index);
            } catch (Exception ex) {
                System.out.println("new map");
                currentMap = new Map(index);
                Maps.add(currentMap);
            } finally {
                currentMap.draw(graphicsContext);
                if(delta > 0)
                    thePlayer.setPosition(currentMap.entryPosition());
                if(delta < 0)
                    thePlayer.setPosition(currentMap.exitPosition());
            }
        }
        thePlayer.draw();
	}

	public void BeginGame(){
        currentMap = new Map(0);
        Maps.add(currentMap);
		thePlayer = new Player(this);
		currentMap.draw(graphicsContext);
		thePlayer.draw();
        currentMap.createEntry(thePlayer.getPosition());
    }

    public GraphicsContext getGraphicsContext(){
		return graphicsContext;
	}
}