import java.util.HashMap;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Write a description of class BestModel here.
 * 
 * @author (Shaun (Siong) Leong) 
 * @version (05/05/14)
 */
public class BestModel
{
    //Markov models for text1 and text2
    MarkovModel model1, model2; 
    //k+1-gram model for test string
    NgramModel testNgram;
    //log likelihoods of test with model1 and model2
    HashMap<String,Double> loglike1, loglike2; 
    //order of the Markov models
    int order;

    /**
     * Generate k-th order Markov models for text1 and text2
     * and calculate loglikelihoods of test against each model.
     * These structures can then be queried to find best matches.
     * @return true if test is closest to text1 and false otherwise
     */
    public BestModel(int k,
    String text1,
    String text2,
    String test) 
    {
        model1 = new MarkovModel(k, text1);
        model2 = new MarkovModel(k, text2);
        testNgram = new NgramModel(k+1, test);
        loglike1 = new HashMap<String,Double>();
        //loglike1 = logLikelihood(1);
        for (String key :testNgram.getDictionary().keySet()){
            loglike1.put(key, Math.log(model1.laplace(key)));

        }
        loglike2 = new HashMap<String,Double>();
        //loglike2 = logLikelihood(2);
        for (String key :testNgram.getDictionary().keySet()){
            loglike2.put(key, Math.log(model2.laplace(key)));

        }

        order = k;
        //TODO add code here
    }

    /** 
     * Get average log likelihood for the current models.
     * @input whichModel 1=model1, 2=model2
     * 
     * @return HashMap<String,Double> log likelihood for each word of test
     */
    public HashMap<String,Double> logLikelihood(int whichModel) 
    {
        //uses the n grams test strings in testNgram with the text1 or text2 in the
        //MarkovModel objects (model1 or model2) to calc loglikelihood of the test strings in testNgram
        HashMap<String,Double> output = new HashMap<String,Double>();
        if(whichModel == 1){

            output = loglike1;
        }else if(whichModel == 2){

            output = loglike2;
        }
        //TODO add code here
        return output;
    }

    /**
     *Gets the total of all the loglikelihood values from the ngram words.
     *@input HashMap<String,Double> logs 
     *@return double total value
     */
    public double totalLogLikelihood(HashMap<String,Double> logs)
    {
        double total = 0.0;

        for (String key : logs.keySet()){
            int originalFreq = testNgram.getDictionary().get(key);


            if (originalFreq > 1){
                total = total + logs.get(key)*originalFreq;
            }else{
                total = total + logs.get(key);
            }
        }

        //TODO add code here
        return total;
    }

    /**
     * Given the HashMap logs, find the total value and divide it by the total number of Ngrams created
     * @input HashMap<String,Double> logs 
     * @return a double average 
     */
    public double averageLogLikelihood(HashMap<String,Double> logs)
    {

        //TODO add code here
        return (double) (this.totalLogLikelihood(logs))/ (double) testNgram.getNumWords() ;
    }

    /**
     * Given precalculated model1, model2 and loglike1 and loglike2 find best match to test
     * 
     * @return true if text1 is best match and false if text2 is best match
     */
    public boolean chooseBestModel() 
    {

        //TODO add code here
        return this.averageLogLikelihood((this.logLikelihood(1))) > this.averageLogLikelihood(this.logLikelihood(2));
    }

    /**
     * @return confidence measure in best match
     */
    public double confidence() 
    {

        //TODO add code here
        return Math.abs(this.averageLogLikelihood((this.logLikelihood(1)))-this.averageLogLikelihood((this.logLikelihood(2))));
    }

    /**
     * Find the top n words for which the difference in the 
     * log probabilities of two models are greatest.
     * @param topn int number of obs,difs to return
     * @return HashMap<String,Double> top k strings and 
     *    their loglikelihood differences between model 1 and model 2
     */
    public HashMap<String,Double> explainBestModel(int topn)
    {
        HashMap<String,Double> difference = new HashMap<String,Double>();
        HashMap<String,Double> top = new HashMap<String,Double>();

        for (String key : testNgram.getDictionary().keySet()){
            difference.put(key, (Math.abs(loglike1.get(key)-loglike2.get(key))));

        }

        ArrayList<Double> valueSet = new ArrayList<Double>();
        for (String key: difference.keySet()){
            valueSet.add(difference.get(key));
        }

        Collections.sort(valueSet);
        Collections.reverse(valueSet);

        if (difference.size() >= topn){

            for (int i = 0; i < topn; i++){

                for (String key: difference.keySet()){
                    if (difference.get(key) == valueSet.get(i)){
                        top.put(key, valueSet.get(i));
                        difference.remove(key);
                        break;

                    }
                }

            }
        }else{
            top = difference;
        }
        //TODO add code here
        return top;
    }

}
