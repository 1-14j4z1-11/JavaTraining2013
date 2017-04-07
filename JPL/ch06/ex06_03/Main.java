package ex06_03;

public class Main
{
	public static void main(String[] args)
	{
		ConcreteVerbose verbose = new ConcreteVerbose();
		verbose.setVerbosity(MessageLevel.SILENT);
		System.out.println(verbose.getVerbosity());
	}
}

class ConcreteVerbose implements Verbose
{
	private MessageLevel level;
	
	@Override
	public void setVerbosity(MessageLevel level)
	{
		this.level = level;
	}

	@Override
	public MessageLevel getVerbosity()
	{
		return level;
	}
}