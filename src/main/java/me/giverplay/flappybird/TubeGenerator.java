package me.giverplay.flappybird;

import java.util.Random;
import me.giverplay.flappybird.entities.Tube;

public class TubeGenerator
{
	private static final Random RANDOM = new Random();
	
	private final Game game;
	
	private int time = 0;
	
	public TubeGenerator(Game game)
	{
		this.game = game;
	}
	
	public void tick()
	{
		if(game.getPlayer().isDead())
			return;
		
		time++;
		
		if(time >= 90)
		{
			time = 0;
			
			int height = RANDOM.nextInt((Game.HEIGHT - 12) / 2 - 40) + 40;
			
			game.getEntities().add(new Tube(Game.WIDTH, 0, 1, height, true));
			game.getEntities().add(new Tube(Game.WIDTH, height + 70, 1, (Game.HEIGHT - 12) - (height + 70), false));
		}
	}
}
