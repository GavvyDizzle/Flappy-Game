import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class FlappyGame extends JComponent implements ActionListener, KeyListener {
	
	private static Pipe[] arr = new Pipe[2];
	private static boolean[] bool = new boolean[20];
	private int scrollSpeed = 0;
	private int birdX = 150;
	private int birdY = 290;
	private double birdYSpeed = 0;
	private double gravity = 3;
	private double jumpHeight = 17;
	private int pipeWidth = 50;
	private int score = 0;
	boolean isGameOver = false;
	boolean isGameWin = false;
	boolean gameStart = false;
	
	
	public static void main(String[] args) 
	{
		JFrame window = new JFrame("Flappy Game");
		FlappyGame game = new FlappyGame();
		window.add(game);
		window.pack();
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		Timer t = new Timer(10, game);
		t.start();
		window.addKeyListener(game);
		
		createPipes();
	}
	
	public Dimension getPreferredSize()
	{
		return new Dimension(600, 600);
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		g.setColor(new Color(100, 255, 255)); //draw bg
		g.fillRect(0, 0, 600, 600);
		
		g.setColor(new Color(76, 153, 0)); //draw bg
		g.fillRect(0, 551, 600, 49);
		g.setColor(Color.BLACK);
		g.fillRect(0, 550, 600, 2);
		
		
		g.setColor(Color.ORANGE); //draw bird
		g.fillOval(birdX, birdY, 20, 20);
		g.setColor(Color.BLACK);
		g.drawOval(birdX, birdY, 20, 20);
		
		for (int i = 0; i < arr.length; i++) { //draw pipes
		g.setColor(Color.GREEN);
		g.fillRect(arr[i].getPipeX(), 0, pipeWidth, arr[i].getTop());
		g.setColor(Color.BLACK);
		g.drawRect(arr[i].getPipeX(), -1, pipeWidth, arr[i].getTop() + 1); //top pipe
		g.setColor(Color.GREEN);
		g.fillRect(arr[i].getPipeX(), arr[i].getBottom() + 1, pipeWidth, 600 - arr[i].getBottom());
		g.setColor(Color.BLACK);
		g.drawRect(arr[i].getPipeX(), arr[i].getBottom() + 1, pipeWidth, 600 - arr[i].getBottom());
		}

		if(isGameOver) {
			g.setFont(new Font("sansserif", Font.BOLD, 42));
			g.setColor(new Color( (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255) ));
			g.drawString("GAM OVER", 200, 300);
		}
		
		if(isGameWin && !isGameOver) {
			g.setFont(new Font("sansserif", Font.BOLD, 42));
			g.setColor(new Color( (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255) ));
			g.drawString("YOU WIN", 200, 300);
		}
		
		if(!gameStart) {
			g.setFont(new Font("sansserif", Font.BOLD, 30));
			g.setColor(Color.BLACK);
			g.drawString("Press the up arrow to begin", 100, 50);		
		}
		else {
			g.setColor(Color.BLACK);
			g.setFont(new Font("sansserif", Font.BOLD, 20));
			g.drawString(Integer.toString(score), 295, 20);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) //executes every frame
	{	
		if (gameStart)
			birdY += birdYSpeed + gravity;
		
		for (int i = 0; i < arr.length; i++) {
			arr[i].setPipeX(scrollSpeed); //+ score * 0.25);
			
			if (arr[i].getPipeX() + pipeWidth < birdX + 20 && !bool[i]) {
				score++;
				bool[i] = true;
			}
		}
		
		if (birdYSpeed < 0) {
			birdYSpeed += 1;
			gravity = 4;
		}
		
		if (birdYSpeed == 0)
			gravity += .5;
		
		if (gravity > 10)
			gravity = 10;
		
		if (birdY < 0) {
			birdY = 0;
			birdYSpeed = 0;
		}
		
		if (birdY > 580)
			birdY = 580;
		
		
		for (int i = 0; i < arr.length; i++) {
			
			if ( birdX + 20 > arr[i].getPipeX() && birdX < arr[i].getPipeX() + pipeWidth &&
				( birdY < arr[i].getTop() || birdY + 20 > arr[i].getTop() + Pipe.empty ))
			{
				scrollSpeed = 0;
				isGameOver = true;
				gravity = 0;
			}
				
		}
		
		if (score == arr.length && birdX > arr[arr.length - 1].getPipeX() + 20) {
			isGameWin = true;
		}
		
		
		repaint();
	}
	
	public static void createPipes()
	{
		for (int i = 0; i < arr.length; i++)
			arr[i] = new Pipe( (int) ( (Math.random() * 250 ) + 100 ), 600 + i * 300);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!isGameOver && e.getKeyCode() == KeyEvent.VK_UP) {
			birdYSpeed = -jumpHeight;
			gameStart = true;
			scrollSpeed = 5;
		}
		else if ( (isGameOver || isGameWin) && e.getKeyCode() == KeyEvent.VK_R) {
			createPipes();
			isGameOver = false;
			isGameWin = false;
			gameStart = false;
			scrollSpeed = 0;
			birdY = 290;
			score = 0;
			for (int i = 0; i < bool.length; i++)
				bool[i] = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}