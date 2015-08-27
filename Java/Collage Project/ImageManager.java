import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;

/**
 * Utility class of operations for reading, writing and manipulating Java images.
 * This utility class has no constructor, so you call its methods using the class name 
 * e.g. ImageManager.readImageFile("images/myimage.jpg");
 * 
 * You may ADD functionality to this class, but ensure that you do not change the existing method signatures.
 * 
 * @author Rachel CO 
 * @version March 2014
 */
public class ImageManager
{


    /** 
     * Read an image from a file 
     * see http://docs.oracle.com/javase/tutorial/2d/images/loadimage.html
     */
    public static BufferedImage readImageFile(String imageFilename) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imageFilename));
        } catch (IOException e) {
            System.out.println("There was a problem opening "+imageFilename);
            System.out.println("Please check the name and the directory and try again");
        }
        return img;
    } 

}
