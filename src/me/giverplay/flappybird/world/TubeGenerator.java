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
		time++;
		
		if(time >= maxTime)
		{
			time = 0;
			
			int height = random.nextInt(game.HEIGHT / 2 + game.HEIGHT / 4 - 40) + 40;
			
			game.getEntities().add(new Tube(game.WIDTH, 0, 1, height, true));
		}
	}
}
