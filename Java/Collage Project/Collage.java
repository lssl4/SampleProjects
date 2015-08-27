import java.util.ArrayList;
import java.awt.Color;


/**
 * A collage is a collection of Fragments.
 * Its methods create and modify a collage.
 * 
 * @author  (Shaun Leong (20753794))
 * @version (6 April 2014)
 */
public class Collage
{
    /**
     * A collage (picture) comprises a collection of Fragments (image components)
     */
    private ArrayList<Fragment> collage;

    /**
     * Construct a new Collage object.
     * This method constructs an Arraylist of Fragment class for the varialbe collage
     */
    
    public Collage()
    {       
        collage = new ArrayList<Fragment>();
    }

    /**
     * Add a new Fragment (line) to the collage.
     * @param f Fragment to add
     * this method adds a fragment of class Fragment to the collage arraylist
     */
    
    public void addFragment(Fragment f)
    {        
        collage.add(f);
    }

    /**
     * Get the Fragment at position i.
     * @param  i  int the position of the Fragment object
     * @return Fragment object at position i
     */
    
    public Fragment getFragment(int i)
    { 
        return collage.get(i); 
    }

    /**
     * Get the size of the collage.
     * @return  int the number of Fragments in the Collage
     */
    public int numberOfFragments() 
    {   
        return collage.size(); 
    }

    /**
     * Move every fragment in the collage by a given amount.
     * @param xDelta int pixels of horizontal move
     * @param yDelta int pixels of vertical move
     */
    public void move(int xDelta, int yDelta)
    {
        for(Fragment f: collage){
            f.moveFragment(xDelta, yDelta);
        }
    }

    /**
     * Set every fragment in the collage to have size tileWidth by tileHeight.
     * @param tileWidth new int x-size for the Fragment
     * @param tileHeight new int y-size for the Fragment
     */
    public void makeTiles(int tileWidth, int tileHeight)
    {
        for(Fragment f: collage){
            f.scaleFragment(tileWidth, tileHeight);
        }
    }

    /**
     * Scale the collage (relative to 0,0) by moving each image to a new position and scaling the image size. 
     * @param scale double where less than one means shrink and more than one means expand the collage
     */
    public void explode(double scale)
    {
        for(Fragment f: collage){
            f.setPosition((int) Math.round(scale*f.getX()), (int) Math.round(scale*f.getY()));
            f.scaleFragment((int) Math.round(scale*f.getWidth()), (int) Math.round(scale*f.getHeight()));
        }
    }

    /**
     * Find smallest x position in the collage, assuming that the Collage has at least one Fragment.
     * @return int minimum x-value of any Fragment
     */
    public int minX() 
    {
        int smallestThingSoFar = collage.get(0).getX();
        int minimumX= 0;
        for(Fragment f: collage){
            if(f.getX()< smallestThingSoFar ){
                smallestThingSoFar = f.getX();
                minimumX = smallestThingSoFar;          
            }
        }
        return minimumX;
    }


    /**
     * Find smallest y position in the collage, assuming that the Collage has at least one Fragment.
     * @return int minimum y-value of any Fragment
     */
    public int minY() 
    {
        
        int minimumY= collage.get(0).getY();
        for(Fragment f: collage){
            if(f.getY()< minimumY ){
               
                minimumY = f.getY();         
            }
        }
        
        return minimumY;
    }

    /**
     * Find largest x position in the collage, assuming that the Collage has at least one Fragment.
     * @return int maximum x-value of any Fragment
     */
    public int maxX() 
    { 
        int highestThingSoFar = collage.get(0).getX();
        int maximumX = 0;
        for(Fragment f: collage){
            if(f.getX() > highestThingSoFar ){
                highestThingSoFar= f.getX();               
                maximumX = highestThingSoFar + f.getWidth();           
            }else if(f.getX() <= highestThingSoFar ){
                maximumX = highestThingSoFar + f.getWidth();          
            }
        } 
        return maximumX;                  
    }
        

    /**
     * Find largest y position in the collage, assuming that the Collage has at least one Fragment.
     * @return int maximum y-value of any Fragment
     */
    public int maxY() 
    {
        int highestThingSoFar = collage.get(0).getY();
        int maximumY = 0;
        for (Fragment f: collage){
            if(f.getY() > highestThingSoFar){
                highestThingSoFar = f.getY();
                maximumY = highestThingSoFar + f.getHeight();
            }
        }
        return maximumY; 
    }

    /**
     * Choose any one of the fragments in the collage at random and remove it from the collage.
     */
    public void removeRandom()
    {
        int indexNumber = (int) (Math.random() * (collage.size()-1));
        collage.remove(indexNumber);
    }

    /**
     * Count number of images whose keywords include the substring key.
     * @param key String, the substring to search for
     * @return count int of number of qualifying images
     */
    public int countKeys(String key) 
    {
        int numOfImages = 0;
        for(Fragment f: collage){
            String name = f.getKeyWords();
            if (name.contains(key)){
                numOfImages ++;
            }
        }
        return numOfImages; 
    }

    /**
     * Find a Fragment whose name includes key. 
     * This method could be used e.g. to select colors for making tile pictures.
     * Optional extension: return any one of the matching fragments chosen at random, not just the first one.
     * @param key String to search for 
     * @return Fragment the first that matches this key
     */

    public Fragment findFragment(String key)
    {
        for (Fragment f: collage){
            String name = f.getKeyWords();
            if(name.contains(key)){
                return f;
            }
        }
        return null; 
    }

    
    /** 
     * Make a newline separated string of all keywords in the collage excluding any duplicates.
     * Optional extension: use substrings of keywords when searching for duplicates (and write JUnit test cases for that)
     * @return String of Fragment keywords
     */
    public String getAllKeywords()
    {
        String allKeywords = "";
        ArrayList<String> arrayKeywords = new ArrayList<String>();
        for (Fragment f: collage){
            String fragKeyword = f.getKeyWords() + "\n";           
            if(!arrayKeywords.contains(fragKeyword)){
                arrayKeywords.add(fragKeyword);        
            }  
        }
        for(String s: arrayKeywords){
            allKeywords = allKeywords + s;
           }       
        return allKeywords; 
    }

    /**
     * Generate a human readable summary of the features of the collage.
     * Format the string as you wish with your own choice of information.  
     * e.g. you can include information such as the number of Fragments, 
     * bounding box co-ordinates, and keywords used.
     * @return String summarising the features of the collage.
     */
    public String toString() 
    {
        String fragSummary = ""; 
            for(Fragment f : collage){
            fragSummary = f.toString();
        }
        String collSummary1 = "Number of Fragments: "+ this.numberOfFragments();
        String collSummary2 = "\nMax X position: " + this.maxX() + "\nMax Y Position: " + this.maxY(); 
        String collSummary3 = "\nMin X Position: " + this.minX() + "\nMin Y Position: " + this.minY();
        String collSummary4 = "\nCollage Dimensions: ";   
        String collSummary5 = "\n    Height: " + (this.maxY()-this.minY());
        String collSummary6 = "\n    Width: " + (this.maxX()-this.minX()) + "\n";
            
        return fragSummary + collSummary1 + collSummary2 + collSummary3 + collSummary4 + collSummary5+collSummary6;
    }
    
}

