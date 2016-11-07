package Croissant.Engine;

import Croissant.Characters.Player;
import Croissant.Level.Map;
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
			currentMap.draw(graphicsContext);
			thePlayer.Draw(graphicsContext);
	}

	public void Tick() {
		// TODO - implement GameController.Tick
		throw new UnsupportedOperationException();
	}

	public void BeginGame(){
        currentMap = new Map();
		thePlayer = new Player(this);
		currentMap.draw(graphicsContext);
    }
}