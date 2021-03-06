/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patternRecognition.supervised.distance;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import patternRecognition.supervised.SupervisedClassifier;

/**
 *
 * @author AndrésEspinalJiménez
 */
public class KNearestNeighbor extends SupervisedClassifier{
    private Integer K;
    private final Metric metric;
    private int nFeatures;    
    private LinkedList<DistanceComparator> distances;    
    
    public KNearestNeighbor(HashMap<Integer, LinkedList<float[]>> training, Integer K, Metric metric, int nFeatures) {
        
        super(training);
        this.K = K;
        this.metric = metric;
        this.nFeatures = nFeatures;        
        this.distances = new LinkedList<>();        
        train();        
        
    }

    @Override
    protected void train() {}

    @Override
    public int classify(float[] x) {
        int[] votes = new int[this.training.size()];
        Integer maxVotes = -1;
        Integer maxIndex = -1;
                
        this.distances.clear();
        for(Integer key : this.training.keySet()){
            LinkedList<float[]> patterns = this.training.get(key);
            
            for(float[] pattern : patterns)
                this.distances.add(new DistanceComparator(key, this.metric.calculate(x, pattern,0)));
        }
        Collections.sort(this.distances);
        for(Integer i = 0; i < this.K; i++)
            votes[this.distances.get(i).getKeyClass() - 1] ++;
        for(Integer i = 0; i < votes.length; i++)
            if(votes[i] > maxVotes){
                maxVotes = votes[i];
                maxIndex = i;
            }
        
        return maxIndex + 1;
    }
    public int[][] desempenototal(){
        HashMap<Integer, int[]> exp = new HashMap<>();
        for(int i =0;i< training.size();i++){
            int[] c = new int[training.get(i+1).size()];
        for(int j =0;j< training.get(i+1).size();j++){
            c[j] = classify(training.get(i+1).get(i));
        }
        exp.put(i, c);
        }
        return generarmatriz(exp);
    }
    public static int[][] generarmatriz(HashMap<Integer, int[]> exp){
        int[][] matrizc = llenar(exp.size());
        for(int i =0;i< matrizc.length;i++){
            for(int j =0;j< exp.get(i).length;j++){
                matrizc[i][exp.get(i)[j]-1]++;
            }
        }
        return matrizc;
    }
    public static int[][] llenar(int entr){
        int[][] c = new int[entr][entr];
        for(int i =0;i<c.length;i++){
            for(int j =0;j<c[i].length;j++){
                c[i][j] =0;
        }
        }
        return c;
    }
}
