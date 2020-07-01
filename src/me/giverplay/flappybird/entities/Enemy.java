package me.giverplay.flappybird.entities;

import java.awt.Graphics;

import me.giverplay.flappybird.Game;
import me.giverplay.flappybird.algorithms.AStar;
import me.giverplay.flappybird.algorithms.Vector2i;

public class Enemy extends Entity
{
	private int life = 10;
	private int ghostF = 0;
	private int mGhostF = random.nextInt(60 * 5 - 60 * 3) + 60 * 3;
	
	private boolean ghostM = false;
	
	private Player player;
	private Game game;
	
	public Enemy(double x, double y, int width, int height)
	{
		super(x, y, width, height, 1, SPRITE_GHOST[random.nextInt(4)]);
		
		game = Game.getGame();
		player = game.getPlayer();
		
		setDepth(1);
	}
	
	@Override
	public void tick()
	{
		ghostF++;
		
		if(ghostF >= mGhostF)
		{
			mGhostF = random.nextInt(60 * 5 - 60 * 3) + 60 * 3;
			ghostF = 0;
			ghostM = !ghostM;
		}
		
		if(isCollifingEntity(this, game.getPlayer()))
		{
			player.damage();
		}
		
		if(ghostM)
			return;
		
		if(path == null || path.size() == 0)
		{
			path = AStar.findPath(game.getWorld(), new Vector2i((int) (getX() / 16), (int) (getY() / 16)), new Vector2i((int) (player.getX() / 16), (int) (player.getY() / 16)));
		}
		
		followPath(path);
	}
	
	@Override
	public void render(Graphics g)
	{
		super.render(g);
		
		if(ghostM)
			g.drawImage(GHOST, getX(), getY(), null);
	}
	
	public int getLife()
	{
		return this.life;
	}
	
	public void damage(int toDamage)
	{
		life += toDamage;
		
		if(life < 0) life = 0;
		if(life > 10) life = 10;
	}
}
