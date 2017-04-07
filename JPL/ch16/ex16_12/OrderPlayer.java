package ex16_12;

import java.io.*;
import java.util.*;
import ex16_12.Game.Mark;

public class OrderPlayer implements StrategyPlayable
{
	List<Integer> list = new ArrayList<>();
	
	public void setStrategy(InputStream in)
	{
		int data;
		
		try
		{
			while((data = in.read()) != -1)
			{
				try
				{
					this.list.add(Integer.parseInt(Character.toString((char)data)));
				}
				catch(NumberFormatException e)
				{
					continue;
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void play(Game game, Mark mark)
	{
		for(int i : this.list)
		{
			if(game.putMark(i % Game.FIELD_SIZE, i / Game.FIELD_SIZE, mark))
			{
				return;
			}
		}
		
		for(int i = 0;
			game.putMark(i % Game.FIELD_SIZE, i / Game.FIELD_SIZE, mark)
			|| game.isFinished(); i++);
	}
}
