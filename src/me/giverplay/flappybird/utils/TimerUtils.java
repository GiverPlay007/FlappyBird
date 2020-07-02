package me.giverplay.flappybird.utils;

import java.util.Timer;
import java.util.TimerTask;

public class TimerUtils
{
	public static void runLater(int segundos, TimerTask task)
	{
		new Timer().schedule(task, segundos * 1000);
	}
}
