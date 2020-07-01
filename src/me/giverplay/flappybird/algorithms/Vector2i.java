package me.giverplay.flappybird.algorithms;

public class Vector2i
{
	public int x, y;
	
	public Vector2i(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object object)
	{
		Vector2i vec = (Vector2i) object;
		
		return vec.x == this.x && vec.y == this.y;
	}
}
