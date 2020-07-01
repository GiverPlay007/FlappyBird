package me.giverplay.flappybird.entities;

import me.giverplay.flappybird.Game;

public class Fruta extends Entity implements Collectible
{
	public Fruta(int x, int y)
	{
		super(x, y, 16, 16, 1, Entity.SPRITE_FRUTA[random.nextInt(4)]);
	}
	
	public void collect() 
	{
		Game.getGame().getPlayer().addFruit();
		destroy();
	}
	
	@Override
	public void destroy()
	{
		Game.getGame().getFruits().remove(this);
	}
}
