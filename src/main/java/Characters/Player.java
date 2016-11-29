package Characters;

import Abstract.IMoving;
import Abstract.Vector;
import Engine.GameController;
import Items.Equipment;
import Items.Item;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static Engine.Constants.cellSize;

public class Player extends Fighting implements IMoving {

	private String Class;
	private Item Backpack;
	private Equipment Eq;
	private String Race;
	private int posX;
    private int posY;
    private GameController gameController;

    public Player(GameController gameController){
        this.gameController = gameController;

        while(!gameController.currentMap.getCellAt(posX, posY).getWalkable()){
            posX++;
            if(posX >= gameController.currentMap.x) {
                posX = 0;
                posY = (posY + 1) % gameController.currentMap.y;
            }
        }

    }

	public void Move(String dir){
        gameController.currentMap.getCellAt(posX, posY).draw(gameController.getGraphicsContext());
        int destx = posX, desty = posY;
        switch(dir) {
            case "LEFT":
                destx = Math.max(0, posX - 1);
                break;
            case "RIGHT":
                destx = Math.min(posX + 1, gameController.currentMap.x - 1);
                break;
            case "UP":
                desty = Math.max(0, posY - 1);
                break;
            case "DOWN":
                desty = Math.min(posY + 1, gameController.currentMap.y - 1);
                break;
        }
        if(gameController.currentMap.getCellAt(destx, desty).getWalkable()) {
            posX = destx;
            posY = desty;
        }
    }

	public void Move(){

    }

    public void draw(){
        GraphicsContext graphicsContext = gameController.getGraphicsContext();
        graphicsContext.setFill(Color.DARKSLATEBLUE);
        graphicsContext.fillOval(posX *cellSize, posY*cellSize, cellSize-1, cellSize-1);
    }

    public Vector getPosition() {
        return new Vector(posX, posY);
    }

    public void setPosition(Vector position) {
        this.posX = position.getX();
        this.posY = position.getY();
    }
}