package me.giverplay.flappybird.sound;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound
{
	public static Clips theme = load("/calm3.wav", 1);
		
	public static class Clips
	{
		public Clip[] clips;
		private int p;
		private int count;
		
		public Clips(byte[] buffer, int count) throws IllegalArgumentException, LineUnavailableException, IOException, UnsupportedAudioFileException
		{
			if(buffer == null)
			{
				throw new IllegalArgumentException("Buffer n�o pode ser nulo");
			}
			
			clips = new Clip[count];
			
			this.count = count;
			
			for(int i = 0; i < count; i++)
			{
				clips[i] = AudioSystem.getClip();
				clips[i].open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer)));
			}
		}
		
		public void play() throws IllegalStateException
		{
			if(clips == null)
			{
				throw new IllegalStateException("Lista de clips est� vazia");
			}
			
			clips[p].stop();
			clips[p].setFramePosition(0);
			clips[p].start();
			
			p++;
			
			if(p >= count)
			{
				p = 0;
			}
		}
		
		public void loop() throws IllegalStateException
		{
			if(clips == null)
			{
				throw new IllegalStateException("Lista de clips est� vazia");
			}
			
			clips[p].loop(300);
		}
	}
	
	private static Clips load(String name, int count)
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataInputStream dis = new DataInputStream(Sound.class.getResourceAsStream(name));
			
			byte[] buffer = new byte[1024];
			
			int read = 0;
			
			while((read = dis.read(buffer)) >= 0)
			{
				baos.write(buffer, 0, read);
			}
			
			dis.close();
			
			byte[] data = baos.toByteArray();
			
			try
			{
				return new Clips(data, count);
			} 
			catch (IllegalArgumentException e)
			{
				System.out.println("Argumentos inv�lidos: " + e.getMessage());
			} 
			catch (LineUnavailableException e)
			{
				System.out.println("Linha indispon�vel");
			} 
			catch (UnsupportedAudioFileException e)
			{
				System.out.println("Arquivo de audio n�o suportado");
			}
		}
		catch(NullPointerException | IOException e)
		{
			try
			{
				try
				{
					return new Clips(null, count);
				} 
				catch (IllegalArgumentException e1)
				{
					System.out.println("Argumentos inv�lidos: " + e.getMessage());
				} 
				catch (LineUnavailableException e1)
				{
					System.out.println("Linha indispon�vel");
				} 
				catch (UnsupportedAudioFileException e1)
				{
					System.out.println("Arquivo de audio n�o suportado");
				}
			} 
			catch (IOException e1)
			{
				return null;
			}
		}
		
		return null;
	}
}
