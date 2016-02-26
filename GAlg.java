import java.util.Random;
import java.lang.String;
import java.lang.StringBuilder;

public class GAlg {
	
	public static void main (String[] args) {
		
	}
	
	static int bnaLen=16;
	static int popSize=40;
	static double xRate=0.7;//chance that two individuals will swap their bits
	static double mRate=0.001;//chance that a bit in an individual will be flipped
	private static Random rand=new Random();

	public class Population {
		Individual[] pop;	
	
		//constructors
		Population () {
			this(popSize);
		}
	
		Population (int popSize) {
			pool=new Individual[popSize];
			for(int i=0;i<pop.length;i++) {
				pool[i]=new Individual();
			}
		}
	
		//getters
		public Individual getIndividual (int i) {
			return pop[i];
		}
	
		public int getPoolSize() {
			return popSize;
		}
	}
	
	public class Individual {
		String bna;
		double fitness;
	
		//constructors
		Individual () {
			this(bnaLen);
		}
	
		Individual (int l) {
			bna=new String(binString(l));
		}
	
		//random binary string generation
		private StringBuilder binString(int l) {
			StringBuilder sta=new StringBuilder();
			int tmp;
		
			for (int i=0;i<l;i++) {
				tmp=rand.nextInt(2);
				sta.append(tmp);
			}
			return sta;
		}
	
		//getters
		public String getBna() {
			return this.bna;
		}
	
		public int getSize() {
			return this.bna.length();
		}
	
		//bna setter
		public void setBna(String str) {
			this.bna=str;
		}
	}
		
	//takes selected individuals and crosses their BNA strings
	public static void xOver(Individual a,Individual b) {
		if(rand.nextDouble()<xRate) {
			//System.out.println("Crossover.");
			int cut=rand.nextInt(a.getSize());
			String tmpA=a.getBna().substring(cut);
			String tmpB=b.getBna().substring(cut);
			a.setBna((a.getBna().substring(0,cut))+tmpB);
			b.setBna((b.getBna().substring(0,cut))+tmpA);
		}
	}
	
	//step through the chosen individuals bits and flip them at a rate of mRate
	public static void mutate(Individual a) {
		for(int i=0;i<a.getSize();i++) {
			if(rand.nextDouble()<mRate) {
				System.out.println("Mutation at "+i);
				String tmpA=a.getBna().substring(0,i);
				String tmpZ=a.getBna().substring(i+1,a.getSize());
				if (a.getBna().charAt(i)=='0') {
					a.setBna(tmpA+"1"+tmpZ);
				} else {
					a.setBna(tmpA+"0"+tmpZ);
				}
			}
		}
	}
	
	public class Selection {
		public static roulette(Population pop,int selSize) {
			
		}
		
	}

}