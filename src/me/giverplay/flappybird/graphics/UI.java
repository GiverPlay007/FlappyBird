package me.giverplay.flappybird.graphics;

import static me.giverplay.flappybird.Game.WIDTH;
import static me.giverplay.flappybird.Game.HEIGHT;
import static me.giverplay.flappybird.Game.SCALE;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.giverplay.flappybird.Game;

public class UI
{
	private Game game;
	
	public UI()
	{
		game = Game.getGame();
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, WIDTH * SCALE - 1, HEIGHT * SCALE - 1);
		
		g.setColor(Color.WHITE);
		g.setFont(FontUtils.getFont(24, Font.BOLD));
		String score = String.valueOf(game.getScore());
		g.drawString(score, (WIDTH * SCALE - g.getFontMetrics(g.getFont()).stringWidth(score)) /2, 40);
	}
}
