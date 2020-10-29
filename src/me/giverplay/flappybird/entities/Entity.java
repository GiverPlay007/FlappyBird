package me.giverplay.flappybird.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import me.giverplay.flappybird.Game;

import static me.giverplay.flappybird.Game.TILE_SIZE;

public abstract class Entity
{
	protected static final Game game = Game.getGame();
	
	public static final BufferedImage SPRITE_PLAYER = game.getSpritesheet().getSprite(0, 0, TILE_SIZE, TILE_SIZE);
	public static final BufferedImage SPRITE_WINGS = game.getSpritesheet().getSprite(0, 16, 8, 8);
	public static final BufferedImage TUBE = game.getSpritesheet().getSprite(16, 0, 16, 32);
	public static final BufferedImage TUBE_GOAL = game.getSpritesheet().getSprite(0, 29, 16, 3);
	
	private final int height;
	private final int width;
	
	protected double x;
	protected double y;
	protected double speed;
	
	private int depth;
	
	public Entity(double x, double y, int width, int height, double speed)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.depth = 0;
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public void setDepth(int toSet)
	{
		this.depth = toSet;
	}
	
	public int getX()
	{
		return (int) this.x;
	}
	
	public int getY()
	{
		return (int) this.y;
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public int getDepth()
	{
		return this.depth;
	}
	
	public boolean isColliding(Player e2)
	{
		Rectangle e1m = new Rectangle(getX(), getY(), getWidth(), getHeight());
		Rectangle e2m = new Rectangle(e2.getX(), e2.getY() - 2, e2.getWidth() + 2, e2.getHeight());
		
		return e1m.intersects(e2m);
	}
	
	public void destroy()
	{
		Game.getGame().getEntities().remove(this);
	}
	
	public static Comparator<Entity> sortDepth = Comparator.comparingInt(Entity::getDepth);
}
