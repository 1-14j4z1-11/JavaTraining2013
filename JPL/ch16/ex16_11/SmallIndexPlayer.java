package ex16_11;

import ex16_11.Game.Mark;

public class SmallIndexPlayer implements Playable
{
	@Override
	public void play(Game game, Mark mark)
	{
		for(int i = 0; !game.putMark(i % Game.FIELD_SIZE, i / Game.FIELD_SIZE, mark); i++);
	}
}
