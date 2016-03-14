import java.lang.Runtime;
import java.lang.Process;
import java.util.Scanner; 
import java.io.File;
import java.util.Arrays;

public class brainmusic{
  

  private static boolean createFolder(String theFilePath)
{
    boolean result = false;

    File directory = new File(theFilePath);

    if (directory.exists()) {
        System.out.println("Folder already exists");
    } else {
        result = directory.mkdirs();
    }

    return result;
}
  
  public static void main(String[] args){
    try
    {
      int generations;
      
      Scanner a = new Scanner(System.in);
        String currentDirectory;
        currentDirectory = System.getProperty("user.dir");
        System.out.println("Current working directory : "+currentDirectory);
        Population pop = new Population();
        String[] genArray = new String[16];
       

          
      System.out.println("how many generations shall we listen to?");
      generations = Integer.parseInt(a.next());
      for(int i=1; i<=generations; i++){
        createFolder("/Users/abraxas/Desktop/brainmusic/gen"+i);
    
      for(int m=0; m<16; m++){
          genArray[m]=pop.getBna(m);
        }
      
        for(int n=0; n<16; n++){
          String c = new String("./muse-player -l 5000 -C gen"+i+"/song"+(n+1)+" -i /muse/elements/delta_absolute /muse/elements/is_good");
          Process read = Runtime.getRuntime().exec(c);
          MidiConverter.convertToMidi(genArray[n]);
          read.destroy();
          System.out.println("Created data file gen"+i+"song"+(n+1));
          
          CSVParse parser = new CSVParse("/Users/abraxas/Desktop/brainmusic/gen"+i+"/song"+(n+1));
          double temp=parser.parse();
          pop.setFitness(n,temp);
       
        }
        
        System.out.println("done gen"+i);
        pop = new Population(Selection.select(pop),pop);
          
        
      }
      System.out.println("all done!");
    }
    catch(Exception e){
    System.out.println(e.getMessage());
   
    }
       
  }
}