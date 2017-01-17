package Level;

import Abstract.Vector;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Created by MajronMan on 15.01.2017.
 */
public class MapTest {
    @Test
    public void getPath() throws Exception {
        boolean[][][] cells = new boolean[4][4][4];
        cells[0] = new boolean[][]{
                {true, true, true, true},
                {true, true, true, true},
                {true, true, true, true},
                {true, true, true, true},
        };
        cells[1] = new boolean[][]{
                {false, false, false, false},
                {false, false, false, false},
                {false, false, false, false},
                {false, false, false, false},
        };
        cells[2] = new boolean[][]{
                {true, true, false, false},
                {false, true, false, false},
                {false, true, false, false},
                {false, true, false, true},
        };
        cells[3] = new boolean[][]{
                {true, true, false, false},
                {false, true, false, false},
                {false, true, false, false},
                {false, true, true, true},
        };
        Map[] maps = new Map[4];
        ArrayList<ArrayList<Cell>> tester = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            maps[i] = new Map(cells[i], 4, 4);
            tester.add(maps[i].getPath(new Vector(0, 0), new Vector(3,3)));
        }
        assertTrue(tester.get(0).size() == 2);
        assertTrue(tester.get(1) == null);
        assertTrue(tester.get(2) == null);
        assertTrue(tester.get(3).size() == 3);
    }

}