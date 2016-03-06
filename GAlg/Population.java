/*
  Population is an Object primarily used to store, retrieve
  and manipulate data for use in a genetic algorithm.
 */

package ga;
import java.io.*;

public class Population extends GAlg{
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

    Population (String filePath) throws Exception {
	this.pop=new Individual[popSize];
	loadFromCsv(filePath);
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

    //static methods

    //save data in a csv file
    //only saves gen and bna strings
    public void writeToCsv(String fileName) throws Exception {
	FileWriter writer=new FileWriter(fileName);

	writer.append(Integer.toString(this.gen));
	writer.append('\n');

	for(int i=0;i<popSize;i++) {
	    writer.append(getBna(i));
	    writer.append('\n');
	}
	writer.close();
    }

    //loads data from a csv file
    //should be called from constructor only
    public void loadFromCsv(String filePath) throws Exception {
	BufferedReader br=new BufferedReader(new FileReader(filePath));
	String line="";
	int i=0;

	line=br.readLine();
	this.gen=Integer.parseInt(line);
	
	while((line=br.readLine()) != null) {
	    this.setBna(i,line);
	    i++;
	}
	br.close();
    }
}//end class Population
