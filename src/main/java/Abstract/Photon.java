package Abstract;

import Level.Map;
import Level.Cell;

import java.util.ArrayList;
import java.util.Random;

import static Engine.Constants.sight;
import static java.lang.Math.min;

/**
 * Created by MajronMan on 28.12.2016.
 */
public class Photon {
    public double intensity;
    public int distance;
    public int threshold;
    public Vector direction;
    public Cell myCell;
    public static Vector startingPoint;
    private static final double coefficient = 5;

    public Photon(Vector direction, Cell myCell) {
        intensity = 1.0;
        this.direction = direction;
        this.myCell = myCell;
    }

    public Photon(Vector direction, Cell myCell, double intensity) {
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

    public static double KirchhoffAmendment(double angle){
        return 0.5*(1+Math.cos(Math.toRadians(angle)));
    }

    private static double nextIntensity(double baseIntensity, double alfa){
//        double delta = target.distance(startingPoint);
//        if(delta < 0.01) return 0;
        double c = Math.abs(Math.cos(Math.toRadians(alfa)));
        if(c < 0.1 || c > 0.9) c = 1;

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
