import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * A Fragment object has on-screen coordinates and intended size and an image to be displayed.
 * 
 *  You may ADD functionality to this class, but ensure that you do not change the existing method signatures.
 *  
 * @author Rachel Cardell-Oliver
 * @version March 2014
 */
public class Fragment
{
    private String keywords; 
    private BufferedImage image;
    private int xpos, ypos; 
    private int width, height; 

    /**
     * Construct a Fragment object for a Collage.
     * By default, the size (width and height) of the Fragment is the same as the image 
     * but the desired drawing size can be changed later. 
     * @param keywords String of words to identify this fragment eg. "blue, nature".
     * The name need not be unique eg. could use color as info for tile pictures.
     * The keyword string may include commas or spaces for multiple keywords.
     * @param xpos int position co-ordinate
     * @param ypos int position co-ordinate
     * @param image BufferedImage picture for this fragment
     */
    public Fragment(String keywords, int xpos, int ypos, BufferedImage image)
    {
        this.keywords = keywords;
        this.xpos = xpos;
        this.ypos = ypos;
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
    }


    // The following are the getter methods for Fragment attributes
    public String getKeyWords() 
    { 
        return keywords; 
    }

    public int getX() 
    { 
        return xpos; 
    }

    public int getY() 
    { 
        return ypos; 
    }

    public BufferedImage getImage() 
    { 
        return image; 
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    /**
     * Change the absolute position of a Fragment.
     * @param x1 int position coordinates 
     * @param y1 int
     */
    public void setPosition(int x, int y)
    {
        xpos = x;
        ypos = y;
    }

    /**
     * Move the Fragment a specified x and y distance. 
     * @param xDelta The x-distance to move      
     * @param yDelta The y-distance to move 
     */
    public void moveFragment(int xDelta, int yDelta)
    {
        xpos = xpos + xDelta;
        ypos = ypos + yDelta;
    }

    /**
     * Change the desired size for drawing a Fragment image.
     * @param newWidth
     * @param newHeight
     * No error checking is done for these parameters 
     * so they can be larger than image
     */
    public void scaleFragment(int newWidth, int newHeight)
    {
        width = newWidth;
        height = newHeight;
    }

    /**
     * Test the equality of the contents of this object and another one.
     * @return true if the contents of the fragments have the same values
     */
    public boolean equals(Fragment f1) 
    {
        return
        ((keywords.equals(f1.keywords)) && 
            (image.equals(f1.image)) &&
            (xpos == f1.xpos) &&
            (ypos == f1.ypos) &&
            (width == f1.width) &&
            (height == f1.height));
    }

    /**
     * Get printable information about this object.
     * @return String of information about this Fragment.
     */
    public String toString() {
        String str1 = "Keywords = " + keywords;
        String str2 = "Position = " + xpos +"," + ypos;
        String str3 = "Dimensions = " + width + "," + height;
        String str4 = "Image width = " + image.getWidth() + " and image height = " +  image.getHeight();
        return str1+"\n"+str2+"\n"+str3+"\n"+str4+"\n";
    }

}
