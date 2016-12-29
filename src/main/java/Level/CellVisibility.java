package Level;
import Abstract.Photon;
import Abstract.Vector;

import java.util.ArrayList;

import static Engine.Constants.sight;

/**
 * Created by MajronMan on 28.12.2016.
 */
public class CellVisibility {
    public boolean visible;
    public boolean opaque;
    public double intensity = 0.0;
    public boolean visited;
    public Cell myCell;


    public CellVisibility(boolean visible, boolean opaque, Cell cell){
        this.visible = visible;
        this.opaque = opaque;
        this.myCell = cell;
    }

    public ArrayList<Cell> getNeighbours(){
        ArrayList<Cell> ret = new ArrayList<>();
        Vector v = new Vector(1, 0);
        for(double alfa = 0; alfa<361; alfa += 45){
            v = v.rotate(alfa);
            Cell cell = myCell.getMap().getCellAt(myCell.getPosition().sum(v));
            if(cell != null)
                ret.add(cell);
        }
        return ret;
    }

    public void see() {
        visible = true;
        myCell.draw();
    }


    public void raytrace(Vector forward){
        Map map = myCell.getMap();
        map.hide();
        myCell.setIntensity(1.0);

        ArrayList<Photon> currentGeneration = new ArrayList<>();
        ArrayList<Photon> lastGeneration = new ArrayList<>();
        Photon.startingPoint = myCell.getPosition();
/*
        for(Cell neighbour: getNeighbours()){
            Vector delta = neighbour.getPosition().substract(myCell.getPosition());
            Photon photon = new Photon(0, sight, delta, neighbour);
            photons.add(photon);
        }*/
        for(double alfa = 0.0; alfa<360; alfa+=45) {
            Vector v = forward.rotate(alfa);
            Vector target = v.sum(myCell.getPosition());
            if (map.getCellAt(target) != null) {
                currentGeneration.add(new Photon(v, map.getCellAt(target), 1, alfa));
            }
        }

        while(!currentGeneration.isEmpty()) {
            lastGeneration.addAll(currentGeneration);
            currentGeneration.clear();
            while (!lastGeneration.isEmpty()) {
                Photon photon = lastGeneration.remove(0);
                photon.HuygensKirchhoff(currentGeneration, map);
            }
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isOpaque() {
        return opaque;
    }

    public void setOpaque(boolean opaque) {
        this.opaque = opaque;
    }
/*
    private void BFS() {
        GameController.getCurrentMap().HideCells();
        Queue<Cell> q = new Queue<>();
        q.enqueue(myCell);
        visible = true;
        while (!q.isEmpty()) {
            try {
                Cell p = q.dequeue();
                p.getVisibility().visited = true;
                p.color = cur;
                neighbours(p.x, p.y).stream().filter(f -> f.getWalkable() && !f.visited).forEach(q::enqueue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/
}
