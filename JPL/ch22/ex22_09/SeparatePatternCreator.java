package ex22_09;

public interface SeparatePatternCreator
{
	String create(int cellNum);
}

enum CSVRegexPattern implements SeparatePatternCreator
{
	PATTERN1
	{
		@Override
		public String create(int cellNum)
		{
			String pat = "^(.*)";
			
			for(int i = 1; i < cellNum; i++)
			{
				pat += ",(.*)";
			}

			pat += "$";
			
			return pat;
		}
	},
	
	PATTERN2
	{
		@Override
		public String create(int cellNum)
		{
			String pat = "^([^,]*)";
			
			for(int i = 1; i < cellNum; i++)
			{
				pat += ",([^,]*)";
			}

			pat += "$";
			
			return pat;
		}
	},
	
	PATTERN3
	{
		@Override
		public String create(int cellNum)
		{
			String pat = "^(.+?)";
			
			for(int i = 1; i < cellNum; i++)
			{
				pat += ",(.+?)";
			}

			pat += "$";
			
			return pat;
		}
	},
	
	PATTERN4
	{
		@Override
		public String create(int cellNum)
		{
			String pat = "^(.+)";
			
			for(int i = 1; i < cellNum; i++)
			{
				pat += ",(.+)";
			}

			pat += "$";
			
			return pat;
		}
	};
}
