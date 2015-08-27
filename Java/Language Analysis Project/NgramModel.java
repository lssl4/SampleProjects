import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

/**
 * Ngram generator
 * 
 * @author (Shaun (Siong) Leong) 
 * @version (May 20 2014)
 */
public class NgramModel
{
    //dictionary of all ngrams and their frequencies
    private HashMap<String,Integer> ngram; 
    //number of distinct characters in the input
    private int alphabetSize;
    //total number of ngrams in the input
    private int numWords;
    
  
    /** 
     * Create an n-gram frequency model for an input string
     * ngrams at the end of the string wrap to the front
     * e.g. "abbbbc" includes "bca" and "cab" in its 3-grams
     * @param int n size of n-grams to create
     * @param String inp input string to be modelled
     */
    public NgramModel(int n, String inp) 
    {

        ngram = new HashMap<String, Integer>();
        HashSet<String> alphaset = new HashSet<String>();
        ArrayList<String> wordList = new ArrayList<String>();

        String[] array = inp.split("");
        for(String ch : array){
            if(!ch.equals("")){
                alphaset.add(ch);

            }
        }  
        alphabetSize = alphaset.size();


       
        String concatInput = inp + inp;
        if(concatInput.length() >= n){
            for (int i = 0; i < inp.length() ; i++){
                String substring = concatInput.substring(i, i+n);
                int freq = 1;
                wordList.add(substring);

                if(!ngram.containsKey(substring)){
                    ngram.put(substring, freq);
                }else{
                    ngram.put(substring, ngram.get(substring) + 1);

                }

            }
        }

        numWords = wordList.size();
        
      

        //TODO add code here
    }

    /** 
     * default constructor generates model for ngrams of size 1
     */
    public NgramModel(String inp) 
    {
        this(1,inp);
    }

    /**
     * @return HashMap<String,Integer> the ngram dictionary with word frequencies
     */
    public HashMap<String,Integer> getDictionary() {
        return ngram;
    }

    /**
     * @return int the size of the alphabet of a given input
     */
    public int getAlphabetSize() {
        return alphabetSize;
    }

    /**
     * @return int the total number of ngram words counted in the model
     */
    public int getNumWords() {
        return numWords;
    }

    /**
     * Make list of all words with frequency at least freq
     * @param freq int lower frequency limit for returned words
     * @return ArrayList all words with frequency more than freq
     */
    public ArrayList<String> getTopWords(int freq) {
        ArrayList<String> topWords = new ArrayList<String>();

        for (String key: ngram.keySet()){
            if (ngram.get(key) >= freq){
                topWords.add(key);

            }

        }
        //TODO add code here
        
        return topWords;
    }

    /**
     * @return String representation of the ngram model
     */
    public String toString()
    {
        String rep = "";

        for (String key : ngram.keySet()){
            String frequency = Integer.toString(ngram.get(key));

            rep = rep + key + "\t" + frequency + "\n";

        }
        rep = rep + "Alphabet size = " + alphabetSize + "\n" + "Number of Words = " + numWords + "\n";
        //TODO add code here
        return rep;
    }
    
    
    

}
