package we_Snake;

import java.awt.Graphics;
import java.awt.Image;

public class WeBoss extends WeGameObject{
	private boolean live = true;
	private double degree;

	Image bossWin = WeTool.loadImage("images/boss.png");
	public void draw(Graphics g){
		if(live)
		{
			x -= WeConst.GAME_BLOCK*Math.cos(degree);
			y += WeConst.GAME_BLOCK*Math.sin(degree);
			if(y > WeConst.GAME_HEIGHT - 4*WeConst.GAME_BLOCK || y < 3*WeConst.GAME_BLOCK)
				degree = -degree;
			if(x < 2*WeConst.GAME_BLOCK || x > WeConst.GAME_WIDTH - 4*WeConst.GAME_BLOCK)
				degree = Math.PI - degree;
			g.drawImage(img, (int)x, (int)y, null);
		}
		else
			g.drawImage(bossWin, (int)x, (int)y, null);
	}

	public void update(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	public boolean isLive()				{	return live;		}
	public void setLive(boolean live) 	{	this.live = live;	}
	public WeBoss(String path, double x, double y){
		super(path, x, y);
		degree = Math.random()*Math.PI*2;
	}

}
