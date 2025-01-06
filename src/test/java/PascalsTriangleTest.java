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
    public void testFirstRowReturns() {
        assertEquals(asList(1), pascalsTriangle.triangleRow(0));
    }

    @Test
    public void testFifthRowReturns() {
        assertEquals(asList(1, 4, 6, 4, 1), pascalsTriangle.triangleRow(4));
    }

}
