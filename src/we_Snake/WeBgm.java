package we_Snake;

import java.applet.Applet; 
import java.applet.AudioClip;
import java.io.File;
import java.net.URL; 

public class WeBgm extends Applet
{
	AudioClip clipBGMusic; 
	File fileBGMusic;
	URL urlBGMusic;
	 
	public WeBgm() 
	{
		try {
			 fileBGMusic = new File("sounds/dead.wav");
			 urlBGMusic = fileBGMusic.toURL();
			 clipBGMusic = Applet.newAudioClip(urlBGMusic);	  
		} catch(Exception e){System.out.println(e);}
		clipBGMusic.play();       
	}
}
