// note:  you can get rid of/change the package

package miditest;

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
 *  'tick' = the timestamp of the noteOn/noteOff event.  0 = start of the song.  6 = 1/16th note into the song,
 *      12 = 2/16th notes in, etc.
 *      
 */

public class MidiBuilder
{
	public void noteOn(int note, int tick) {}
	
	public void noteOff(int tick) {}
	
	public MidiSong songComplete() {}
}
