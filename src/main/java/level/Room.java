package level;

import immaterial.Vector;
import items.Equipment;
import items.Item;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Integer.signum;

/**
 * Created by MajronMan on 16.11.2016.
 */
class Room{
    private int x;
    private int y;
    private int w;
    private int h;
    int number;
    private Map myMap;

    Room(int x, int y, int w, int h, Map map){
        myMap = map;
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

    private boolean collides(){
        if(myMap.outOfBounds(x, y) || myMap.outOfBounds(x+w, y+h))
            return true;
        // leave at least 3 free cells
        for (int i = Math.max(0, x-3); i < Math.min(myMap.x, x+w+3); i++) {
            for (int j = Math.max(0, y-3); j < Math.min(myMap.y, y+h+3); j++) {
                if(myMap.getCellAt(i, j).getWalkable())
                    return true;
            }
        }
        return false;
    }

    boolean carve(){
        if(collides()) return false;
        createBorder();
        for (int i = x+1; i < x+w-1; i++) {
            for (int j = y+1; j < y+h-1; j++) {
                myMap.createCell(i, j, Pavement.class);
            }
        }
        myMap.Rooms.add(this);
        placeItem();
        return true;
    }
    private void createBorder(){
        ArrayList<Vector> vectors = new ArrayList<>();
        for(int i = x +1; i< x+w-1; i++){
            vectors.add(new Vector(i, y));
            vectors.add(new Vector(i, y+h-1));
        }
        for(int i=y+1; i<y+h-1; i++){
            vectors.add(new Vector(x, i));
            vectors.add(new Vector(x+w-1, i));
        }
        myMap.createCells(vectors, Wall.class);
    }

    void drawPath(Room to){
        int cx = middleX(), cy = middleY(), tx = to.middleX(), ty = to.middleY();
        int dx = signum(tx-cx);
        int dy = signum(ty-cy);
        Random r = new Random();

        if(x > tx) {
            cx = x-1;
            myMap.createCell(cx + 1, cy, Door.class);
        }
        else if (x + w - 1 < tx) {
            cx = x+w;
            myMap.createCell(cx - 1, cy, Door.class);
        }
        else if (y > ty) {
            cy = y-1;
            myMap.createCell(cx, cy + 1, Door.class);
        }
        else if(y+h-1 < ty) {
            cy = y+h;
            myMap.createCell(cx, cy-1, Door.class);
        }

        myMap.createCell(cx, cy, Pavement.class);
        cx += dx;
        myMap.createCell(cx, cy, Pavement.class);
        cy+=dy;
        myMap.createCell(cx, cy, Pavement.class);
        while(cx != tx || cy != ty){
            dx = signum(tx-cx);
            dy = signum(ty-cy);
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
            if(myMap.getCellAt(cx, cy).getClass().equals(Wall.class)) {
                myMap.createCell(cx, cy, Door.class);
                return;
            }
            myMap.createCell(cx, cy, Pavement.class);
        }
    }

    private int middleX(){
        return x + w/2;
    }
    private int middleY() {
        return y + h / 2;
    }
    int distance(Room a){
        return Math.abs(middleX() - a.middleX()) + Math.abs(middleY() - a.middleY());
    }

    Vector addExit(int elevate) {
        Random r = new Random();
        int ex = middleX() + r.nextInt(3);
        int ey = middleY() + r.nextInt(3);
        myMap.createCell(ex, ey, Exit.class);
        ((Exit)myMap.getCellAt(ex, ey)).setElevate(elevate);
        return new Vector(ex, ey);
    }

    private boolean placeItem(){
        Random r = new Random();
        int ix = x+1+r.nextInt(w-2), iy = y+1+r.nextInt(h-2);
        Pavement cell = (Pavement) myMap.getCellAt(ix, iy);
        Item item;
        if(r.nextBoolean()){
            item = new Item(cell);
        }
        else
            item = new Equipment(cell);

        cell.addContent(item);
        return true;
    }

    Vector randomInside(){
        Random r = new Random();
        int rx = x + r.nextInt(w-2)+1;
        int ry = y + r.nextInt(h-2)+1;
        return new Vector(rx, ry);
    }
}
