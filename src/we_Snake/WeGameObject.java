
package we_Snake;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class WeGameObject {
	Image img;
	double x,y;
	int width,height;

	public Rectangle getRect(){
		return new Rectangle((int)x,(int) y, width, height);
	}

	public WeGameObject(String path, double x, double y) {
		this.img =  WeTool.loadImage(path);
		this.width = img.getWidth(null);
		this.height = img.getWidth(null);
		this.x = x;
		this.y = y;
	}

	public WeGameObject(Image img, double x, double y) {
		this.img =  img;
		this.width = img.getWidth(null);
		this.height = img.getWidth(null);
		this.x = x;
		this.y = y;
	}

}
