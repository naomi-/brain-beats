import java.util.*;
import java.lang.String;
import java.lang.StringBuilder;

public class GAlg {
    
    public void main (String[] args) {
	Population newGen=new Population();

	//loop here
	    //evaluate fitness
	    
	    newGen=new Population(Selection.select(newGen),newGen);
	    Selection.xover(newGen.pop);
	    Selection.mutate(newGen.pop);
	    //loop end
    }
    
    static int bnaLen=16;
    static int popSize=40;//must be even
    static double xRate=0.7;//chance that two individuals will swap their bits
    static double mRate=0.001;//chance that a bit in an individual will be flipped
    private static Random rand=new Random();
    
    public class Population {
	Individual[] pop;
	int gen;
	double totalFitness;
	
	//constructors
	Population () {
	    this(popSize);
	}
	
	Population (int popSize) {
	    this.pop=new Individual[popSize];
	    for(int i=0;i<pop.length;i++) {
		this.pop[i]=new Individual();
	    }
	    this.gen=0;
	}

	Population (Individual[] newPop,Population oldGen) {
	    this.pop=new Individual[newPop.length];
	    for (int i=0;i<pop.length;i++) {
		pop[i]=newPop[i];
	    }
	    this.gen=oldGen.gen+1;
	}

	//sum of all individual fitnesses
	public void findTotalFit() {
	    for (int i=0;i<popSize;i++) {
		totalFitness+=this.getFitness(i);
	    }
	}
	
	//getters
	public double getTotalFit() {
	    return this.totalFitness;
	}

	public Individual getIndividual (int i) {
	    return this.pop[i];
	}

	public String getBna(int i) {
	    return this.pop[i].bna;
	}

	public double getFitness(int i) {
	    return this.pop[i].fitness;
	}

	public double getPSelect(int i) {
	    return this.pop[i].pSelect;
	}

	//setters
	public void setBna(int i,String str) {
	    this.pop[i].bna=str;
	}
	
	public void setFitness(int i,double x) {
	    this.pop[i].fitness=x;
	}

	public void setPSelect(int i,double x) {
	    this.pop[i].pSelect=x;
	}
    }//end class Population
    
    public class Individual implements Comparable<Individual>{
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
	@Override
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
    
    public static class Selection {
	public static Individual[] select(Population pop) {
	    Individual[] selected=new Individual[popSize];
	    int temp;

	    //set pSelect vals for roulette selection
	    createWheel(pop);

	    //create a random number, then search for that value in pop
	    //add target object to selected[]
	    for(int i=0;i<popSize;i++) {
		temp=binSearch(rand.nextDouble(),pop);
		selected[i]=pop.getIndividual(temp);
	    }

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
		//pSelect of least fit Individual gets fitness/total fitness
		if(i==0) {
		   pop.setPSelect(i,pop.getFitness(i)/totalFit);
		//pSelect of everything else gets (fitness/total fitness)+pSelect(i-1)
		} else {
		    pop.setPSelect(i,(pop.getFitness(i)/totalFit)+pop.getPSelect(i-1));
		}
	    }
	}

	//binary search algorithm for roulette selection, returns index of target object
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
	
	//step through each bits in the population and flip them at a rate of mRate
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
	
    }//end class SelRep
}//end class GAlg