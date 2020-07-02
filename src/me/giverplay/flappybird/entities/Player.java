package me.giverplay.flappybird.entities;

import java.awt.Graphics;

import me.giverplay.flappybird.Game;

public class Player extends Entity
{	
	private Game game;
	
	private boolean jumping = false;
	
	public Player(int x, int y, int width, int height)
	{
		super(x, y, width, height, 2, null);
		game = Game.getGame();
		
		setDepth(2);
	}
	
	@Override
	public void tick()
	{
		y += jumping ? -2 : 2;
		
	}
	
	@Override
	public void render(Graphics g)
	{
		g.drawImage(Entity.SPRITE_PLAYER, getX(), getY(), null);
	}
	
	public void handleJump(boolean jumping)
	{
		this.jumping = jumping;
	}
}
