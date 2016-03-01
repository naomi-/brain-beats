
/*This converts a bit string into a sequence of midi notes, which it then plays.*/

import java.util.ArrayList;

public class MidiConverter {
	
	public static void main(String[] args)
	{
		int count = 150;
		String s = new String();
		for(int i = 0; i < count*6; ++i)
		{
			if(Math.random() >= 0.5)  	s = s + "1";
			else 						s = s + "0";
		}
		
		convertToMidi(s);
	}
  
  //This method takes in a binary string and converts it to an array of midi notes
  public static void convertToMidi(String binaryString){
    try{
      MidiBuilder builder = new MidiBuilder();
      int midi = 0;
      ArrayList<Integer> notes = new ArrayList<Integer>();
      int i=0; //Counter for iterating through the binary string, six bits at a time
      int j=0; //Counter for the notes array
      boolean isNoteOn=false;
      
      while(i<binaryString.length()){
        int command = Integer.parseInt(binaryString.substring(i, i+2));
        System.out.println(command);
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
            case "1000": midi = 74;
            break;
            case "1001": midi = 76;
            break;
            case "1010": midi = 77;
            break;
            case "1011": midi = 79;
            break;
            case "1100": midi = 81;
            break;
            case "1101": midi = 83;
            break;
            case "1110": midi = 84;
            break; 
            case "1111": midi = 86;
          }
          //turn last note off
          if(j!=0){
            builder.noteOff(notes.get(j-1), j);
          }
          //turn new note on
          builder.noteOn(midi, j);
          isNoteOn=true;
        }
        
        i=i+6;
        j++;
        notes.add(midi);
        //Store note into notes array
        System.out.println(notes);
      }
      MidiPlayback out = new MidiPlayback();
      out.setSong(builder.songComplete());
      
      out.play();
      Thread.sleep(10000);
      out.close();
    }
    catch(Exception e)
    {}
  }
}


