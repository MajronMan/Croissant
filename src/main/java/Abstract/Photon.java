package Abstract;

import Level.Map;
import Level.Cell;

import java.util.ArrayList;

/**
 * Created by MajronMan on 28.12.2016.
 */
public class Photon {
    public int distance;
    public int threshold;
    public Vector direction;
    public Cell myCell;

    public Photon(int distance, int threshold, Vector direction, Cell myCell) {
        this.distance = distance;
        this.threshold = threshold;
        this.direction = direction;
        this.myCell = myCell;
    }

    public void Huyghens(ArrayList<Photon> photons, Map map){
        if(myCell.getVisibility().isVisible())
            return;
        myCell.setVisible(true);
        if(distance == threshold || myCell.getVisibility().isOpaque())
            return;

        Vector[] to = { direction, direction.rotate(45), direction.rotate(-45)};
        for(Vector v: to) {
            Vector v2 = myCell.getPosition().sum(v);
            Cell nextCell = map.getCellAt(v2.getX(), v2.getY());

            if (nextCell != null) {
                Photon next = new Photon(distance + 1, threshold, v, nextCell);
                photons.add(next);
            }
        }
    }

    @Override
    public String toString() {
        return "Photon{" +
                "distance=" + distance +
                ", threshold=" + threshold +
                ", direction=" + direction +
                ", myCell=" + myCell +
                '}';
    }
}
