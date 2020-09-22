package me.giverplay.flappybird.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.giverplay.flappybird.Game;

public class Tube extends Entity
{
	private BufferedImage sprite;
	private BufferedImage end;
	
	private Game game;
	
	private boolean cima;
	private boolean jaDeuPonto = false;
	
	public Tube(int x, int y, int speed, int height, boolean cima)
	{
		super(x, y, 32, height, speed, null);
		this.game = Game.getGame();
		
		this.sprite = game.getSpritesheet().getSprite(16, 0, 16, 32);
		this.end = game.getSpritesheet().getSprite(0, 29, 16, 3);
		
		this.cima = cima;
		
		setDepth(0);
	}
	
	@Override
	public void tick()
	{
		if(game.getPlayer().isDead())
			return;
		
		x -= speed;
		
		if(isColliding(this, game.getPlayer()))
			game.getPlayer().handleDeath();
		
		if(getX() + getWidth() < game.getPlayer().getX() && !jaDeuPonto && cima)
		{
			game.addScore(1);
			jaDeuPonto = true;
		}
		
		if(getX() < -getWidth())
			destroy();
	}
	
	@Override
	public void render(Graphics g)
	{
		g.drawImage(sprite, getX(), getY(), getWidth(), getHeight(), null);
		g.drawImage(end, getX(), getY() + (cima ? getHeight() - 6 : 0), getWidth(), 6, null);		
	}
}
