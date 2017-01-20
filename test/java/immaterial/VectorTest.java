package immaterial;

import static java.lang.Math.*;
import static org.junit.Assert.*;

import org.junit.Test;
/**
 * Created by MajronMan on 18.12.2016.
 */

public class VectorTest {
    private boolean compareAngle(double a1, double a2){
        double o1 = abs(a1-a2), o2 = abs(a1-a2)-2*PI;
        return o1 < 1e-6 || o2 < 1e-6;
    }

    @Test
    public void alfa() throws Exception {
        int dirs9[][] = {
                {1, 1}, {0, 1}, {1, 0}, {0, -1},
                {-1, -1}, {-1, 0}, {1, -1}, {-1, 1}};
        double expected[] = {
                toRadians(45), toRadians(90), toRadians(0), toRadians(-90),
                toRadians(-135), toRadians(180), toRadians(-45), toRadians(135),};
        for (int i = 0; i < 8; i++) {
            Vector v = new Vector(dirs9[i][0], dirs9[i][1]);
            assertTrue(compareAngle(v.alfa(), expected[i]));
        }
    }

    @Test
    public void snapToDir() throws Exception {
        int expected[][] = {
                {1, 1}, {0, 1}, {1, 0}, {0, -1},
                {-1, -1}, {-1, 0}, {1, -1}, {-1, 1}};
        int tests[][]={
                {101,99}, {101,99}, {3, 1000}, {-3, 100}, {100, 3}, {100, -4}, {2, -501}, {-3, -499},
                {-1003, -999}, {-998, -1001}, {-100, 2}, {-100, -2}, {101, -101}, {99, -99}, {-33, 33}, {-32, 34}
        };

        for(int i=0; i<16; i++){
            Vector v1 = new Vector(tests[i][0], tests[i][1]).snapToDir();
            Vector v2 = new Vector(expected[i/2][0], expected[i/2][1]);
            assertTrue(v1.equals(v2));
        }

    }

    @Test
    public void getDelta() throws Exception {
        int expected[][] = {
                {1, 1}, {0, 1}, {1, 0}, {0, -1},
                {-1, -1}, {-1, 0}, {1, -1}, {-1, 1}};
        int input[][] = {
                {34, 34}, {33, 34}, {34, 33}, {33, 32},
                {32, 32}, {32, 33}, {34, 32}, {32, 34}
        };
        Vector tester = new Vector(33, 33);

        for (int i = 0; i < 8; i++) {
            Vector in = new Vector(input[i][0], input[i][1]);
            Vector expect = new Vector(expected[i][0], expected[i][1]);
            assertTrue(tester.getDelta(in).equals(expect));
        }
    }

    @Test
    public void rotate() throws Exception{
        int expected[][] = {
                {1, 1}, {1, 0}, {1, -1}, {0, -1},
                {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}};

        for (int i = 1; i < 9; i++) {
            Vector in = new Vector(expected[i%8][0], expected[i%8][1]);
            assertEquals(new Vector(expected[i-1][0], expected[i-1][1]), in.rotate(45));
        }
    }
}