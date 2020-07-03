package me.giverplay.flappybird.world;

import java.util.Random;

import me.giverplay.flappybird.Game;
import me.giverplay.flappybird.entities.Tube;

public class TubeGenerator
{
	private static Random random = new Random();
	
	private Game game;
	
	private int time = 0;
	private int maxTime = 90;
	
	public TubeGenerator(Game game)
	{
		this.game = game;
	}
	
	public void tick()
	{
		if(game.getPlayer().isDead())
			return;
		
		time++;
		
		if(time >= maxTime)
		{
			time = 0;
			
			int height = random.nextInt((Game.HEIGHT - 12) / 2 - 40) + 40;
			
			game.getEntities().add(new Tube(Game.WIDTH, 0, 1, height, true));
			game.getEntities().add(new Tube(Game.WIDTH, height + 70, 1, (Game.HEIGHT - 12) - (height + 70), false));
		}
	}
}
