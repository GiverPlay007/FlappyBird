package me.giverplay.flappybird;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JFrame;
import me.giverplay.flappybird.entities.Entity;
import me.giverplay.flappybird.entities.Player;
import me.giverplay.flappybird.graphics.FontUtils;
import me.giverplay.flappybird.graphics.Spritesheet;
import me.giverplay.flappybird.graphics.UI;

public class Game extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 340;
	public static final int HEIGHT = 264;
	public static final int SCALE = 2;
	public static final int TILE_SIZE = 16;
	public static final int FLOOR_OFFSET = 26;
	
	private static Game game;
	private static int FPS = 0;
	
	private final ArrayList<Entity> entities = new ArrayList<>();
	
	private TubeGenerator generator;
	private Spritesheet sprite;
	private Player player;
	private UI ui;
	
	private BufferedImage image;
	private Thread thread;
	
	private boolean isRunning = false;
	private boolean showGameOver = true;
	private boolean dead = false;
	private boolean won = false;
	
	private int gameOverFrames = 0;
	private int score = 0;
	private int record = 0;
	
	public static Game getGame()
	{
		return game;
	}
	
	public Game()
	{
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setupFrame();
	}
	
	public static void main(String[] args)
	{
		new Game().start();
	}
	
	private void setupFrame()
	{
		JFrame frame = new JFrame("Game 03 - Flappy Bird Clone");
		frame.add(this);
		frame.setResizable(false);
		frame.setUndecorated(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		new Listeners(this);
	}
	
	public synchronized void start()
	{
		game = this;
		sprite = new Spritesheet("/Spritesheet.png");
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
		ui = new UI();
		record = 0;
		
		restart();
		
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop()
	{
		isRunning = false;
		
		try
		{
			thread.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public synchronized void restart()
	{
		entities.clear();
		won = false;
		dead = false;
		score = 0;
		
		generator = new TubeGenerator(this);
		player = new Player(WIDTH / 2 - 8, 100, 16, 16);
		
		entities.add(player);
	}
	
	public static void handleRestart()
	{
		game.restart();
	}
	
	@Override
	public void run()
	{
		requestFocus();
		
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		
		double ticks = 60.0D;
		double ns = 1000000000 / ticks;
		double delta = 0.0D;
		
		int fps = 0;
		
		while(isRunning)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if(delta >= 1)
			{
				tick();
				render();
				
				delta--;
				fps++;
			}
			
			if(System.currentTimeMillis() - timer >= 1000)
			{
				FPS = fps;
				fps = 0;
				timer = System.currentTimeMillis();
			}
		}
		
		stop();
	}
	
	public synchronized void tick()
	{
		if(!dead && !won)
		{
			generator.tick();
			
			for(int i = 0; i < entities.size(); i++) entities.get(i).tick();
		}
	}
	
	public synchronized void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		
		g.setColor(new Color(0xFF2eccd1));
		g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
		
		entities.sort(Entity.sortDepth);
		
		for(Entity entity : entities)
		{
			entity.render(g);
		}
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		
		renderSmooth(g);
		
		if(dead || won)
		{
			Graphics2D g2 = (Graphics2D) g;
			
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
			
			String txt = dead ? "Game Over" : "Você Venceu!";
			g.setColor(Color.WHITE);
			g.setFont(FontUtils.getFont(32, Font.BOLD));
			g.drawString(txt, (WIDTH * SCALE - g.getFontMetrics(g.getFont()).stringWidth(txt)) / 2, HEIGHT * SCALE / 2);
			
			gameOverFrames++;
			
			if(gameOverFrames > 30)
			{
				gameOverFrames = 0;
				showGameOver = !showGameOver;
			}
			
			if(showGameOver)
			{
				g.setFont(FontUtils.getFont(24, Font.BOLD));
				g.drawString("> Aperte ENTER para reiniciar <", (WIDTH * SCALE - g.getFontMetrics(g.getFont()).stringWidth("> Aperte ENTER para reiniciar <")) / 2, HEIGHT * SCALE / 2 + 28);
			}
		}
		
		bs.show();
	}
	
	public void renderSmooth(Graphics g)
	{
		ui.render(g);
		
		g.setColor(new Color(100, 100, 100));
		g.setFont(FontUtils.getFont(18, Font.PLAIN));
		
		g.setColor(Color.WHITE);
		g.setFont(FontUtils.getFont(11, Font.PLAIN));
		g.drawString("FPS: " + FPS, 2, 12);
	}
	
	public Player getPlayer()
	{
		return this.player;
	}
	
	public Spritesheet getSpritesheet()
	{
		return this.sprite;
	}
	
	public ArrayList<Entity> getEntities()
	{
		return this.entities;
	}
	
	public boolean isDead()
	{
		return this.dead;
	}
	
	public boolean winnowed()
	{
		return this.won;
	}
	
	public void kill()
	{
		this.dead = true;
	}
	
	public int getScore()
	{
		return this.score;
	}
	
	public int getRecord()
	{
		return this.record;
	}

	public void addScore()
	{
		++score;
		
		if(score > record)
		{
			record = score;
		}
	}
}
