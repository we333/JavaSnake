package we_Snake;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class WeTool {
	private WeTool(){}

	public static Image loadImage(String path){
		BufferedImage img=null;
		try{
			URL u = WeTool.class.getClassLoader().getResource(path);
			img = javax.imageio.ImageIO.read(u);
		}catch(IOException e){	e.printStackTrace();	}
		return img;
	}
}
