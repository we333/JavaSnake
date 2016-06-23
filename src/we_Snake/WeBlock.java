package we_Snake;

import java.awt.Graphics;

public class WeBlock extends WeGameObject{
	public void draw(Graphics g){
		g.drawImage(img, (int)x, (int)y, null);
	}

	public WeBlock(String path, double x, double y){
		super(path, x, y);
	}

}
