package me.giverplay.flappybird.graphics;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class FontUtils
{
	private static final InputStream stream = FontUtils.class.getResourceAsStream("/Font.ttf");
	private static Font font;
	
	static
	{
		try
		{
			font = Font.createFont(Font.TRUETYPE_FONT, stream);
		} 
		catch (FontFormatException e)
		{
			System.out.println("Falha no formato de fonte, inicializando com fonte padrão");
		} 
		catch (IOException e)
		{
			System.out.println("Falha na leitura do arquivo de fonte, inicializando com fonte padrão");
		}
	}
	
	public static Font getFont(int size, int style)
	{
		return (font != null ? font.deriveFont((float) size).deriveFont(style) : new Font("arial", style, size));
	}
}
