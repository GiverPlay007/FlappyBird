package me.giverplay.flappybird.entities;

import static me.giverplay.flappybird.Game.TILE_SIZE;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.Random;

import me.giverplay.flappybird.Game;

public class Entity
{
	public static final BufferedImage SPRITE_PLAYER = Game.getGame().getSpritesheet().getSprite(0, 0, TILE_SIZE, TILE_SIZE);
	public static final BufferedImage SPRITE_WINGS = Game.getGame().getSpritesheet().getSprite(0, 16, 8, 8);
	
	protected static Random random = new Random();
	
	protected double x;
	protected double y;
	protected double speed;
	
	private int width;
	private int height;
	private int depth;
	
	private BufferedImage sprite;
	
	public Entity(double x, double y, int width, int height, double speed, BufferedImage sprite)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.depth = 0;
		
		this.sprite = sprite;
	}
	
	public void tick()
	{
		
	}
	
	public void render(Graphics g)
	{
		g.drawImage(sprite, getX(), getY(), null);
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public void setDepth(int toSet)
	{
		this.depth = toSet;
	}
	
	public void moveX(double d)
	{
		x += d;
	}
	
	public void moveY(double d)
	{
		y += d;
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
	
	public BufferedImage getSprite()
	{
		return this.sprite;
	}
	
	public double pointDistance(int x1, int y1, int x2, int y2)
	{
		return Math.sqrt((x2 - x1) * (x2 - x1) + ((y2 - y1) * (y2 - y1)));
	}
	
	public static boolean isCollifingPlayer(Entity e1, Player e2)
	{
		Rectangle e1m = new Rectangle(e1.getX(), e1.getY(), e1.getWidth(), e1.getHeight());
		Rectangle e2m = new Rectangle(e2.getX(), e2.getY() - 2, e2.getWidth() + 2, e2.getHeight());
		
		return e1m.intersects(e2m);
	}
	
	public void destroy()
	{
		Game.getGame().getEntities().remove(this);
	}
	
	public static Comparator<Entity> sortDepth = new Comparator<Entity>()
	{
		@Override
		public int compare(Entity e0, Entity e1)
		{
			return (e1.getDepth() < e0.getDepth() ? +1 : e1.getDepth() > e0.getDepth() ? -1 : 0);
		}
	};
}
