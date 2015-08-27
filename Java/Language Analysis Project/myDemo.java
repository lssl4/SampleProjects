import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * This class demonstrates a use case for Shaun recognition.
 * This class is a modified version of sampleDemo. 
 * 
 * @author Siong (Shaun) Leong
 * @version May 20 2014
 */
public class myDemo
{
    String testDir = "shorttexts/"; //directory of sample texts
    /*
     * Source: http://www.gutenberg.org/wiki/Category:Science_Fiction
     * La Vie Électrique (french1.txt)
     * Les voyages de Gulliver, dessins de Paul Gavarni (french2.txt)
     */ 

    String f1 = "english1.txt";
    String f2 = "english2.txt";
    String f3 = "french1.txt";
    String f4 = "french2.txt";
    String f5 = "german1.txt";
    String f6 = "german2.txt";

    //strings of text with spaces taken out
    //String sp1, sp2, sp3, sp4, sp5, sp6;

    String s1 = TextFileManager.cleanString(TextFileManager.readFile(testDir+f1));
    String s2 = TextFileManager.cleanString(TextFileManager.readFile(testDir+f2));
    String s3 = TextFileManager.cleanString(TextFileManager.readFile(testDir+f3));
    String s4 = TextFileManager.cleanString(TextFileManager.readFile(testDir+f4));
    String s5 = TextFileManager.cleanString(TextFileManager.readFile(testDir+f5));
    String s6 = TextFileManager.cleanString(TextFileManager.readFile(testDir+f6));

    
    /**
     * Show how well a text conforms to expected English frequencies
     * using a bi-gram model
     */
    public void BiGramDemo() 
    {
        String[] s1array = s1.split(" ");
        String sp1 = "";
        for (String s: s1array){
            sp1 = sp1 + s;
        }

        NgramModel bigram = new NgramModel(2, sp1);
        //find words with frequency greater than 10%
        int pct = (bigram.getNumWords()*2/100);
        ArrayList<String> list = bigram.getTopWords(pct);

        System.out.println();
        System.out.println("=============================================");
        System.out.println("Bi-Gram of Letters of English text with freq >= 2% in "+f1+ ":");
        System.out.println(list);
        System.out.println("vs expected TH HE AN RE ER IN ON for English");
        System.out.println("Average number of times that each n-gram appeared: " + bigram.getNumWords()/bigram.getDictionary().keySet().size());
        System.out.println("Alphabet Size: " + bigram.getAlphabetSize());
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
        MarkovModel model = new MarkovModel(k,s3);

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
        System.out.println("Alphabet Size: " + model.getAlphabetSize());
        System.out.println("Markove Model Order (also Ngram Model order): " + model.getK());
        //System.out.println( model.toString()); //fix this and just do challenge task

        System.out.println("=============================================");

    }
    
    /**
     * Language demo among German, French and English texts.
     */
    public void languageDemo(){
 

        int k=2; //order of the markov model

       

        experimentResults(k, f4, f5, f6, s4, s5, s6);
        experimentResults(k, f3, f5, f4, s3, s5, s4);

    }

    /**
     * Prints out the character frequencies of each language.
     * @param n accepts an int, desired number of n-grams
     * @param lang accepts an int, 1:English, 2:French, 3: German
     */
    public void allNgrams(int n, int lang){


        //splited English text
        String[] s1array = s1.split(" ");
        String sp1 = "";
        for (String s: s1array){
            sp1 = sp1 + s;
        }

        //splitted French Text
        String[] s3array = s3.split(" ");
        String sp3 = "";
        for (String s: s3array){
            sp3 = sp3 + s;
        }

        //splitted German Text
        String[] s5array = s5.split(" ");
        String sp5 = "";
        for (String s: s5array){
            sp5 = sp5 + s;
        }

        if(lang == 1){
            //English

            NgramModel ngram = new NgramModel(n, sp1);
       

            System.out.println("=============================================");
            System.out.println("English Ngram Key and Frequency");

            System.out.println(ngram.toString());

            System.out.println("=============================================");

        }else if(lang == 2){
            //French
            NgramModel ngram = new NgramModel(n, sp3);

            System.out.println("=============================================");
            System.out.println("French Ngram Key and Frequency");

            System.out.println(ngram.toString());
            System.out.println("=============================================");
        }else if (lang == 3){
            //German

            NgramModel ngram = new NgramModel(n, sp3);

            System.out.println("=============================================");
            System.out.println("German Ngram Key and Frequency");

            System.out.println(ngram.toString());
            System.out.println("=============================================");
        }else{
            System.out.println("Error. Please input version 1, 2, or 3");
        }

    }

    public void decipherTextLanguage(String s){
        NgramModel germanNgram = new NgramModel(3,s5);
        NgramModel englishNgram = new NgramModel(3,s1);
        NgramModel testNgram = new NgramModel(3,s1);

        MarkovModel englishModel = new MarkovModel(3,s1);
        MarkovModel germanModel = new MarkovModel(2,s5);

        BestModel englishlang = new BestModel(2, s1, s2, s);
        BestModel germanlang = new BestModel(2, s5, s6, s);
        BestModel frenchlang = new BestModel(2, s3, s4, s);

        

        System.out.println();
        System.out.println("=============================================");
        if ((englishlang.averageLogLikelihood(englishlang.logLikelihood(1)) > germanlang.averageLogLikelihood(germanlang.logLikelihood(1))) && (englishlang.averageLogLikelihood(englishlang.logLikelihood(1)) > frenchlang.averageLogLikelihood(frenchlang.logLikelihood(1))))
        
        {
            System.out.println("English is the most likely langauge of test string");

        }
        
        if(englishlang.averageLogLikelihood(englishlang.logLikelihood(1)) < germanlang.averageLogLikelihood(germanlang.logLikelihood(1))){
            System.out.println("German is the most likely langauge of test string");

        }
        
        if(englishlang.averageLogLikelihood(englishlang.logLikelihood(1)) < frenchlang.averageLogLikelihood(frenchlang.logLikelihood(1))){
            System.out.println("French is the most likely langauge of test string");
        }
        
        
        System.out.println();
        System.out.println("=============================================");

    }
    
    /**
     * Run all the demos
     */
    public void runAllDemos(int n, int lang, String s)
    {
        BiGramDemo();
        languageDemo(); 
        allNgrams(n, lang);
        decipherTextLanguage(s);

    }
    
    
    boolean palindrome(String str){
        int lastIndex = str.length()-1; int firstIndex = 0; boolean indicator = true; 
        while(firstIndex<lastIndex){
            if(str.charAt(firstIndex)!= str.charAt(lastIndex)){
                indicator = false;
            }
            firstIndex++;lastIndex--;
        }
            
            return indicator;
        
        }
        
        
    

}
