import java.util.ArrayList;
import java.awt.image.BufferedImage;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class DrawingTest.
 * 
 * You may ADD functionality to this class, but ensure that you do not change the existing method signatures.
 *
 * @author  Rachel Cardell-Oliver
 * @version March 2014
 */
public class CollageTest
{

    //sample collages for testing
    Collage c, e;
    //images used for the tests
    BufferedImage leaves = ImageManager.readImageFile("images/leaves.jpeg");
    BufferedImage stones = ImageManager.readImageFile("images/stones.jpeg"); 

    /**
     * Sets up the test fixture, creating some collages, including an empty one.
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        e = new Collage(); // e is an empty collage
        c = new Collage(); // c is a collage with 5 Fragments
        c.addFragment(new Fragment("brown", 200,0, stones));
        c.addFragment(new Fragment("yellow", 0,0, leaves)); 
        c.addFragment(new Fragment("yellow", 200,200, leaves)); 
        c.addFragment(new Fragment("brown", 0, 200,stones)); 
    }

    @Test
    public void testEmptyCollage() { 
        //A newly created drawing object has size 0.
        assertEquals(0,e.numberOfFragments());
    }

    @Test
    public void testAddRemoveFragment()
    {
        assertEquals(4,c.numberOfFragments()); //initial size
        c.addFragment(new Fragment("extra1, brown", 100, 100, stones));
        assertEquals(5,c.numberOfFragments());
        c.addFragment(new Fragment("extra 1, brown", 100, 100, stones));
        assertEquals(6,c.numberOfFragments());
        c.removeRandom();
        assertEquals(5,c.numberOfFragments());
        c.removeRandom();
        assertEquals(4,c.numberOfFragments());
        c.removeRandom();
        assertEquals(3,c.numberOfFragments());
        //This test does NOT check that removeRandom is random.  See SampleDemo for that.
    }

    @Test
    public void testGetFragment()
    {
        Fragment f = c.getFragment(2); 
        //this is 3rd Fragment("yellow", 200,200, leaves)
        assertEquals(200,f.getX());
        assertEquals(200,f.getY());
        assertEquals("yellow", f.getKeyWords()); 
        assertEquals(177, f.getHeight()); //size of leaves
        assertEquals(284, f.getWidth());
        
    }

    @Test
    public void testNumberOfFragmentsNonEmpty() 
    {   
        assertEquals(4,c.numberOfFragments());
    }

    @Test
    public void testNumberOfFragmentsEmpty() 
    {   
        assertEquals(0,e.numberOfFragments());
    }

    /** 
     * Test that a sample point is moved to and fro correctly
     * The move method should also be tested visually
     * More tests could be added to check other points
     */
    @Test
    public void testMoveSimple()
    {

        assertEquals(200,c.getFragment(0).getX());
        assertEquals(0,c.getFragment(0).getY());        
        assertEquals(200,c.getFragment(2).getX());
        assertEquals(200,c.getFragment(2).getY());

        c.move(20,30);
        assertEquals(220,c.getFragment(0).getX());
        assertEquals(30,c.getFragment(0).getY());
        assertEquals(220,c.getFragment(2).getX());
        assertEquals(230,c.getFragment(2).getY());  

        c.move(30,-50);
        assertEquals(250,c.getFragment(0).getX());
        assertEquals(-20,c.getFragment(0).getY()); //maybe off screen but OK in model
        assertEquals(250,c.getFragment(2).getX());
        assertEquals(180,c.getFragment(2).getY());
    }

    @Test
    public void testMinX() 
    {
        c.move(10,10);
        assertEquals(10,c.minX()); 
    }

    @Test
    public void testMaxX() 
    {
        c.move(100,10);
        assertEquals(600,c.maxX()); 
    }

    @Test
    public void testMinY() 
    {
        c.move(0,10);
        assertEquals(10,c.minY());
    }

    @Test
    public void testMaxY() 
    {
        c.move(10,-10);
        assertEquals(367,c.maxY());
    }

    @Test
    public void testMakeTiles() 
    { 
        Fragment f;
        c.makeTiles(50,100);
        int csize = c.numberOfFragments();
        for (int i=0; i<csize; i++) {
            f = c.getFragment(i);
            assertEquals(50,f.getWidth());
            assertEquals(100,f.getHeight());
        }
    }

    @Test
    public void testGetAllKeywords()
    {
        String kw = c.getAllKeywords();
        assertEquals("brown\nyellow\n",kw);
    }

    @Test
    public void testGetAllKeywordsEmpty()
    {
        assertEquals("",e.getAllKeywords());
    }

    @Test
    public void testFindFragmentPresent()
    {
        Fragment f = c.findFragment("yellow");
        Fragment fe = c.getFragment(1);
        assertSame(f,fe);
        //Asserts that two identifiers refer to the same object
    }

    @Test
    public void testFindFragmentAbsent()
    {
        assertEquals(null,c.findFragment("blue"));
        assertEquals(null,e.findFragment("yellow"));
    }

    @Test
    public void testExplode()
    
    {
        Fragment f;
        int sh, sw, lh, lw;
        sh = stones.getHeight();
        sw = stones.getWidth();
        lh = leaves.getHeight();
        lw = leaves.getWidth();
        int[] xs = {200,0,200,0}; //original x coordinates in c
        int[] ys = {0,0,200,200}; //original y coordinates in c
        int[] ws = { sw, lw, lw, sw}; //original widths in c
        int[] hs = { sh, lh, lh, sh }; //original heights in c
        
        c.explode(2.0); //double
        int csize = c.numberOfFragments();
        for (int i=0; i<csize; i++) {
            f = c.getFragment(i);
            assertEquals(xs[i]*2, f.getX());
            assertEquals(ys[i]*2, f.getY());
            assertEquals(ws[i]*2, f.getWidth());
            assertEquals(hs[i]*2, f.getHeight());
        }
        c.explode(0.5); //undo double, back to original size
        for (int i=0; i<csize; i++) {
            f = c.getFragment(i);
            assertEquals(xs[i], f.getX());
            assertEquals(ys[i], f.getY());
            assertEquals(ws[i], f.getWidth());
            assertEquals(hs[i], f.getHeight());
        }
        
        
        
    }
        
        
        
}


