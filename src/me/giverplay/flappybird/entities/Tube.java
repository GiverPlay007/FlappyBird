package me.giverplay.flappybird.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.giverplay.flappybird.Game;

public class Tube extends Entity
{
	private BufferedImage sprite;
	private BufferedImage end;
	
	private boolean cima;
	
	public Tube(int x, int y, int speed, int height, boolean cima)
	{
		super(x, y, 32, height, speed, null);
		this.sprite = Game.getGame().getSpritesheet().getSprite(16, 0, 16, 32);
		this.end = Game.getGame().getSpritesheet().getSprite(0, 29, 16, 3);
		
		this.cima = cima;
		
		setDepth(0);
	}
	
	@Override
	public void tick()
	{
		x -= speed;
	}
	
	@Override
	public void render(Graphics g)
	{
		g.drawImage(sprite, getX(), getY(), getWidth(), getHeight(), null);
		g.drawImage(end, getX(), getY() + getHeight() - 6, getWidth(), 6, null);
	}
}
