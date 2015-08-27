import java.awt.Color;
import java.util.Random;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

import java.util.ArrayList;


/**
 * Example of client-focussed examples to demonstrate the functionality of the Collage class.
 * Call this demo using the main method (there is no constructor).
 * 
 * Do not change this class.  Instead create a new class, MyDemo.java, for your own demonstration (see README).
 * 
 * @author  Rachel Cardell-Oliver
 * @version March 2014
 */
public class SampleDemo
{

    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 800;

    // some sample images
    private static BufferedImage riverbank = ImageManager.readImageFile("images/riverbank.jpeg");
    private static BufferedImage flowers = ImageManager.readImageFile("images/flowers.jpeg");
    private static BufferedImage leaves = ImageManager.readImageFile("images/leaves.jpeg");
    private static BufferedImage stones = ImageManager.readImageFile("images/stones.jpeg");
    private static BufferedImage sky = ImageManager.readImageFile("images/sky.jpeg");

    /**
     * Helper method to draw mycollage on my canvas, first erasing any previous image
     */
    private static void display(Collage mycollage, Canvas mycanvas) 
    {
        mycanvas.erase();
        int size = mycollage.numberOfFragments();
        for (int i = 0; i < size; i++) {
            mycanvas.drawFragment(mycollage.getFragment(i)); 
        }
    }

    /**
     * Helper method to draw a bounding box around the Collage
     * @param mycollage collection of Fragments to be drawn
     * @param mycanvas graphics canvas to draw on
     * @param lineWidth float valued stroke width for the line (see mycanvas)
     */
    private static void drawBorder(Collage mycollage, Canvas mycanvas, float lineWidth)
    { 
        int x1,y1, x2,y2; //bounding box coords
        x1 = mycollage.minX();
        y1 = mycollage.minY();
        x2 = mycollage.maxX();
        y2 = mycollage.maxY();
        mycanvas.setStrokeWidth(lineWidth);
        mycanvas.drawLine(x1,y1,x1,y2);
        mycanvas.drawLine(x2,y1,x2,y2);
        mycanvas.drawLine(x1,y1,x2,y1);
        mycanvas.drawLine(x1,y2,x2,y2);
    }

    /**
     * Run the demo by calling the main method (no arguments needed).
     */
    public static void main(String[] args) 
    {
        
        int waittime = 2000; //millisec delay to use between pictures in the animation
        
        int tilesize = 40; //for random tiling
        int n = 20; //number of tiles each way
        
        Canvas mycanvas = new Canvas("Random Collage",n*tilesize,n*tilesize); 
        Collage mycollage = new Collage();
        Fragment f, nf;
        BufferedImage img;
        String name="";
        
        for (int i=0; i<150; i++) {
            int x = (int) Math.ceil(Math.random()*(n-1)*tilesize);
            int y = (int) Math.ceil(Math.random()*(n-1)*tilesize);
            nf = new Fragment("r"+i, x,y, sky); 
            nf.scaleFragment(tilesize,tilesize);
            mycollage.addFragment(nf);

        }
        display(mycollage,mycanvas);
        mycanvas.wait(waittime);

        //write a summary of this collage to the terminal
        System.out.println(mycollage.toString());
        
        //shrink the collage
        mycollage.explode(0.5);
        display(mycollage,mycanvas);
        mycanvas.wait(waittime);
        
        //move the collage
        mycollage.move(150,150);
        display(mycollage,mycanvas);
        drawBorder(mycollage, mycanvas, 2.0f);
        mycanvas.wait(waittime);
        
        //remove 50 items
        for (int i=0; i<100; i++) {
            mycollage.removeRandom();
        }
        display(mycollage,mycanvas);
        mycanvas.wait(waittime);
        
        //move back and expand again
        mycollage.move(-150,-150);
        mycollage.explode(2.0);
        display(mycollage,mycanvas);
        mycanvas.wait(waittime);
        //write a summary of this collage to the terminal
        System.out.println(mycollage.toString());

        //Make a tiled collage on a new canvas
        mycanvas = new Canvas("Nature Collage",150*5,100*7); 
        mycollage = new Collage(); //start again
        //now make an 4x3 tiled collage
        for (int row=0; row<7; row=row+1) {
            for (int col=0; col<5; col=col+1) {
                int r = (int) Math.ceil(Math.random()*5); 
                switch (r) {
                    case 1 : img = riverbank; name = "earth, blue"; break;
                    case 2 : img = flowers; name = "earth, pink"; break;
                    case 3 : img = leaves; name = "earth, yellow"; break;
                    case 4 : img = stones;name = "earth, brown";  break;
                    case 5 : img = sky; name = "sky, blue"; break;
                    default : img = null; //error case
                }
                int x = col*150;
                int y = row*100;
                mycollage.addFragment(new Fragment(name, x,y,img));
            }
        }

        //make the fragments exactly fit the spaces
        mycollage.makeTiles(150,100);
        display(mycollage,mycanvas);
        mycanvas.wait(waittime); 
        
        //write a summary of the collage to the terminal
        System.out.println(mycollage.toString());
    }
}
