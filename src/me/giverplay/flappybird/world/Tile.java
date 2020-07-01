package me.giverplay.flappybird.world;

import static me.giverplay.flappybird.world.World.TILE_SIZE;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.giverplay.flappybird.Game;

public class Tile
{
	private static Game game = Game.getGame();
	
	public static BufferedImage TILE_PAREDE = game.getSpritesheet().getSprite(TILE_SIZE * 0, 0, TILE_SIZE, TILE_SIZE);
	public static BufferedImage TILE_GRAMA = game.getSpritesheet().getSprite(TILE_SIZE * 1, 0, TILE_SIZE, TILE_SIZE);
	
	private BufferedImage sprite;
	private int x, y;
	
	public Tile(int x, int y, BufferedImage sprite)
	{
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g)
	{
		g.drawImage(sprite, x, y, null);
	}
}
