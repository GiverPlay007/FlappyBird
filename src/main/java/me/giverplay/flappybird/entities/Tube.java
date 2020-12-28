package me.giverplay.flappybird.entities;

import java.awt.Graphics;

public class Tube extends Entity
{
	private final boolean up;
	private boolean pointed = false;
	
	public Tube(int x, int y, int speed, int height, boolean up)
	{
		super(x, y, 32, height, speed);
		
		this.up = up;
		
		setDepth(0);
	}
	
	@Override
	public void tick()
	{
		if(game.getPlayer().isDead())
		{
			return;
		}
		
		x -= speed;
		
		if(isColliding(game.getPlayer()))
		{
			game.getPlayer().handleDeath();
		}
		
		if(getX() + getWidth() < game.getPlayer().getX() && !pointed && up)
		{
			game.addScore();
			pointed = true;
		}
		
		if(getX() < -getWidth())
		{
			destroy();
		}
	}
	
	@Override
	public void render(Graphics g)
	{
		g.drawImage(TUBE, getX(), getY(), getWidth(), getHeight(), null);
		g.drawImage(TUBE_GOAL, getX(), getY() + (up ? getHeight() - 6 : 0), getWidth(), 6, null);
	}
}
