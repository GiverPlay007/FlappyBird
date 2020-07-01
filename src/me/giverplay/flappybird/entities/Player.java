package me.giverplay.flappybird.entities;

import static me.giverplay.flappybird.world.World.canMove;

import java.awt.Graphics;

import me.giverplay.flappybird.Game;
import me.giverplay.flappybird.graphics.GraphicsUtils;

public class Player extends Entity
{	
	private static final int DIR_RIGHT = 0;
	private static final int DIR_DOWN = 90;
	private static final int DIR_LEFT = 180;
	private static final int DIR_UP = 270;
	
	private static final int MAX_FRAMES_ANIM = 5;
	
	private boolean up, down, left, right;
	
	private boolean damaged = false;
	private boolean isJumping = false;
	private boolean fechandoBoca = false;
	private boolean canDamage = false;
	
	private int undamageable = 0;
	private int maxVida = 5;
	private int vida = 5;
	private int fruits = 0;
	private int anim = 0;
	private int anim_frames = 0;
	private int dir = 0;
	
	private Game game;
	
	public Player(int x, int y, int width, int height)
	{
		super(x, y, width, height, 2, null);
		game = Game.getGame();
		
		setDepth(2);
	}
	
	@Override
	public void tick()
	{
		if(vida == 0)
		{
			game.matar();
			return;
		}
		
		if(!canDamage)
		{
			undamageable++;
			
			if(undamageable >= 30)
			{
				undamageable = 0;
				canDamage = true;
			}
		}
		
		if(!(right && left))
		{
			if(right)
			{
				if(canMove((int) (x + speed), getY())) moveX(speed);
			}
			else if(left)
			{
				if(canMove((int) (x - speed), getY())) moveX(-speed);
			}
		}
		
		if(!(up && down))
		{
			if(up)
			{
				if(canMove(getX(), (int) (y - speed))) moveY(-speed);
			}
			else if(down)
			{
				if(canMove(getX(), (int) (y + speed))) moveY(speed);
			}
		}
		
		anim_frames++;
		
		if(anim_frames >= MAX_FRAMES_ANIM)
		{
			anim_frames = 0;
			
			if(!fechandoBoca)
				anim++;
			else
				anim--;
			
			if(anim >= Entity.SPRITE_PLAYER.length)
			{
				anim--;
				fechandoBoca = !fechandoBoca;
			}
			else if(anim < 0)
			{
				anim++;
				fechandoBoca = !fechandoBoca;
			}
		}
		
		checkItems();
	}
	
	@Override
	public void render(Graphics g)
	{
		g.drawImage(GraphicsUtils.rotate(Entity.SPRITE_PLAYER[anim], dir), getX(), getY(), null);
	}
	
	public boolean walkingRight()
	{
		return this.right;
	}
	
	public boolean walkingLeft()
	{
		return this.left;
	}
	
	public boolean walkingDown()
	{
		return this.down;
	}
	
	public boolean walkingUp()
	{
		return this.up;
	}
	
	public void setWalkingRight(boolean walking)
	{
		this.right = walking;
		this.dir = DIR_RIGHT;
		
		if(!walking && left)
			dir = DIR_LEFT;
	}
	
	public void setWalkingLeft(boolean walking)
	{
		this.left = walking;
		this.dir = DIR_LEFT;
		
		if(!walking && right)
			dir = DIR_RIGHT;
	}
	
	public void setWalkingUp(boolean walking)
	{
		this.up = walking;
		this.dir = DIR_UP;
		
		if(!walking && down)
			dir = DIR_DOWN;
	}
	
	public void setWalkingDown(boolean walking)
	{
		this.down = walking;
		this.dir = DIR_DOWN;
		
		if(!walking && up)
			dir = DIR_UP;
	}
	
	public int getLife()
	{
		return vida;
	}
	
	public void modifyLife(int toModify)
	{
		vida += toModify;
		
		if (vida < 0)
			vida = 0;
		if (vida > maxVida)
			vida = maxVida;
	}
	
	public int getMaxLife()
	{
		return this.maxVida;
	}
	
	public boolean isDamaged()
	{
		return this.damaged;
	}
	
	public void setDamaged(boolean toDamage)
	{
		if (toDamage && isJumping)
			return;
		
		this.damaged = toDamage;
	}
	
	public void handleJump()
	{
		if (!isJumping)
		{
			
		}
	}
	
	public void damage()
	{
		if(!canDamage)
			return;
		
		canDamage = false;
		
		vida--;
		
		if(vida < 0)
			vida = 0;
	}
	
	public boolean isJumping()
	{
		return this.isJumping;
	}
	
	public void checkItems()
	{
		for (int i = 0; i < game.getFruits().size(); i++)
		{
			Entity entity = game.getFruits().get(i);
			
			if (isCollifingEntity(this, entity))
			{
				((Collectible) entity).collect();
			}
		}
	}
	
	public boolean canBeDamaged()
	{
		return this.canDamage;
	}
	
	public void addFruit()
	{
		fruits++;
	}
	
	public int getFruits()
	{
		return this.fruits;
	}
}
