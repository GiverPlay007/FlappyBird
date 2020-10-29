package me.giverplay.flappybird.entities;

import java.awt.Graphics;

import me.giverplay.flappybird.Game;

public class Player extends Entity
{
	private boolean jumping = false;
	private boolean goingUp = false;
	private boolean dead = false;
	private boolean closingWings = false;
	
	private int upFrames = 0;
	private int wingsHeight = 5;
	
	public Player(int x, int y, int width, int height)
	{
		super(x, y, width, height, 2);
		setDepth(2);
	}
	
	@Override
	public void tick()
	{
		if(!dead)
		{
			if(jumping)
			{
				if(!goingUp)
					goingUp = true;
				
				upFrames = 0;
			}
			
			if(goingUp)
			{
				upFrames++;
				
				if(upFrames >= 5)
				{
					upFrames = 0;
					goingUp = false;
				}
				else
				{
					if(y > 0)
						y -= speed + 1;
				}
			}
			else 
			{
				y += speed;
				
				if(y >= Game.HEIGHT - Game.FLOOR_OFFSET)
				{
					handleDeath();
				}
			}
		}
		else
		{		
			if(y >= Game.HEIGHT - Game.FLOOR_OFFSET || y + 4 >= Game.HEIGHT - Game.FLOOR_OFFSET)
			{	
				game.kill();
				return;
			}
			
			if(!game.isDead())
				y += 4;
		}
	}
	
	@Override
	public void render(Graphics g)
	{
		wingsHeight += closingWings ? -1 : 1;
		
		if(wingsHeight >= 12 || wingsHeight <= 5)
		{
			closingWings = !closingWings;
		}
		
		g.drawImage(SPRITE_PLAYER, getX(), getY(), null);		
		g.drawImage(SPRITE_WINGS, getX() - 1, getY() + 5, 10, wingsHeight, null);
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
