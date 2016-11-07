package Croissant.Level;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


import java.util.ArrayList;
import sun.misc.Queue;
import java.util.Random;

import static Croissant.Engine.Constants.cellSize;

public class Map {

	private Cell[][] Cells;
	private int depth;
    public int x;
    public int y;

	public Map() {
        x = 30;
        y = 30;
        Cells = new Cell[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                Cells[i][j] = new Cell(i, j);
            }
        }
        //SE();
        //BFS();
        //recursive(0, x, 0, y);
        gameOfLife(0);
	}

	private boolean outOfBounds(int x, int y){
        return x<0 || x >= this.x || y < 0 || y >= this.y;
    }

    private int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public Cell getCellAt(int x, int y){
        return Cells[y][x];
    }

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

    private int dirs9[][] = {{1, 1}, {0, 1}, {1, 0}, {0, -1}, {-1, -1}, {-1, 0}, {1, -1}, {-1, 1}};
    private ArrayList<Cell> lifeNeighbours(int i, int j){
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

    /*
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

	public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setStroke(Color.ALICEBLUE);
        for(Cell cellsRow[] : Cells)
            for(Cell cell: cellsRow) {
                graphicsContext.setFill(cell.color);
                graphicsContext.strokeRect(cellSize * cell.x, cellSize * cell.y, cellSize, cellSize);
                graphicsContext.fillRect(cellSize * cell.x, cellSize * cell.y, cellSize, cellSize);
            }

	}
}