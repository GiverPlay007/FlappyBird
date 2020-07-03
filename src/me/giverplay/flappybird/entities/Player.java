package me.giverplay.flappybird.entities;

import java.awt.Graphics;

import me.giverplay.flappybird.Game;
import me.giverplay.flappybird.graphics.GraphicsUtils;

public class Player extends Entity
{	
	private Game game;
	
	private boolean jumping = false;
	private boolean subindo = false;
	private boolean dead = false;
	private boolean closingWings = false;
	
	private int subindoFrames = 0;
	private int maxSubindoFrames = 5;
	private int speed = 2;
	private int wingsRotation = 0;
	private int maxWingsFrames = 45;
	
	public Player(int x, int y, int width, int height)
	{
		super(x, y, width, height, 2, null);
		game = Game.getGame();
		
		setDepth(2);
	}
	
	@Override
	public void tick()
	{
		if(!dead)
		{
			if(jumping)
			{
				if(!subindo)
					subindo = true;
				
				subindoFrames = 0;
			}
			
			if(subindo)
			{
				subindoFrames++;
				
				if(subindoFrames >= maxSubindoFrames)
				{
					subindoFrames = 0;
					subindo = false;
				}
				else
				{
					y -= speed + 1;
					
					if(y <= 0)
						handleDeath();
				}
			}
			else 
			{
				y += speed;
				
				if(y >= Game.HEIGHT - 26)
					handleDeath();
			}
		}
		else
		{		
			if(y >= Game.HEIGHT - 26 || y + 4 >= Game.HEIGHT - 26)
			{	
				game.matar();
				return;
			}
			
			if(!game.morreu())
				y += 4;
		}
	}
	
	@Override
	public void render(Graphics g)
	{
		g.drawImage(Entity.SPRITE_PLAYER, getX(), getY(), null);
		
		Graphics wings = Entity.SPRITE_PLAYER.getGraphics();
		
		if(wings == null)
			wings = Entity.SPRITE_PLAYER.createGraphics();
		
		// Recomanda-se fazer isso no Tick, mas eu sou a lei
		wingsRotation += closingWings ? 1 : -1;
		
		if(wingsRotation >= maxWingsFrames)
		{
			closingWings = !closingWings;
		}
		
		wings.drawImage(GraphicsUtils.rotate(Entity.SPRITE_WINGS, wingsRotation), 0, 5, 10, 8, null);
		
		
	}
	
	public void handleDeath()
	{
		this.dead = true;
	}
	
	public boolean isDead()
	{
		return this.dead;
	}
	
	public void handleJump(boolean jumping)
	{
		this.jumping = jumping;
	}
}
