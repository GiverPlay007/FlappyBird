package me.giverplay.flappybird.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import me.giverplay.flappybird.Game;
import me.giverplay.flappybird.entities.Enemy;
import me.giverplay.flappybird.entities.Fruta;
import me.giverplay.flappybird.utils.Cores;

public class World
{
	public static final int TILE_SIZE = 16;
	
	private Game game;
	
	private int width;
	private int height;
	
	private static Tile[] tiles;
	
	public World(String path)
	{
		game = Game.getGame();
		
		try
		{
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			
			width = map.getWidth();
			height = map.getHeight();
			
			int lenght = width * height;
			int[] pixels = new int[lenght];
			
			tiles = new Tile[lenght];
			
			map.getRGB(0, 0, width, height, pixels, 0, width);
			
			for(int xx = 0; xx < width; xx++)
			{
				for(int yy = 0; yy < height; yy++)
				{
					int index = xx + (yy * width);
					
					tiles[index] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE);
					
					switch (pixels[index])
					{	
						case Cores.MAPA_PAREDE:
							tiles[index] = new WallTile(xx * TILE_SIZE, yy * TILE_SIZE);
							break;
							
						case Cores.MAPA_GHOST:
							game.getEntities().add(new Enemy(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE));
							break;
						
						case Cores.MAPA_FRUTA:
							game.getFruits().add(new Fruta(xx * TILE_SIZE, yy * TILE_SIZE));
							break;
							
						case Cores.MAPA_JOGADOR:
							game.getPlayer().setX(xx * TILE_SIZE);
							game.getPlayer().setY(yy * TILE_SIZE);
							break;
							
						default:
							tiles[index] = new Tile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_GRAMA);
							break;
					}
				}
			}
			
		} catch (IOException e)
		{
			System.out.println("Falha ao ler o mapa");
		}
	}
	
	public void render(Graphics g)
	{
		int xf = (Game.WIDTH * Game.SCALE >> 4);
		int yf = (Game.HEIGHT * Game.SCALE >> 4);
				
				
				for(int xx = 0; xx <= xf; xx++)
				{
					for(int yy = 0; yy <= yf; yy++)
					{
						
						if(xx < 0 || yy < 0 || xx >= width || yy >= height)
							continue;
						
						tiles[xx + (yy * width)].render(g);
					}
				}
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public Tile[] getTiles()
	{
		return tiles;
	}
	
	public static boolean canMove(int xn, int yn)
	{
		int x1 = xn / TILE_SIZE;
		int y1 = yn / TILE_SIZE;
		
		int x2 = (xn + TILE_SIZE -1) / TILE_SIZE;
		int y2 = yn / TILE_SIZE;
		
		int x3 = xn / TILE_SIZE;
		int y3 = (yn + TILE_SIZE -1) / TILE_SIZE;
		
		int x4 = (xn + TILE_SIZE -1) / TILE_SIZE;
		int y4 = (yn + TILE_SIZE -1) / TILE_SIZE;
		
		World world = Game.getGame().getWorld();
		
		int index1 = x1 + (y1 * world.getWidth());
		int index2 = x2 + (y2 * world.getWidth());
		int index3 = x3 + (y3 * world.getWidth());
		int index4 = x4 + (y4 * world.getWidth());
		
		return !(tiles[index1] instanceof WallTile 
				|| tiles[index2] instanceof WallTile
				|| tiles[index3] instanceof WallTile
				|| tiles[index4] instanceof WallTile);
	}
}
