

import javax.sound.midi.*;
import javax.sound.midi.*;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

public class MidiPlayback 
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
