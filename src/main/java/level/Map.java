package level;

import immaterial.Graph;
import immaterial.interfaces.IDrawable;
import immaterial.Vector;
import characters.Enemy;
import engine.GameController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import static engine.Constants.mapX;
import static engine.Constants.mapY;
import static java.lang.Integer.signum;

public class Map implements IDrawable{

	private Cell[][] Cells;
	public int depth;
    public int x;
    public int y;
    public ArrayList<Room> Rooms = new ArrayList<>();
    public int[][] distances;
    public Graph<Room> roomGraph;
    private Vector exit = null;
    private Vector entry = null;

    public Map(int depth) {
        this.depth = depth;
        x = mapX;
        y = mapY;
        Cells = new Cell[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                Cells[i][j] = new Cell(j, i);
                Cells[i][j].setMap(this);
            }
        }
        generateRooms();
        getGraph();
        drawCorridors();
        createExits();
	}

	// for tests only
	public Map(boolean [][] cells, int x, int y){
        this.x = x;
        this.y = y;
        Cells = new Cell[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if(cells[i][j]) {
                    Cells[i][j] = new Pavement(j, i);
                }
                else {
                    Cells[i][j] = new Wall(j, i);
                }
                Cells[i][j].setMap(this);
            }
        }
    }

    private void drawCorridors() {
	    roomGraph.Kruskal().forEach((Graph<Room>.Edge e) -> (e.getFrom().getValue()).drawPath(e.getTo().getValue()));
    }

    public void hide(){
        for (Cell[] row : Cells) {
            for (Cell c: row) {
                if(c.getIntensity() > 0.1)
                    c.setIntensity(0.15);
            }
        }
        draw();
    }
	boolean outOfBounds(int x, int y){
        return x<0 || x >= this.x || y < 0 || y >= this.y;
    }

    private int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public Cell getCellAt(int x, int y){
        try {
            return Cells[y][x];
        }catch(ArrayIndexOutOfBoundsException e){
            return null;
        }
    }

    public Cell getCellAt(Vector v){
        return getCellAt(v.getX(), v.getY());
    }

    boolean createCell(int x, int y, Class<? extends Cell> cls) {
        try {
            Cells[y][x] = cls.newInstance();
            Cells[y][x].setXY(x, y);
            Cells[y][x].setMap(this);
        }catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    boolean createCells(Collection<Vector> positions, Class<? extends Cell> cls){
        for (Vector v :
                positions) {
            if (!createCell(v.getX(), v.getY(), cls)){
                return false;
            }
        }
        return true;
    }

    private boolean newRoom() {
        Random r = new Random();
        int div = Math.min(x, y);
        Room room = new Room(r.nextInt(x), r.nextInt(y), 7+r.nextInt(div/6), 7+r.nextInt(div/6), this);
        return room.carve();
    }

    private void generateRooms(){
        for(int i=0; i < 200; i = newRoom()? i: i+1);
    }


    private void getGraph(){
        int n = Rooms.size();
        distances = new int[n][n];
        for (int i = 0; i < n; i++) {
            Rooms.get(i).number = i;
            for (int j = 0; j < n; j++) {
                distances[i][j] = Rooms.get(i).distance(Rooms.get(j));
            }
        }
        Room[] rooms = new Room[Rooms.size()];
        Rooms.toArray(rooms);
        roomGraph = new Graph<>(rooms, distances);
    }
	public void draw() {
        GraphicsContext graphicsContext = GameController.getGraphicsContext();
        graphicsContext.setStroke(Color.BLACK);
        for(Cell cellsRow[] : Cells) {
            for (Cell cell : cellsRow) {
                cell.draw();
            }
        }
	}

	private void createExits(){
        Random r = new Random();
        int number = r.nextInt(Rooms.size());
        exit = Rooms.get(number).addExit(1);
        if(depth > 0 ){
            int number2 = r.nextInt(Rooms.size());
            while(number == number2){
                number2 = r.nextInt(Rooms.size());
            }
            entry = Rooms.get(number2).addExit(-1);
        }
    }

    public Vector exitPosition() {
        return exit;
    }

    public Vector entryPosition(){
        return entry;
    }

    public void createEntry(Vector v){
        entry = v;
        createCell(v.getX(), v.getY(), Exit.class);
        Exit ex = (Exit) getCellAt(v.getX(), v.getY());
        ex.setXY(v.getX(), v.getY());
        ex.setElevate(-1);
    }
    public int isExit(Vector v){
        if( v.equals(exit))
            return 1;
        if( v.equals(entry))
            return -1;
        return 0;
    }

    public void generateEnemies(){
        Random r = new Random();
        for(int i=0; i<10; i++){
            int rn = r.nextInt(Rooms.size());
            GameController.addEnemy( addEnemy(Rooms.get(rn)));
        }
    }
    private Enemy addEnemy(Room room){
        Enemy enemy = Enemy.newRandomStats(this);
        Vector inside = room.randomInside();
        enemy.setPosition(inside);
        ((Pavement) getCellAt(inside)).addPedestrian(enemy);
        System.out.println(inside);
        return enemy;
    }

    private Cell pathStep(Vector from, Vector to){
        Vector delta = to.substract(from);
        Vector signs = new Vector(signum(delta.getX()), signum(delta.getY()));
        ArrayList<Vector> possibilities = new ArrayList<>();
        possibilities.add(from.sum(signs));
        if(signs.getY() != 0 && signs.getX() != 0){
            possibilities.add(from.sum(new Vector(signs.getX(), 0)));
            possibilities.add(from.sum(new Vector(0, signs.getY())));
        }
        for(Vector v: possibilities){
            Cell pav = getCellAt(v);
            if(pav != null && pav.getWalkable())
                return pav;
        }
        return null;
    }

    public ArrayList<Cell> getPath(Vector from, Vector to){
        Cell next = pathStep(from, to);
        if(next == null) return null;
        ArrayList<Cell> ret = new ArrayList<>();
        while(to.distance(from)>1.5){
            ret.add(next);
            next = pathStep(from, to);
            if(next == null)
                return null;
            from = next.getPosition();
        }
        return ret;
    }
}