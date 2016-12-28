package Engine;

import Characters.Player;
import Level.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import static Engine.Constants.cellSize;
import static Engine.Constants.mapX;
import static Engine.Constants.mapY;

public class GameController {
    private static GameController ourInstance = new GameController();
    public static GameController getInstance() {
        return ourInstance;
    }

	private ArrayList<GameObject> Actors = new ArrayList<>();
	private ArrayList<Map> Maps = new ArrayList<>();
    private GraphicsContext graphicsContext;
    private Map currentMap;
	private Player thePlayer;

    public GameController() {}

    public static void handleKeyboardInput(KeyEvent e) {
        ourInstance._handleKeyboardInput(e);
	}


    public static void handleMouseInput(MouseEvent mouseEvent) {
        ourInstance._handleMouseInput(mouseEvent);
    }

	private void _handleKeyboardInput(KeyEvent e){
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
                currentMap.draw();
                if(delta > 0)
                    thePlayer.setPosition(currentMap.entryPosition());
                if(delta < 0)
                    thePlayer.setPosition(currentMap.exitPosition());
            }
        }
        thePlayer.raytrace();
        currentMap.draw();
        thePlayer.draw();
    }

    private void _handleMouseInput(MouseEvent e){
        int x = (int) e.getX()/cellSize, y = (int) e.getY()/cellSize;
        if(x >= mapX || y >= mapY)
            return;
        currentMap.getCellAt(x, y).Interact();
    }

	public static void BeginGame(){
        ourInstance.currentMap = new Map(0);
        ourInstance.Maps.add(ourInstance.currentMap);
        ourInstance.thePlayer = new Player();
        ourInstance.thePlayer.raytrace();
        ourInstance.currentMap.draw();
        ourInstance.thePlayer.draw();
        ourInstance.currentMap.createEntry(ourInstance.thePlayer.getPosition());
    }

    public static GraphicsContext getGraphicsContext(){
		return ourInstance.graphicsContext;
	}

	public static void setGraphicsContext(GraphicsContext gc) { ourInstance.graphicsContext = gc; }

    public static Map getCurrentMap() {
        return ourInstance.currentMap;
    }

    public static void setCurrentMap(Map currentMap) {
        ourInstance.currentMap = currentMap;
    }

    public static Player getPlayer() {
        return ourInstance.thePlayer;
    }

    public static void setPlayer(Player thePlayer) {
        ourInstance.thePlayer = thePlayer;
    }

}