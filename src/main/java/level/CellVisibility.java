package level;
import immaterial.Photon;
import immaterial.Vector;

import java.util.ArrayList;

/**
 * Created by MajronMan on 28.12.2016.
 */
public class CellVisibility {
    boolean visible;
    private boolean opaque;
    public double intensity = 0.0;
    private Cell myCell;


    CellVisibility(boolean visible, boolean opaque, Cell cell){
        this.visible = visible;
        this.opaque = opaque;
        this.myCell = cell;
    }

    public void raytrace(Vector forward){
        Map map = myCell.getMap();
        map.hide();
        myCell.setIntensity(1.0);

        ArrayList<Photon> currentGeneration = new ArrayList<>();
        ArrayList<Photon> lastGeneration = new ArrayList<>();
        Photon.startingPoint = myCell.getPosition();

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
        return intensity > 0.151;
    }

    public boolean isOpaque() {
        return opaque;
    }

    void setOpaque(boolean opaque) {
        this.opaque = opaque;
    }
}
