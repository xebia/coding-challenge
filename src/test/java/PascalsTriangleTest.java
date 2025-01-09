import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class PascalsTriangleTest {

    private PascalsTriangle pascalsTriangle;

    @Before
    public void setUp() {
        pascalsTriangle = new PascalsTriangle();
    }

    @Test
    public void testRow0Returns() {
        assertEquals(asList(1), pascalsTriangle.triangleRow(0));
    }

    @Test
    public void testRow1Returns() {
        assertEquals(asList(1, 1), pascalsTriangle.triangleRow(1));
    }

    @Test
    public void testRow2Returns() {
        assertEquals(asList(1, 2, 1), pascalsTriangle.triangleRow(2));
    }

    @Test
    public void testRow3Returns() {
        assertEquals(asList(1, 3, 3, 1), pascalsTriangle.triangleRow(3));
    }

    @Test
    public void testRow4Returns() {
        assertEquals(asList(1, 4, 6, 4, 1), pascalsTriangle.triangleRow(4));
    }

}
