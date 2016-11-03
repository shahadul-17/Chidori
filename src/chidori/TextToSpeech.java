package chidori;

import javax.swing.JTextArea;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.AudioPlayer;

public class TextToSpeech {
	
	private static int position = 0;
	private static boolean pause = false;
	public static boolean reading = false;
	
	private static final String voiceName = "kevin";
	private static Voice voice = null;
	private static AudioPlayer audioPlayer;
	public static volatile Tab tab;
	
	private static void initialize() {
		stop();
		
		try {
			Thread.sleep(100);
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		
		reading = true;
		voice = VoiceManager.getInstance().getVoice(voiceName);
		voice.allocate();
		
		audioPlayer = voice.getAudioPlayer();
	}
	
	public static void read(final String text) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				initialize();
				
				try {
					voice.speak(text);
				}
				catch (Exception exc) {
					/*
					 * don't need to know this exception...
					 */
				}
				
				reading = false;
			}
		}).start();
	}
	
	public static void read(final boolean fromCaret, final JTextArea textArea) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				initialize();
				
				String[] splits;
				
				if (fromCaret) {
					position = textArea.getCaretPosition();
					splits = textArea.getText().substring(textArea.getCaretPosition()).split("[\\r\\n]+");
				}
				else {
					position = 0;
					splits = textArea.getText().split("[\\r\\n]+");
				}
				
				for (int i=0; i<splits.length && reading; i++) {
					try {
						if ((position = textArea.getText().indexOf(splits[i], position)) >= 0) {
							if (Frame.tabbedPane.selectedTab.tabBody.textArea.equals(textArea)) {
								textArea.select(position, (position + splits[i].length()));
							}
							
							position += splits[i].length();
						}
						
						voice.speak(splits[i]);
					}
					catch (Exception exc) {
						/*
						 * don't need to know this exception...
						 */
					}
					
					if (pause && reading) {
						continue;
					}
				}
				
				reading = false;
			}
		}).start();
	}
	
	public static void pause() {
		audioPlayer.pause();
		
		pause = true;
	}
	
	public static void resume() {
		pause = false;
		
		audioPlayer.resume();
	}
	
	public static void stop() {
		reading = false;
		
		if (voice != null) {
			audioPlayer.pause();
			voice.deallocate();
		}
		
		voice = null;
	}
	
}