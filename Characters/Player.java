/**
 * Singleton
 */
package Croissant.Characters;

import Croissant.Abstract.IMoving;
import Croissant.Characters.Fighting;
import Croissant.Engine.GameController;
import Croissant.Items.Equipment;
import Croissant.Items.Item;
import Croissant.Level.Cell;
import Croissant.Level.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static Croissant.Engine.Constants.cellSize;

public class Player extends Fighting implements IMoving {

	private String Class;
	private Item Backpack;
	private Equipment Eq;
	private String Race;
	public int posX;
    public int posY;
    public GameController gameController;

    public Player(GameController gameController){
        this.gameController = gameController;
        /*
        while(!gameController.currentMap.getCellAt(posX, posY).Walkable){
            posX++;
            if(posX > gameController.currentMap.x){

            }
            posY = (posY+1)%gameController.currentMap.y;
        }
        */
    }

	public void Move(String dir){
        switch(dir){
            case "LEFT":
                posX--;
                break;
            case "RIGHT":
                posX++;
                break;
            case "UP":
                posY--;
                break;
            case "DOWN":
                posY++;
                break;
        }

        int nx = Math.min(gameController.currentMap.x-1, Math.max(0, posX));
        int ny = Math.min(gameController.currentMap.y-1, Math.max(0, posY));
        if(gameController.currentMap.getCellAt(nx, ny).getWalkable()){
            posX = nx;
            posY = ny;
        }
	}

	public void Move(){

    }

    public void Draw(GraphicsContext graphicsContext){
        graphicsContext.setFill(Color.DARKSLATEBLUE);
        graphicsContext.fillRect(posX *cellSize, posY*cellSize, cellSize-1, cellSize-1);
    }
}