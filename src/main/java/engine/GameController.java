package engine;

import immaterial.Vector;
import characters.Enemy;
import characters.Player;
import gui.UIwriter;
import level.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Arrays;

import static engine.Constants.cellSize;
import static engine.Constants.mapX;
import static engine.Constants.mapY;

public class GameController {
    private static GameController ourInstance = new GameController();
	private ArrayList<Enemy> enemies = new ArrayList<>();
	private ArrayList<Map> maps = new ArrayList<>();
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
        if(!thePlayer.isAlive()) return;
        String code = e.getCode().toString();
        if(code.equals("TAB")){
            UIwriter.toggleHistory();
            return;
        }

        String[] s = new String[] {"E", "SHIFT", "UP", "DOWN", "LEFT", "RIGHT"};
        if(Arrays.stream(s).anyMatch((element) -> element.equals(code)))
            tick(code);
    }

    private void checkNextMap(){
        int delta = currentMap.isExit(thePlayer.getPosition());
        int index = delta + currentMap.depth;
        if(delta != 0 && index >= 0) {
            try {
                currentMap = maps.get(index);
            } catch (Exception ex) {
                System.out.println("new map");
                currentMap = new Map(index);
                currentMap.generateEnemies();
                maps.add(currentMap);
            } finally {
                currentMap.draw();
                if(delta > 0)
                    thePlayer.setPosition(currentMap.entryPosition());
                if(delta < 0)
                    thePlayer.setPosition(currentMap.exitPosition());
            }
        }
    }

    private void tick(String code) {
        UIwriter.clearHistory();
        enemies.forEach(Enemy::roam);
        String[] s = new String[] {"UP", "DOWN", "LEFT", "RIGHT"};
        if(Arrays.stream(s).anyMatch((element) -> element.equals(code))) {
            thePlayer.handleInput(code);
            checkNextMap();
        }
        if(code.equals("E")){
            if(!currentMap.getCellAt(thePlayer.getPosition().sum(thePlayer.getFacing())).interact())
                currentMap.getCellAt(thePlayer.getPosition()).interact();
        }

        thePlayer.raytrace();
        currentMap.draw();
        thePlayer.draw();
    }

    private void _handleMouseInput(MouseEvent e){
        int x = (int) e.getX()/cellSize, y = (int) e.getY()/cellSize;
        if(x >= mapX || y >= mapY || new Vector(x, y).distance(thePlayer.getPosition())>1.5)
            return;

        currentMap.getCellAt(x, y).interact();
        enemies.forEach(Enemy::roam);
        thePlayer.raytrace();
        currentMap.draw();
        thePlayer.draw();
    }

	public static void BeginGame(){
        ourInstance.currentMap = new Map(0);
        ourInstance.currentMap.generateEnemies();
        ourInstance.maps.add(ourInstance.currentMap);
        ourInstance.thePlayer = new Player();
        UIwriter.HPChanged();
        UIwriter.statsChanged();
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

    public static Player getPlayer() {
        return ourInstance.thePlayer;
    }

    public static void addEnemy(Enemy theEnemy){ ourInstance.enemies.add(theEnemy); }

    public static void removeEnemy(Enemy theEnemy) { ourInstance.enemies.remove(theEnemy); }

}