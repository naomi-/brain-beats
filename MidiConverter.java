
/*This converts a bit string into a sequence of midi notes, which it then plays.*/

import java.util.ArrayList;

public class MidiConverter {
 
 public static void main(String[] args)
 {
   generateAudio();
 }
 
 public static String generateAudio()
 {
  int count = 80;
  String s = new String();
  for(int i = 0; i < (count*6)+5; ++i)
  {
   if(Math.random() >= 0.5)   s = s + "1";
   else       s = s + "0";
   
   
  }
  
  convertToMidi(s);
  return s;
 }
  
  //This method takes in a binary string and converts it to an array of midi notes
  public static void convertToMidi(String binaryString){
    try{
      // key notes for major and minor scales
      final int[][] midicodes = {
              {60, 62, 64, 65, 67, 69, 71, 72, 74, 76, 77, 79, 81, 83, 84}, // c  major
              {60, 62, 63, 65, 67, 68, 70, 72, 74, 75, 77, 79, 80, 82, 84}  // c  minor
      };
      
      // header to scale transformation
      final int[][] headerToScale = 
      {
      //  key, major/minor
          { 0, 0 },  // C  major
          { 1, 0 },  // C# major
          { 2, 0 },  // D  major
          { 3, 0 },  // D# major
          { 4, 0 },  // E  major
          { 5, 0 },  // F  major
          { 6, 0 },  // F# major
          { 7, 0 },  // G  major
          { 8, 0 },  // G# major
          { 9, 0 },  // A  major
          {10, 0 },  // A# major
          {11, 0 },  // B  major
          { 0, 1 },  // C  minor
          { 1, 1 },  // C# minor
          { 2, 1 },  // D  minor
          { 3, 1 },  // D# minor
          { 4, 1 },  // E  minor
          { 5, 1 },  // F  minor
          { 6, 1 },  // F# minor
          { 7, 1 },  // G  minor
          { 8, 1 },  // G# minor
          { 9, 1 },  // A  minor
          {10, 1 },  // A# minor
          {11, 1 },  // B  minor
          { 0, 0 },  // C  major
          { 2, 0 },  // D  major
          { 4, 0 },  // E  major
          { 5, 0 },  // F  major
          { 7, 0 },  // G  major
          { 9, 0 },  // A  major
          {11, 0 },  // B  major
          { 0, 0 }
      };
      
      int header = Integer.parseInt(binaryString.substring(0, 5), 2);
      int keyAdjust = headerToScale[header][0];
      int majorMinor = headerToScale[header][1];
     
      MidiBuilder builder = new MidiBuilder();
      int midi = 0;
      ArrayList<Integer> notes = new ArrayList<Integer>();
      int i=5; //Counter for iterating through the binary string, six bits at a time (start after the header)
      int j=0; //Counter for the notes array
      boolean isNoteOn=false;
      
      while(i<binaryString.length()){
        int command = Integer.parseInt(binaryString.substring(i, i+2));
        int data = Integer.parseInt(binaryString.substring(i+2, i+6), 2);
        
        //System.out.println(command);
        if(command==00 || command==01){
          //Sustain (don't do anything)
        }else if(command==10){
          if(isNoteOn){
            //Play a rest (turn the note off, and don't turn it back on)
            builder.noteOff(notes.get(j-1), j);
            isNoteOn=false;
          }
        }else if(command==11){
          //Play new note
          // unless data=1111, in which case it's a sustain
          if(data == 0xF){
         // Sustain (don't do anything)
          }
          else{
              midi = midicodes[majorMinor][data] + keyAdjust - 12;
              
           //turn last note off
           if(j!=0){
             builder.noteOff(notes.get(j-1), j);
           }
           //turn new note on
           builder.noteOn(midi, j);
           isNoteOn=true;
          }
        }
        
        i=i+6;
        j++;
        notes.add(midi);
        //Store note into notes array
        //System.out.println(notes);
      }
      MidiPlayback out = new MidiPlayback();
      out.setSong(builder.songComplete());
      
      out.play();
      while(out.isPlaying())
          Thread.sleep(100);
      out.close();
    }
    catch(Exception e)
    {}
  }
}


