package me.giverplay.flappybird;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Listeners implements KeyListener
{
	private final Game game;
	
	public Listeners(Game game)
	{
		this.game = game;
		this.game.addKeyListener(this);
	}
	
	@Override
	public void keyPressed(KeyEvent event)
	{
		if(!game.isDead() && !game.winnowed())
		{
			if(event.getKeyCode() == KeyEvent.VK_SPACE)
			{
				game.getPlayer().handleJump(true);
			}
		}
		else
		{
			if(event.getKeyCode() == KeyEvent.VK_ENTER)
			{
				Game.handleRestart();
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent event)
	{
		if(event.getKeyCode() == KeyEvent.VK_SPACE)
		{
			game.getPlayer().handleJump(false);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0)	{ }
}
