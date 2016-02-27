import java.util.ArrayList;

public class MidiConverter {
 
 //This method takes in a binary string and converts it to an array of midi notes
 private static ArrayList<Integer> convertToMidi(String binaryString){
   int midi = 0;
   ArrayList<Integer> notes = new ArrayList<Integer>();
   int i=0;
   int j=0;
   while(i<binaryString.length()){
     int command = Integer.parseInt(binaryString.substring(i, i+2));
     System.out.println(command);
     if(command==00 || command==01){
       //Check if there is a previous note
       if(j!=0){
         //Sustain the previous note
         midi = notes.get(j-1);
       }else{
         //Play a rest
       }
     }else if(command==10){
       //Rest
     }else if(command==11){
       //Play new note
       String note = binaryString.substring(i+2, i+6);
       
       //Encode the correct midi note from the bit string
       switch(note){
         case "0000": midi = 60;
                      break;
         case "0001": midi = 62;
                      break;
         case "0010": midi = 64;
                      break;
         case "0011": midi = 65;
                      break;
         case "0100": midi = 67;
                      break;
         case "0101": midi = 69;
                      break;
         case "0110": midi = 71;
                      break;
         case "0111": midi = 72;
                      break;
       }
     }
     
     i=i+6;
     j++;
     notes.add(midi);
     //Store note into notes array
     System.out.println(notes);
   }
   //int midiFinal = Integer.parseInt(midi);
   return notes;
 }
 
 
  public static void main(String[] args)
 {
  try
  {
   MidiBuilder builder = new MidiBuilder();
   
   ArrayList<Integer> notes = convertToMidi("110101001111110110110111000000110110110100110110110101");

   //ArrayList<Integer> notes = new ArrayList<Integer>();
   //notes.add(60);
   //notes.add(62);
   //64, 65, 67, 69, 71, 72, 71, 69, 67, 65, 64, 62, 60);

   for(int i = 0; i < notes.size(); ++i)
   {
    builder.noteOn(notes.get(i), i*4);
    builder.noteOff(notes.get(i), (i+1)*4);
   }
   
   
   MidiPlayback out = new MidiPlayback();
   out.setSong(builder.songComplete());
   
   out.play();
   Thread.sleep(8000);
   
   out.close();
  }
  catch(Exception e)
  {}
 }

}
