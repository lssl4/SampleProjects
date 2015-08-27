import java.awt.Color;
import java.util.Random;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

import java.util.ArrayList;


/**
 * SampleDemo extended by Shaun Leong
 * 
 * @author Shaun Leong 
 * @version April 2014
 */
public class MyDemo
{
    // instance variables
    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 800;

    //sample images from folder
    private static BufferedImage riverbank = ImageManager.readImageFile("images/riverbank.jpeg");
    private static BufferedImage flowers = ImageManager.readImageFile("images/flowers.jpeg");
    private static BufferedImage leaves = ImageManager.readImageFile("images/leaves.jpeg");
    private static BufferedImage stones = ImageManager.readImageFile("images/stones.jpeg");
    private static BufferedImage sky = ImageManager.readImageFile("images/sky.jpeg");
    private static BufferedImage apple = ImageManager.readImageFile("images/apple.png");
    private static BufferedImage earth = ImageManager.readImageFile("images/earth.jpeg"); 
    //earth image is from http://earthobservatory.nasa.gov/blogs/elegantfigures/2013/04/22/earth-day-and-night/
    private static BufferedImage globearth = ImageManager.readImageFile("images/earth.png"); 
    
    //methods extended and revised from SampleDemo
    /**
     * Helper method that draws up collage on canvas
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
     * Helper method do box limit around the collage
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
        int waittime = 250; //millisec delay to use between pictures in the animation
        
        int tilesize = 100; //for random tiling
        int n = 5; //number of tiles each way
        
        //makes a collage called Earth Collage
        Canvas mycanvas = new Canvas("Earth Collage",tilesize*n,tilesize*n); 
        Collage mycollage = new Collage();
        Fragment f, nf, ef, ff, gf;
        BufferedImage img;
        String name="";
        
        for (int i=0; i<(n-1); i++) {
            
            //flowers image
            nf = new Fragment("flowers"+i, 0 ,((int) (tilesize*n*0.2 + (i*tilesize))), flowers); 
            nf.scaleFragment(tilesize,tilesize);
            mycollage.addFragment(nf);
            mycanvas.wait(waittime);
            display(mycollage,mycanvas);
            
            

        }
        
        //prints the string statements of the mycollage objects
        System.out.println(mycollage.toString());
        
        for (int i=0; i<(n-1); i++) {
            
            //leaves image
            nf = new Fragment("leaves"+i, ((int) (tilesize*(i)+ tilesize*n*0.2)) ,((int) (tilesize*n*0.8)), leaves); 
            nf.scaleFragment(tilesize,tilesize);
            mycollage.addFragment(nf);
            mycanvas.wait(waittime);
            display(mycollage,mycanvas);
            
            

        }
        
        //prints the string statements of the mycollage objects
        System.out.println(mycollage.toString());
        
        for (int i=0; i<(n-1); i++) {
            
            //sky image
            nf = new Fragment("sky"+i, ((int) (tilesize*n*0.8)) , ((int)(tilesize*n*0.6+i*(-1*tilesize*n*0.2))), sky); 
            nf.scaleFragment(tilesize,tilesize);
            mycollage.addFragment(nf);
            mycanvas.wait(waittime);
            display(mycollage,mycanvas);
            
            

        }
       
        //prints the string statements of the mycollage objects
        System.out.println(mycollage.toString());
        
        for (int i=0; i<(n-1); i++) {
            
            //stones image
            nf = new Fragment("stones"+i, ((int) ((tilesize*n*0.6) + i*(-1*tilesize*n*0.2))), 0, stones); 
            nf.scaleFragment(tilesize,tilesize);
            mycollage.addFragment(nf);
            mycanvas.wait(waittime);
            display(mycollage,mycanvas);
            
            

        }
        
        //prints the string statements of the mycollage objects
        System.out.println(mycollage.toString());
        
        //adds earth image to collage
        ef = new Fragment("r-earth", ((int) (tilesize*n*0.2)), ((int)(tilesize*n*0.2)), earth);
        ef.scaleFragment(((int)(tilesize*n*0.6)),(((int)(tilesize*n*0.6))));
        mycollage.addFragment(ef);
        mycanvas.wait(waittime);
        display(mycollage,mycanvas);
        mycanvas.wait(waittime);
        
        //prints the string statements of the mycollage objects
        System.out.println(mycollage.toString());
        
        //shrink collage
        mycollage.explode(0.75);
        display(mycollage,mycanvas);
        mycanvas.wait(waittime);
        
        //move the collage
        mycollage.move(100,100);
        display(mycollage,mycanvas);
        mycanvas.wait(waittime);
        
        //write a summary of this collage to the terminal
        System.out.println(mycollage.getAllKeywords());
        mycanvas.wait(waittime);
        
        //remove 16 items
        for (int i=0; i<16; i++) {
            mycollage.removeRandom();
            display(mycollage,mycanvas);
            mycanvas.wait(waittime);
        }
       
        
        
        
        //Make a tiled collage on a new canvas
        mycanvas = new Canvas("Random Collage",150*5,100*6); 
        mycollage = new Collage(); //start again
        
        //add 25 random fragments in the collage. 
        for (int row=0; row<5; row=row+1) {
            for (int col=0; col<5; col=col+1) {
                int r = (int) Math.ceil(Math.random()*6); 
                switch (r) {
                    case 1 : img = riverbank; name = "earth, blue"; break;
                    case 2 : img = flowers; name = "earth, pink"; break;
                    case 3 : img = leaves; name = "earth, yellow"; break;
                    case 4 : img = stones;name = "earth, brown";  break;
                    case 5 : img = sky; name = "sky, blue"; break;
                    case 6 : img = apple; name = "apple, green"; break;
                    default : img = null; //error case
                }
                int x = col*150;
                int y = row*100;
                mycollage.addFragment(new Fragment(name, x,y,img));
            }
        }
        
        //makeTiles for collage
        mycollage.makeTiles(150,100);
        display(mycollage,mycanvas);
        mycanvas.wait(waittime);
        
        //explode random collage
        mycollage.explode(0.75);
        display(mycollage,mycanvas);
        mycanvas.wait(waittime);
        
        //prints string statements about new collage canvas
        System.out.println(mycollage.toString());
        
        //remove 25 items one at a time from the Random collage
        for (int i=0; i<25; i++) {
            mycollage.removeRandom();
            display(mycollage,mycanvas);
            mycanvas.wait(waittime);
        }
        
        
        
      
        
        
    
    }
    
    
}
