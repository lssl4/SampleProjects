import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * This class demonstrates a use case for author recognition.
 * There is no need to add any code to this class, just use it to test your project.
 * You should also write your own MyDemo. 
 * 
 * @author Rachel Cardell-Oliver
 * @version April 2014
 */
public class SampleDemo
{

    String testDir = "shorttexts/"; //directory of sample texts
    
    /**
     * Show how well a text conforms to expected English frequencies
     * using a 1-gram model
     */
    public void OneGramDemo() 
    {
        String f1 = "english1.txt";
        String s1 = TextFileManager.cleanString(
                    TextFileManager.readFile(testDir+f1));
        NgramModel onegram = new NgramModel(s1);
        //find words with frequency greater than 10%
        int fivepct = (onegram.getNumWords()*5/100);
        ArrayList<String> list = onegram.getTopWords(fivepct);
        
        System.out.println();
        System.out.println("=============================================");
        System.out.println("Characters with freq >= 5% in "+f1+ ":");
        System.out.println(list);
        System.out.println("vs expected e t a o i n s h r for English");
        System.out.println("=============================================");
        //source http://en.wikipedia.org/wiki/Letter_frequency
    }

    /**
     * Helper method to print results of Markov experiment
     * @param k int order of the Markov model
     * @param f1, f2, f3 file names used for data
     * @param s1, s2, s3 cleaned strings used for training (s1,s2) and testing (s3)
     */
    private void experimentResults(int k, 
    String f1, String f2, String f3, 
    String s1, String s2, String s3)
    {
        BestModel language;

        System.out.println();
        System.out.println("=============================================");
        language = new BestModel(k, s1, s2, s3);
        if (language.chooseBestModel()) {
            System.out.println(f1 + " is the best model for " + f3);
        } else {
            System.out.println(f2 + " is the best model for " + f3);
        }
        System.out.printf("Confidence for this choice is %.4f\n", language.confidence());
        System.out.println("Top difference n-grams are:\n" + language.explainBestModel(5));
        System.out.println("=============================================");
    }

    public void languageDemo() 
    {
        String f1, f2, f3, f4, s1, s2, s3, s4; 

        int k=2; //order of the markov model
         
        f1 = "english1.txt";
        f2 = "german1.txt";
        f3 = "english2.txt";
        f4 = "german2.txt";

        s1 = TextFileManager.cleanString(TextFileManager.readFile(testDir+f1));
        s2 = TextFileManager.cleanString(TextFileManager.readFile(testDir+f2));
        s3 = TextFileManager.cleanString(TextFileManager.readFile(testDir+f3));
        s4 = TextFileManager.cleanString(TextFileManager.readFile(testDir+f4));

        experimentResults(k, f1, f2, f3, s1, s2, s3);
        experimentResults(k, f1, f2, f4, s1, s2, s4);
    }

    
    
    public void classicsDemo() 
    {
        String testDir = "shorttexts/";
        String s1, s2, s3, s4;

        int k=5; //order of the markov model
        
        s1 = TextFileManager.cleanString(TextFileManager.readFile(testDir+"austen1.txt"));
        s2 = TextFileManager.cleanString(TextFileManager.readFile(testDir+"carroll1.txt"));
        s3 = TextFileManager.cleanString(TextFileManager.readFile(testDir+"austen2.txt"));
        s4 = TextFileManager.cleanString(TextFileManager.readFile(testDir+"carroll2.txt"));

        experimentResults(k, "Austen", "Carroll", "austen2", s1, s2, s3);
        experimentResults(k, "Austen", "Carroll", "carroll2", s1, s2, s4);

    }
    
    public void electionDemo() 
    {
        String testDir = "shorttexts/";
        String s1, s2, s1a, s1b, s2a, s2b;

        int k=2; //order of the markov model
        
        s1 = TextFileManager.cleanString(TextFileManager.readFile(testDir+"1996-paul-keating.txt"));
        s2 = TextFileManager.cleanString(TextFileManager.readFile(testDir+"2007-kevin-rudd.txt"));
        
        s1a = s1.substring(0,5000);
        s1b = s1.substring(5000,10000);
        s2a = s2.substring(0,5000);
        s2b = s2.substring(5000,10000);

        experimentResults(k, "PK96", "KR07", "PK96b", s1a, s2a, s1b);
        experimentResults(k, "PK96", "KR07", "KR07b", s1a, s2a, s2b);

    }
    
    public void runAllDemos()
    {
        OneGramDemo();
        languageDemo(); 
        classicsDemo();
        electionDemo();
    }

}
