package we_Snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class WeSnake extends WeGameObject{
	private GameFrame owner = null;
	private Body head = null;
	private Body tail = null;
	private WeSnakeDir dir = WeSnakeDir.R;

	private int size = 0;
	Image snakeHead = WeTool.loadImage("images/head.png");
	private boolean live = true;
	private boolean isBusy = false;
	
	public void draw(Graphics g){
		if(live)
		{
			move();
			for(Body b = head; b != null; b = b.next)
				g.drawImage(img, (int)b.x, (int)b.y, null);
			g.drawImage(snakeHead, (int)head.x, (int)head.y, null);	//repaint snake_head
		}
	}
 
	public synchronized void move(){
		isBusy = true;
		addNewHead();
		deleteFromTail();
		switch(dir)
		{
			case L:	x -= WeConst.GAME_BLOCK;	break;
			case R:	x += WeConst.GAME_BLOCK;	break;
			case U:	y -= WeConst.GAME_BLOCK;	break;
			case D:	y += WeConst.GAME_BLOCK;	break;
		}
		if(eatSelf())
			setLive(false);
		
		isBusy = true; notify();
	}

	public void eatApple()
	{
		addNewHead();
		addSize();
	}

	public boolean eatSelf()
	{
		if(size < 4)
			return false;
		for(Body b = head.next.next; b != null; b = b.next)
		{
			boolean isEatSelf = b.getRect().intersects(head.getRect());		
			if(b.x == head.x && b.y == head.y)
				return true;
		}
		
		return false;
	}

	public void addNewHead()
	{
		Body newHead = null;
		switch(dir)
		{
			case L:	newHead = new Body("images/body.png", x-WeConst.GAME_BLOCK, y);	break;		
			case R:	newHead = new Body("images/body.png", x+WeConst.GAME_BLOCK, y);	break;			
			case U:	newHead = new Body("images/body.png", x, y-WeConst.GAME_BLOCK);	break;			
			case D:	newHead = new Body("images/body.png", x, y+WeConst.GAME_BLOCK);	break;			
		}
		newHead.next = head;
		head.prev = newHead;
		head = newHead;
	}

	public void deleteFromTail()
	{
		tail = tail.prev;
		tail.next = null;
	}

	public synchronized void direction(KeyEvent e){
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:	if(dir != WeSnakeDir.R)	dir = WeSnakeDir.L;	break;
			case KeyEvent.VK_UP:	if(dir != WeSnakeDir.D)	dir = WeSnakeDir.U;	break;
			case KeyEvent.VK_RIGHT:	if(dir != WeSnakeDir.L)	dir = WeSnakeDir.R;	break;
			case KeyEvent.VK_DOWN:	if(dir != WeSnakeDir.U)	dir = WeSnakeDir.D;	break;
			default:	break;
		}
	}
 
	private class Body extends WeGameObject{
	//	WeSnakeDir dir = WeSnakeDir.R;
		Body next = null;
		Body prev = null;
		
		void draw(Graphics g){
			g.drawImage(img, (int)x, (int)y, null);
		}
		Body(String path, double x, double y){
			super(path, x, y);
		}
	}
 
	public WeSnake(String path, double x, double y) {
		super(path, x, y);
		size = WeConst.SNAKE_INIT_SIZE;
		head = tail = new Body("images/body.png", x, y);
	}
	public void addSize()	{	size ++;}
	public int getSize()		{	return size;}
	public boolean isLive()	{	return live;}
	public void setLive(boolean live){	this.live = live;}
}
