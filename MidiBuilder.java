// note:  you can get rid of/change the package

package miditest;

import javax.sound.midi.*;

/*
 * This will be the interface for the binary data conversion to generate a complete
 * MIDI 'song'.
 * 
 * Usage:
 *   - Create a MidiBuilder object
 *   - examine binary data and translate to a series of noteOn/noteOff commands
 *   - once all commands complete, call songComplete to finish the building process
 *   	and get a MidiSong object which can be used to play the song back.
 *   
 *   
 *  'note' = integer value that MIDI uses to represent the note.  Example:  60 = middle C.  61 = C#, 62=D, etc.
 *  
 *  'tick' = the timestamp of the noteOn/noteOff event.  0 = start of the song (first position), 1 = next position, 2 = next, etc.
 *      
 */

public class MidiBuilder
{
	private Sequence sequence;
	private Track track;
	private static final int velocity = 100;
	private static final int tickMult = 6;      // 1/16th note is tick=6 for the MIDI format
	private static final int tickBoost = 5;     // for some reason MIDI output gets paused when tick=0
											    //   so add a bit to prevent that from happening
	
	public MidiBuilder() throws InvalidMidiDataException
	{
		reset();
	}
	
	public void noteOn(int note, int tick) throws InvalidMidiDataException
	{
		tick = (tick * tickMult) + tickBoost;
		track.add( new MidiEvent( 
				new ShortMessage(
						ShortMessage.NOTE_ON,
						note,
						velocity
						),
				tick
				));
		
	}
	
	public void noteOff(int note, int tick) throws InvalidMidiDataException
	{
		tick = (tick * tickMult) + tickBoost;
		track.add( new MidiEvent( 
				new ShortMessage(
						ShortMessage.NOTE_OFF,
						note,
						velocity
						),
				tick
				));
	}
	
	public Sequence songComplete() throws InvalidMidiDataException
	{
		Sequence out = sequence;

		reset();
		
		return out;
	}
	
	private void reset() throws InvalidMidiDataException
	{
		sequence = new Sequence(Sequence.PPQ, 24, 1);
		track = sequence.getTracks()[0];
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args)
	{
		try
		{
			MidiBuilder builder = new MidiBuilder();
			
			int notes[] = {60, 62, 64, 65, 67, 69, 71, 72, 71, 69, 67, 65, 64, 62, 60};

			for(int i = 0; i < notes.length; ++i)
			{
				builder.noteOn(notes[i], i*4);
				builder.noteOff(notes[i], (i+1)*4);
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
