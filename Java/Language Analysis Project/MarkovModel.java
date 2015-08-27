import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

/**
 * Construct a Markov model and make predictions.
 * 
 * @author (your name) 
 * @version (May 20 2014)
 */
public class MarkovModel
{

    int k; //markov model order parameter
    NgramModel ngram; //ngram model order k
    NgramModel n1gram; //ngram model order k+1
    int alphabetSize; //number of words in teh model
    double lapProb; // probability of obs based on markov model

    /**
     * Construct an order-n Markov model from string s
     * @param n int order of the Markov model
     * @param s String input to be modelled
     */
    public MarkovModel(int n, String s) 
    {
        ngram = new NgramModel(n, s);
        n1gram = new NgramModel(n+1, s);

        k = n;
        alphabetSize = ngram.getAlphabetSize();

        //TODO add code here
    }

    /**
     * @return order of this Markov model
     */
    public int getK()
    {
        NgramModel hello = new NgramModel("hello");

        //hello.haha;
        return k;
    }

    /**
     * @return number of characters in the alphbet
     */
    public int getAlphabetSize()
    {
        return alphabetSize;        
    }

    /**
     * Calculate the Laplacian probability of string obs given this Markov model
     * @input obs String of length k+1
     * @return double Laplacian probability 
     */
    public double laplace(String obs) 
    { 
        String formerCharacters = "";

        if (obs != "" && obs != null){
            formerCharacters = obs.substring(0, obs.length()-1);
        }

        int pc;
        int p;

        if(n1gram.getDictionary().containsKey(obs)){
            pc = n1gram.getDictionary().get(obs);

        }else{
            pc = 0;
        }

        if (ngram.getDictionary().containsKey(formerCharacters)){
            p = ngram.getDictionary().get(formerCharacters);
        }else{
            p = 0;
        }

        lapProb = (double) (pc+1)/(p + alphabetSize);
        return lapProb;
        //TODO add code here
    }

    /**
     * @return String representing this Markov model
     */
    public String toString()
    {
        String rep = "Markov Model Order Number: " + k + "\n" + ngram.toString() + "k+1 Ngram" + "\n" + n1gram.toString();

        ArrayList<Integer> intlist = new ArrayList<>();
        for (Integer value: n1gram.getDictionary().values()){
            intlist.add(value);
        }

        Collections.sort(intlist);
        Collections.reverse(intlist);

        rep = rep + "Top five frequent n-grams Laplace probability: \n";

        for (int i = 0; i < 5; i++){
            for (String s: n1gram.getDictionary().keySet()){
                if(n1gram.getDictionary().get(s) == intlist.get(i)){
                    rep = rep + s + "\t" + String.valueOf(this.laplace(s)) + "\n";
                    n1gram.getDictionary().remove(s);
                    break;

                }
            }
        }


        //TODO add code here
        return rep;
    }

}
