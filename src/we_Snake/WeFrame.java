package we_Snake;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WeFrame extends Frame{

	public void launchFrame(){
		setSize(WeConst.GAME_WIDTH, WeConst.GAME_HEIGHT);
		setLocation(100, 100);
		setVisible(true);

		new PaintThread().start();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}

	private Image offScreenImage = null;
	public void update(Graphics g)
	{
		if(offScreenImage == null)
			offScreenImage = this.createImage(WeConst.GAME_WIDTH, WeConst.GAME_HEIGHT);
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	class PaintThread extends Thread
	{
		public void run(){
			while(true)
			{
				repaint();
				try{Thread.sleep(300);}catch(InterruptedException e){e.printStackTrace();}
			}
		}
	}
}
