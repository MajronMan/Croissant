package immaterial;

import java.util.Random;

import static java.lang.Integer.signum;
import static java.lang.Math.abs;

/**
 * Created by MajronMan on 23.11.2016.
 */
public class Vector {
    private int x;
    private int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector(double r, double alfa){
        x = (int) Math.round(r * Math.cos(alfa));
        y = (int) Math.round(r * Math.sin(alfa));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector vector = (Vector) o;

        return  x == vector.x &&
                y == vector.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public Vector sum(Vector other){
        return new Vector(x+other.x, y+other.y);
    }

    public Vector add(int dx, int dy){
        x += dx;
        y += dy;
        return this;
    }

    public double alfa(){
        return Math.atan2(y, x);
    }

    public double r() { return Math.sqrt(x*x + y*y);}

    public Vector snapToDir(){
        double nx = x/r(), ny = y/r();
        return new Vector((int)Math.round(nx), (int)Math.round(ny));
    }

    public double square(){
        return x*x + y*y;
    }

    public double distance(Vector v){return substract(v).r();}

    public double distanceSq(Vector v){ return substract(v).square();}

    public Vector substract(Vector v){
        return new Vector(x - v.x, y-v.y);
    }

    public Vector getDelta(Vector v){
        return v.substract(this).snapToDir();
    }

    public Vector opposite(){
        return new Vector(-x, -y);
    }

    public Vector rotate(double degAngle){
        return new Vector(r(), alfa()+Math.toRadians(degAngle));
    }

    public static Vector randomDirection(){
        Random r = new Random();
        return new Vector(1.0, 45.0*r.nextInt(9));
    }
}
