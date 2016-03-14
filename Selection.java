/*
  Selection contains all of the methods relating to selection
  and reproduction for use in a genetic algorithm.

  The only method you need to use is select()
  all of the other methods are containted within
 */

//package ga;
import java.util.Arrays;

public class Selection extends GAlg {
    public static Individual[] select(Population pop) {
 Individual[] selected=new Individual[popSize];
 int temp;
 
 //set pSelect vals for roulette selection
 createWheel(pop);
 
 //randomly select an Individual to be added to selected[]
 for(int i=0;i<popSize;i++) {
     temp=binSearch(rand.nextDouble(),pop);
     selected[i]=pop.getIndividual(temp);
 }
 
 //reproduction/new gen created
 xover(selected);
 mutate(selected);
 return selected;
    }
    
    //sets pSelect values to proportion and prepares array for selection
    public static void createWheel(Population pop) {
 //calculate total fitness
 pop.findTotalFit();
 double totalFit=pop.getTotalFit();
 //sort pop based on fitness vals
 Arrays.sort(pop.pop);
 
 for(int i=0;i<popSize;i++) {
     if(i==0) {
  //the chance of any specific individual being selected is
  //fitness/total fitness
  pop.setPSelect(i,pop.getFitness(i)/totalFit);
     } else {
  //add pSelect to the last Individuals pSelect
  //to create a personal "space" on the "wheel"
  pop.setPSelect(i,(pop.getFitness(i)/totalFit)+pop.getPSelect(i-1));
     }
 }
    }
    
    //binary search algorithm for roulette selection
    //returns index of target object
    //will select values even if they are not exactly equalto the target
    public static int binSearch(double target,Population pop) {
 int min=0,max=popSize-1,guess;
 
 while(true) {
     guess=(max+min)/2;
     
     if (pop.getPSelect(guess)==target) return guess;
     else if(pop.getPSelect(guess)<target) min=guess+1;
     else if(pop.getPSelect(guess)>target) max=guess;
     if (min==max) return min;  
 }
    }
    
    //takes all selected individual pairs and crosses their bna
    public static void xover(Individual[] pairs) {
 for(int i=0;i<popSize;i+=2) {
     if(rand.nextDouble()<xRate) {
  //set the cutting point
  int cut=rand.nextInt(bnaLen);
  
  //save substring
  String tmp=pairs[i].bna.substring(cut);
  
  //crossover operation
  pairs[i].bna=pairs[i].bna.substring(0,cut)+pairs[i+1].bna.substring(cut);
  pairs[i+1].bna=pairs[i+1].bna.substring(0,cut)+tmp;
     }
 }
    }
    
    //step through each bits in the population 
    //and flip them at a rate of mRate
    public static void mutate(Individual[] pool) {
 for(int i=0;i<popSize;i++) {//for each individual in the pool
     for(int j=0;j<bnaLen;j++) {//for each bit in the bna
  if(rand.nextDouble()<mRate) {
      StringBuilder newBna=new StringBuilder(pool[i].bna);
      
      if(newBna.charAt(j)=='0') {
   //replace 0 with 1
   newBna.setCharAt(j,'1');
      } else {
   //or replace 1 with 0
   newBna.setCharAt(j,'0');
      }
      pool[i].bna=new String(newBna);
  }
     }
 }
    }    
}//end class Selection
