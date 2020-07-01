package me.giverplay.flappybird.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.giverplay.flappybird.Game;
import me.giverplay.flappybird.entities.Entity;

public class UI
{
	private static BufferedImage cor1 = Game.getGame().getSpritesheet().getSprite(48, 0, 8, 8);
	private static BufferedImage cor2 = Game.getGame().getSpritesheet().getSprite(56, 0, 8, 8);
	
	private Game game;
	
	public UI()
	{
		game = Game.getGame();
	}
	
	public void render(Graphics g)
	{
		g.drawImage(Entity.SPRITE_FRUTA[0], 2, Game.HEIGHT * 2 - 34, 28, 34, null);
		
		g.setColor(Color.yellow);
		g.setFont(FontUtils.getFont(14, Font.BOLD));
		g.drawString(game.getPlayer().getFruits() + "/" + (game.getFruits().size() + game.getPlayer().getFruits()), 36, Game.HEIGHT * 2 - 8);
		
		int coe = game.getPlayer().getMaxLife() - game.getPlayer().getLife();
		
		for(int i = game.getPlayer().getMaxLife() * 8; i >= 0; i -= 10)
		{
			g.drawImage(i <= coe * 8 ? cor2 : cor1, Game.WIDTH * 2 - 32 - i * 4, Game.HEIGHT * 2 - 32, 28, 28, null);
		}
		
	}
}
