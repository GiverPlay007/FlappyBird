package me.giverplay.flappybird.entities;

import static me.giverplay.flappybird.world.World.TILE_SIZE;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import me.giverplay.flappybird.Game;
import me.giverplay.flappybird.algorithms.Node;
import me.giverplay.flappybird.algorithms.Vector2i;
import me.giverplay.flappybird.world.World;

public class Entity
{
	public static final BufferedImage[] SPRITE_PLAYER;
	public static final BufferedImage[] SPRITE_GHOST;
	public static final BufferedImage[] SPRITE_FRUTA;
	
	public static final BufferedImage GHOST = Game.getGame().getSpritesheet().getSprite(TILE_SIZE * 2, 0, TILE_SIZE, TILE_SIZE);
	
	static
	{
		SPRITE_GHOST = new BufferedImage[4];
		SPRITE_FRUTA = new BufferedImage[4];
		SPRITE_PLAYER = new BufferedImage[4];
		
		for(int i = 0; i < 4; i++)
		{
			SPRITE_PLAYER[i] = Game.getGame().getSpritesheet().getSprite(i * TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE);
			SPRITE_FRUTA[i] = Game.getGame().getSpritesheet().getSprite(i * TILE_SIZE, 2 * TILE_SIZE, TILE_SIZE, TILE_SIZE);
			SPRITE_GHOST[i] = Game.getGame().getSpritesheet().getSprite(i * TILE_SIZE, 3 * TILE_SIZE, TILE_SIZE, TILE_SIZE);
		}
	}
	
	private static Game game = Game.getGame();
	
	protected List<Node> path;
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
	
	public void destroy()
	{
		game.getEntities().remove(this);
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
	
	public void followPath(List<Node> path)
	{
		if(path != null)
		{
			if(path.size() > 0)
			{
				Vector2i target = path.get(path.size() - 1).getTile();
				
				if(x < target.x * World.TILE_SIZE)
				{
					x++;
				}
				else if(x > target.x * World.TILE_SIZE)
				{
					x--;
				}
				
				if(y < target.y * World.TILE_SIZE)
				{
					y++;
				}
				else if(y > target.y * World.TILE_SIZE)
				{
					y--;
				}
				
				if(x == target.x * World.TILE_SIZE && y == target.y * World.TILE_SIZE)
					path.remove(path.size() -1);
			}
		}
	}
	
	public double pointDistance(int x1, int y1, int x2, int y2)
	{
		return Math.sqrt((x2 - x1) * (x2 - x1) + ((y2 - y1) * (y2 - y1)));
	}
	
	public static boolean isCollifingEntity(Entity e1, Entity e2)
	{
		Rectangle e1m = new Rectangle(e1.getX(), e1.getY(), e1.getWidth(), e1.getHeight());
		Rectangle e2m = new Rectangle(e2.getX(), e2.getY(), e2.getWidth(), e2.getHeight());
		
		return e1m.intersects(e2m);
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
