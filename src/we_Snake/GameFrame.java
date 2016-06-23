package we_Snake;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
 
public class GameFrame extends WeFrame{
	boolean gamePause = false;
	boolean gameOver = false;
	Image bg = WeTool.loadImage("images/desert.jpg");
	WeSnake snake = new WeSnake("images/body.png", WeConst.SNAKE_BORN_X, WeConst.SNAKE_BORN_Y);
	WeApple apple = new WeApple("images/apple.png", WeConst.APPLE_BORN_X, WeConst.APPLE_BORN_Y);
	WeBoss boss = new WeBoss("images/bossCry.png", WeConst.BOSS_BORN_X, WeConst.BOSS_BORN_Y);
	WeBgm bgm = null;
	
	ArrayList<WeGameObject> blocks = new ArrayList<WeGameObject>();

	private int x = WeConst.GAME_START_X;
	private int y = WeConst.GAME_START_Y;

	public void gameOver(Graphics g)
	{
		g.drawImage(bg, 0, 0, null);
		paintBlocks(g);
		boss.draw(g);
		apple.draw(g);

		Color c = g.getColor();
	 	
	 	g.setColor(Color.red);
	 	g.setFont(new Font("Serif", Font.BOLD, 20));
	 	String strScore="Game Over   Your Score : "+String.valueOf(snake.getSize());
		g.drawString(strScore, 100, 250);
		g.setColor(c);
		
		if(!gameOver)
			bgm = new WeBgm();
		gameOver = true;
	}

	public void paintBlocks(Graphics g)
	{
		for(int i = 0; i < blocks.size(); i++)
		{
			WeBlock b = (WeBlock)blocks.get(i);
			b.draw(g);
		}
	}

	public void paint(Graphics g)
	{
		if(!snake.isLive())			// snake eat itself
		{
			gameOver(g);
		}
		else if(gamePause)			// KEY_SPACE
		{
			Color c = g.getColor();
			g.setColor(Color.blue);
			g.setFont(new Font("Serif", Font.BOLD, 20));
			g.drawString("Game Pause, please press SPACE", 100, 250);
			g.setColor(c);
		}
		else
		{
			g.drawImage(bg, 0, 0, null);
			paintBlocks(g);
			if(snake.getSize() > 4)
				boss.draw(g);
			apple.draw(g);
			snake.draw(g);
			if(checkHitBlock())
			{
				snake.setLive(false);
				gameOver(g);
				return;
			}

			if(checkHitBoss())
			{
				boss.setLive(false);
				snake.setLive(false);
				gameOver(g);
				return;
			}

			if(checkHitApple())
			{
				while(true)				// confirm new apple hit the barrier or not
				{
					boolean ok = true;
					int x = ramdomBlocks(30, 400);
					int y = ramdomBlocks(30, 400);
					apple.update(x, y);
					for(int i = 0; i < blocks.size(); i++)
					{
						WeBlock b = (WeBlock)blocks.get(i);
						if(b.getRect().intersects(apple.getRect()))
							ok = false;
					}
					if(ok)
						break;
				}
				apple.draw(g);
				snake.eatApple();
			}
		}
	}

	boolean checkHitBlock()
	{
		for(int i = 0; i < blocks.size(); i++)
		{
			WeBlock b = (WeBlock)blocks.get(i);
			boolean hitBlock = b.getRect().intersects(snake.getRect());	// confirm snake hit the border or not
			if(hitBlock)
				return true;
		}
		return false;
	}

	boolean checkHitBoss()
	{
		return boss.getRect().intersects(snake.getRect());
	}

	boolean checkHitApple()
	{
		return apple.getRect().intersects(snake.getRect());	
	}

	private int ramdomBlocks(int min, int max)
	{
		return min + (int)(Math.random()*((max - min) + 1));
	}

	// create border & barrier into ArrayList
	private void createBlocks()
	{
		for(int i = x; i < WeConst.GAME_WIDTH; i += WeConst.GAME_BLOCK+1)
		{
			blocks.add(new WeBlock("images/wall.png", i, WeConst.GAME_START_Y));
			blocks.add(new WeBlock("images/wall.png", i, WeConst.GAME_HEIGHT - WeConst.GAME_BLOCK-10));
		}
		for(int i = y; i < WeConst.GAME_HEIGHT; i += WeConst.GAME_BLOCK+1)
		{
			blocks.add(new WeBlock("images/wall.png", WeConst.GAME_START_X, i));
			blocks.add(new WeBlock("images/wall.png", WeConst.GAME_WIDTH - WeConst.GAME_BLOCK-10, i));
		}

		for(int i = 0; i < WeConst.GAME_BLOCK_NUM; i++)
		{
			int x = ramdomBlocks(WeConst.GAME_START_X/WeConst.GAME_BLOCK+2, WeConst.GAME_WIDTH/WeConst.GAME_BLOCK-3);
			int y = ramdomBlocks(WeConst.GAME_START_Y/WeConst.GAME_BLOCK+2, WeConst.GAME_HEIGHT/WeConst.GAME_BLOCK-3);
			if((x == WeConst.APPLE_BORN_X || y == WeConst.APPLE_BORN_Y)
			|| (x - WeConst.SNAKE_BORN_X < 5*WeConst.GAME_BLOCK && y == WeConst.SNAKE_BORN_Y))
				continue;
			blocks.add(new WeBlock("images/barrier.png", (x)*WeConst.GAME_BLOCK, (y)*WeConst.GAME_BLOCK));
		}
	}

	class KeyMonitor extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e){
			if(e.getKeyCode() == KeyEvent.VK_SPACE)
				gamePause = !gamePause;
			else
				snake.direction(e);
		}
	}

	public void launchFrame(){
		super.launchFrame();
		addKeyListener(new KeyMonitor());
		createBlocks();
	}

	public static void main(String [] args) {
		GameFrame gf = new GameFrame();
		gf.launchFrame();
	}
}
