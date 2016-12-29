package Level;

import Abstract.Graph;
import Abstract.Interfaces.IDrawable;
import Abstract.Vector;
import Engine.GameController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import static Engine.Constants.mapX;
import static Engine.Constants.mapY;

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

    private void drawCorridors() {
        for(Graph.Edge e : roomGraph.Kruskal()){
            ((Room) e.getFrom().getValue()).drawPath((Room) e.getTo().getValue());
        }
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
/*
	public void drawNextEdge(){
        try {
            Graph.Edge e = (Graph.Edge) mstIterator.next();
            ((Room) e.getFrom().getValue()).drawPath(this, (Room) e.getTo().getValue());
            System.out.println(e.getFrom().getValue() + "->" + e.getTo().getValue());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    */

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

    public boolean createCell(int x, int y, Class<? extends Cell> cls) {
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

    public boolean createCells(Collection<Vector> positions, Class<? extends Cell> cls){
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

    public void generateRooms(){
        for(int i=0; i < 200; i = newRoom()? i: i+1);
    }


    public void getGraph(){
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
/*
    private ArrayList<Cell> neighbours(int i, int j){
        ArrayList<Cell> neigh = new ArrayList<>();
        for (int[] dir :
                dirs) {
            int nx = i+dir[0], ny = j+dir[1];
            if (nx < x && nx >= 0 && ny < y && ny >= 0){
                neigh.add(Cells[ny][nx]);
            }
        }
        return neigh;
    }

    private ArrayList<Room> getNeighbours(int i){
        ArrayList<Room> ret = new ArrayList<>();
        for (int j = 0; j < Rooms.size(); j++) {
            if(i != j && !Rooms.get(j).visited){
                ret.add(Rooms.get(j));
            }
        }
        return ret;
    }

    private void Recurse(int i){
        ArrayList<Room> neigh = getNeighbours(i);
        while(!neigh.isEmpty()) {
            Room room = neigh.get(0);
            neigh.remove(0);
            room.visited = true;
            room.drawPath(this, Rooms.get(i));
            Recurse(room.number);
        }
    }

    private void RoomDFS(){
        for (int i = 0; i < Rooms.size(); i++) {
            Room room = Rooms.get(i);
            if(!room.visited)
            {
                room.visited = true;
                Recurse(i);
            }

        }
    }

    private void BFS(){
        double delta = 360.0 / (double)(x*y);
        double t = 0;
        for (Cell[] row : Cells) {
            for (Cell c: row) {
                if(c.getWalkable()){
                    c.color = Color.hsb(t, 1, 1);
                    t+=delta;
                }
            }
        }
        for(Cell[] row: Cells){
            for(Cell c: row){
                if(c.getWalkable() && !c.visited){
                    Queue<Cell> q = new Queue<>();
                    c.visited = true;
                    Color cur = c.color;
                    q.enqueue(c);
                    while(!q.isEmpty()){
                        try {
                            Cell p = q.dequeue();
                            p.visited = true;
                            p.color = cur;
                            neighbours(p.x, p.y).stream().filter(f -> f.getWalkable() && !f.visited).forEach(q::enqueue);
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
*/
    private int dirs9[][] = {{1, 1}, {0, 1}, {1, 0}, {0, -1}, {-1, -1}, {-1, 0}, {1, -1}, {-1, 1}};
    public ArrayList<Cell> neighbours(int i, int j){
        ArrayList<Cell> ret = new ArrayList<>();
        for (int[] dir :
                dirs9) {
            int nx = i + dir[0], ny = j + dir[1];
            if(!outOfBounds(nx, ny) && Cells[ny][nx].getWalkable()){
                ret.add(Cells[ny][nx]);
            }
        }
        return ret;
    }
    /*
    public void gameOfLife(int epoch){
        Random r = new Random(System.nanoTime());
        for (Cell[] row :
                Cells) {
            for (Cell cell :
                    row) {
                cell.setWalkable(3 == r.nextInt(15));
            }
        }
        while(epoch-- > 0) {
            iterLife();
        }
    }

    public void iterLife(){
        for (Cell[] row :
                Cells) {
            for (Cell cell :
                    row) {
                ArrayList<Cell> neighbours = lifeNeighbours(cell.x, cell.y);
                if (neighbours.size() == 3 )
                    cell.setWalkable(true);
                if (neighbours.size() > 5 )
                    cell.setWalkable(false);
            }
        }
    }

    private void recursive(int xl, int xr, int yt, int yb){
        int dx = xr-xl, dy = yb - yt;
        if(dx <3 || dy <3)
            return;
        Random r = new Random(System.nanoTime());
        int wallx = r.nextInt(dx), wally = r.nextInt(dy);
        for (int i = xl; i < xr; i++) {
            Cells[yt+wally][i].setWalkable(false);
        }
        for (int i = yt; i < yb; i++) {
            Cells[i][xl+wallx].setWalkable(false);
        }
        int door1x, door1y, door2x, door2y;
        if(wallx > 0) {
            door1x = r.nextInt(wallx);
            Cells[yt+wally][xl+door1x].setWalkable(true);
        }
        if(wallx < dx-1){
            door2x = r.nextInt(dx-wallx);
            Cells[yt+wally][xl+door2x].setWalkable(true);
        }
        if(wally > 0) {
            door1y = r.nextInt(wally);
            Cells[yt+door1y][xl+wallx].setWalkable(true);
        }
        if(wally < dy - 1){
            door2y = r.nextInt(dy-wally);
            Cells[yt+door2y][xl+wallx].setWalkable(true);
        }
        recursive(xl, xl+wallx, yt, yt+wally);
        recursive(xl+wallx+1, xr, yt, yt+wally);
        recursive(xl, xl+wallx, yt+wally+1, yb);
        recursive(xl+wallx+1, xr, yt+wally+1, yb);
    }

    private void SE(){
        Random r = new Random(System.nanoTime());
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                boolean b = r.nextBoolean();
                try{
                    Cells[j][i+1].setWalkable(b);
                    Cells[j+1][i].setWalkable(!b);
                }catch (ArrayIndexOutOfBoundsException e){

                }
            }
        }
    }

	private boolean backtrack(int cx, int cy){
        if(outOfBounds(cx, cy)) return false;
        switch(Cells[cy][cx].Walkable) {
            case 0:
                Cells[cy][cx].Walkable++;
                for (int[] dir :
                        dirs) {
                    backtrack(cx + dir[0], cy + dir[1]);
                }
                return true;
            case 1:
                Cells[cy][cx].color = Color.AQUAMARINE;
                Cells[cy][cx].Walkable++;
                return false;
            case 2:
                Cells[cy][cx].color = Color.BLACK;
                return false;
            default:
                return false;
        }

    }
    */

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
}