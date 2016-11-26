package Croissant.Level;

import Croissant.Abstract.Vector;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by MajronMan on 16.11.2016.
 */
public class Room{
    private int x;
    private int y;
    private int w;
    private int h;
    int number;

    public Room(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    @Override
    public String toString() {
        return "Room no " + number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        return  x == room.x &&
                y == room.y &&
                w == room.w &&
                h == room.h &&
                number == room.number;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + w;
        result = 31 * result + h;
        result = 31 * result + number;
        return result;
    }

    public boolean outtaBounds(Map map){
        return x>0 && y>0 && x+w < map.x && y+h < map.y;
    }

    public boolean collides(Map map){
        if(map.outOfBounds(x, y) || map.outOfBounds(x+w, y+h))
            return true;
        // leave at least 3 free cells
        for (int i = Math.max(0, x-3); i < Math.min(map.x, x+w+3); i++) {
            for (int j = Math.max(0, y-3); j < Math.min(map.y, y+h+3); j++) {
                if(map.getCellAt(i, j).getWalkable())
                    return true;
            }
        }
        return false;
    }

    public boolean carve(Map map){
        if(collides(map)) return false;
        createBorder(map);
        for (int i = x+1; i < x+w-1; i++) {
            for (int j = y+1; j < y+h-1; j++) {
                map.createCell(i, j, Pavement.class);
            }
        }
        map.Rooms.add(this);
        return true;
    }
    private void createBorder(Map map){
        ArrayList<Vector> vectors = new ArrayList<>();
        for(int i = x; i< x+w; i++){
            vectors.add(new Vector(i, y));
            vectors.add(new Vector(i, y+h-1));
        }
        for(int i=y; i<y+h; i++){
            vectors.add(new Vector(x, i));
            vectors.add(new Vector(x+w-1, i));
        }
        map.createCells(vectors, Wall.class);
    }

    public void indicateNumber(Map map) {
        int c = 0;
        for (int i = x; i < x + w; i++) {
            for (int j = y; j < y + h; j++) {
                if(c == number) return;
                c++;
                map.getCellAt(i, j).color = Color.RED;
            }
        }
    }

    private int sign(int n){
        if(n < 0) return -1;
        if (n == 0) return 0;
        return 1;
    }

    public void drawPath(Map map, Room to){
        int cx = middleX(), cy = middleY(), tx = to.middleX(), ty = to.middleY();
        int dx = sign(tx-cx);
        int dy = sign(ty-cy);
        Random r = new Random();

        if(x > tx) {
            cx = x-1;
            map.createCell(cx + 1, cy, Door.class);
        }
        else if (x + w - 1 < tx) {
            cx = x+w;
            map.createCell(cx - 1, cy, Door.class);
        }
        else if (y > ty) {
            cy = y-1;
            map.createCell(cx, cy + 1, Door.class);
        }
        else if(y+h-1 < ty) {
            cy = y+h;
            map.createCell(cx, cy-1, Door.class);
        }

        map.createCell(cx, cy, Pavement.class);
        cx += dx;
        map.createCell(cx, cy, Pavement.class);
        cy+=dy;
        map.createCell(cx, cy, Pavement.class);
        while(cx != tx || cy != ty){

            if(r.nextInt(6) == 0) {
                if (cx != tx)
                    cx += dx;
                else
                    cy += dy;
            }
            else{
                if (cy != ty)
                    cy += dy;
                else
                    cx += dx;
            }
            if(map.getCellAt(cx, cy).getClass().equals(Wall.class)) {
                map.createCell(cx, cy, Door.class);
                return;
            }
            map.createCell(cx, cy, Pavement.class);
        }
    }

    public int middleX(){
        return x + w/2;
    }
    public int middleY() {
        return y + h / 2;
    }
    public Vector left() { return new Vector(x, middleY()); }
    public Vector right() {return new Vector(x + w - 1, middleY()); }
    public Vector top() { return new Vector(middleX(), y); }
    public Vector bottom() {return new Vector(middleX(), y + h - 1); }
    public int distance(Room a){
        return Math.abs(middleX() - a.middleX()) + Math.abs(middleY() - a.middleY());
    }
}
