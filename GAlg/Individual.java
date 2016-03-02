/*
  Individual is a record for use in a genetic algorithm.

  Only the bna field is set upon construction.
  The other fields are set during evaluation and selection processes.
  No interaction is inteded to be done within this class,
  use Population instead.
 */

package ga;

public class Individual extends GAlg implements Comparable<Individual>{
    String bna;
    double fitness;
    double pSelect;
    
    //constructors
    Individual () {
	this(bnaLen);
    }
    
    Individual (int l) {
	bna=new String(binString(l));
    }
    
    Individual (String str) {
	bna=str;
    }
    
    //overriding compareTo method to sort by fitness
    //@Override
    public int compareTo(Individual i) {
	double temp=this.fitness - i.fitness;
	if (temp==0) return 0;
	else if(temp>0) return 1;
	else return -1;
    }
    
    //random binary string generation
    private StringBuilder binString(int l) {
	StringBuilder sta=new StringBuilder();
	
	for (int i=0;i<l;i++) {
	    sta.append(rand.nextInt(2));
	}
	return sta;
    }
}//end class Individual
