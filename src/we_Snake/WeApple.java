package we_Snake;

import java.awt.Graphics;

public class WeApple extends WeGameObject{
	private boolean live = true;

	public void draw(Graphics g){
		if(live)
			g.drawImage(img, (int)x, (int)y, null);
	}

	public WeApple(String path, double x, double y){
		super(path, x, y);
	}

	public void update(int x, int y){
		this.x = x;
		this.y = y;
	}

	public boolean isLive(){
		return live;
	}
	public void setLive(boolean live){
		this.live = live;
	}
}
