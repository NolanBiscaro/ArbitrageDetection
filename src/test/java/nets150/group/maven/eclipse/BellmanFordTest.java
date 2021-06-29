package nets150.group.maven.eclipse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class BellmanFordTest {

    private Graph g;

    @Before
    public void init() {
        g = new Graph(4);
        g.addEdge(0, 1, 2, true);
        g.addEdge(3, 0, 5, true);
        g.addEdge(1, 2, 3, true);
        g.addEdge(2, 3, -12, true);

    }

    @Test
    public void testGetNegativePath() {
        BellmanFord bf = new BellmanFord(g, 1.0);
        bf.run(0);
        Object[] expected = new Object[] { 1, 2, 3, 0, 1 };
        Object[] path = bf.getNegativeCycles().toArray(); 
        assertArrayEquals(expected, path);
    }

    @Test
    public void testGetNegativePathMultipleCycles() {
        g.addEdge(0, 2, -6, true);
        BellmanFord bf = new BellmanFord(g, 1.0);
        bf.run(0);
        Object[] expected = new Object[] { 0, 2, 3, 0 };
        Object[] path = bf.getNegativeCycles().toArray();
        assertArrayEquals(expected, path);
    }
    
    //Note: Threshold is set to 1 therefore if diff <= 1 it will not find path. 
    @Test
    public void testGetNegativePathMultipleCycles2() {
        g.addEdge(0, 2, 5, true);
        BellmanFord bf = new BellmanFord(g, 1.0);
        bf.run(0);
        Object[] expected = new Object[] { 0, 2, 3, 0 };
        Object[] path = bf.getNegativeCycles().toArray();
        assertArrayEquals(expected, path);
    }

    @Test
    public void testGetNegativePathExample() {
        Graph g = new Graph(4);
        g.addEdge(0, 1, 0.124939, true);
        g.addEdge(1, 2, -2.17782, false);
        g.addEdge(2, 3, -1.07918, false);
        g.addEdge(3, 0, -3.04576, false);
        BellmanFord bf = new BellmanFord(g, 1.0);
        bf.run(0);
        Object[] expected = new Object[] { 1, 2, 3, 0, 1 };
        Object[] path = bf.getNegativeCycles().toArray();
        assertArrayEquals(expected, path);

    }

    @Test
    public void testGetNegativePathNoCycle() {
        Graph g = new Graph(4);
        g.addEdge(1, 2, 5, true);
        g.addEdge(2, 3, 5, true);
        g.addEdge(0, 1, 5, true);
        BellmanFord bf = new BellmanFord(g, 1.0);
        bf.run(0);
        assertEquals(0, bf.getNegativeCycles().size());
    }

}
