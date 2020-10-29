package me.giverplay.flappybird.graphics;

import static me.giverplay.flappybird.Game.SCALE;
import static me.giverplay.flappybird.Game.WIDTH;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import me.giverplay.flappybird.Game;

public class UI
{
	private final Game game;
	private BufferedImage image;
	
	public int x1 = 0;
	public int x2;
	
	private final int width;
	private final int height;
	
	public UI()
	{
		game = Game.getGame();
		
		try
		{
			image = ImageIO.read(Game.class.getResourceAsStream("/Floor.png"));
		} catch (IOException e)
		{
			System.out.println("IOException");
		}
		
		width = image.getWidth();
		height = image.getHeight();
		
		x2 = width;
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.setFont(FontUtils.getFont(24, Font.BOLD));
		
		String score = String.valueOf(game.getScore());
		String record = String.valueOf(game.getRecord());
		
		// Sim, isso está bem feio...
		g.drawString(record, (WIDTH * SCALE - g.getFontMetrics().stringWidth(record)) / 2, 32);
		g.drawString(score, (WIDTH * SCALE - g.getFontMetrics().stringWidth(score)) / 2, 58);
		
		if(!game.getPlayer().isDead())
		{
			x1 -= 2;
			x2 -= 2;
			
			if (x1 < -width)
			{
				x1 = 0;
			}
			
			if (x2 < 0)
			{
				x2 = width;
			}
		}
		
		g.drawImage(image, x1, Game.HEIGHT * Game.SCALE - height, null);
		g.drawImage(image, x2, Game.HEIGHT * Game.SCALE - height, null);
	}
}
