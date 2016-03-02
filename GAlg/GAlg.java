/*
  GAlg is the parent class for:
  Individual, Population and Selection.

  This class is used just as a record for global variables for now.
 */

package ga;
import java.util.Random;

public class GAlg {
    
    //skeleton main
    public void main (String[] args) {
	//Population newGen=new Population();

	//loop here
	    //evaluate fitness
	    
	    //newGen=new Population(Selection.select(newGen),newGen);
	    //loop end
    }
    
    static int bnaLen=16;
    static int popSize=40;//must be even
    static double xRate=0.7;
    //chance that two individuals will swap their bits
    static double mRate=0.001;
    //chance that a bit in an individual will be flipped
    static Random rand=new Random();
    
}//end class GAlg