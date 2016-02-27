package miditest;

import javax.sound.midi.*;

public class MidiPlayback implements AutoCloseable
{
	private Sequencer midi = null;
	private boolean hadToOpen = false;
	
	public MidiPlayback() throws Exception
	{
		midi = MidiSystem.getSequencer();
		if(midi == null)
			throw new UnsupportedOperationException();
		
		if( !midi.isOpen() )
		{
			hadToOpen = true;
			midi.open();
		}
	}
	
	public void close()
	{
		if( midi != null && hadToOpen && midi.isOpen() )
		{
			hadToOpen = false;
			midi.close();
		}
	}
	
	public boolean isPlaying()
	{
		return (midi != null && midi.isRunning());
	}

	public void play()
	{
		if(midi != null)
		{
			midi.stop();
			midi.setTickPosition(0);
			midi.start();
		}
	}
	
	public void stop()
	{
		if(midi != null)
			midi.stop();
	}
	
	public void setSong(Sequence seq) throws InvalidMidiDataException
	{
		if(midi != null)
		{
			midi.setSequence(seq);
			midi.setTempoInBPM(120.0f);
		}
	}
}
