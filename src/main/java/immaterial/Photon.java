package immaterial;

import level.Map;
import level.Cell;

import java.util.ArrayList;

import static java.lang.Math.min;

/**
 * Created by MajronMan on 28.12.2016.
 */
public class Photon {
    public double intensity;
    public Vector direction;
    public Cell myCell;
    public static Vector startingPoint;

    private Photon(Vector direction, Cell myCell, double intensity) {
        this.intensity = intensity;
        this.direction = direction;
        this.myCell = myCell;
    }

    public Photon(Vector direction, Cell myCell, double baseIntensity, double angle){
        baseIntensity = min(1, baseIntensity);
        this.intensity = baseIntensity * KirchhoffAmendment(angle);
        this.myCell = myCell;
        this.direction = direction;
    }

    private static double KirchhoffAmendment(double angle){
        return 0.5*(1+Math.cos(Math.toRadians(angle)));
    }

    private static double nextIntensity(double baseIntensity, double alfa){
        return baseIntensity * KirchhoffAmendment(alfa)*0.75;
    }

    public void HuygensKirchhoff(ArrayList<Photon> photons, Map map){
        //TODO: każdy klocek dzieli się na 4

        if(myCell.getVisibility().intensity > intensity)
            return;
        myCell.setIntensity(intensity);
        if(intensity < 0.1 || myCell.getVisibility().isOpaque())
            return;

        for(double alfa=0; alfa<360; alfa += 45) {
            Vector v = direction.rotate(alfa);
            Vector v2 = myCell.getPosition().sum(v);
            Cell nextCell = map.getCellAt(v2.getX(), v2.getY());

            if (nextCell != null) {
                double nextIntensity = nextIntensity(intensity, alfa);
                if(nextIntensity > 0.1){
                    Photon next = new Photon(v, nextCell, nextIntensity);
                    photons.add(next);
                }
            }
        }
    }

    @Override
    public String toString() {
        return myCell.getPosition() + " -> " + myCell.getPosition().sum(direction) + " at " + intensity;
    }
}
