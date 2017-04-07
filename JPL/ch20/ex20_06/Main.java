package ex20_06;

import java.io.*;
import java.util.*;

public class Main
{
	private static final String[] NAMES = new String[]
	{
		"A", "B", "C",
	};
	private static final Operator[] OPERATORS = new Operator[]
	{
		new AddOperator(),
		new SubOperator(),
		new AssignOperator(),
	};
	private static final double DEF_VALUE = 0.0;
	
	public static void main(String[] args)
	{
		if(args.length < 1)
		{
			return;
		}
		
		HashMap<String, Double> nameMap = new HashMap<String, Double>();
		HashMap<String, Operator> opMap = new HashMap<String, Operator>();
		
		for(String s : NAMES)
		{
			nameMap.put(s, DEF_VALUE);
		}

		for(Operator o : OPERATORS)
		{
			opMap.put(o.getKeyString(), o);
		}
		
		FileReader reader = null;
		
		try
		{
			reader = new FileReader(args[0]);
			StreamTokenizer in = new StreamTokenizer(reader);
			String targetName = null;
			Operator targetOp = null;
			boolean valid = true;

			in.wordChars('!', 'Z');
			in.wordChars('0', '9');
			in.wordChars('.', '.');
			in.wordChars('+', '+');
			in.ordinaryChars('-', '-');
			in.wordChars('-', '-');
			in.wordChars('=', '=');
			in.wordChars(128 + 32, 255);
			in.whitespaceChars(0, ' ');
			
			while(valid && (in.nextToken() != StreamTokenizer.TT_EOF))
			{
				switch(in.ttype)
				{
					case StreamTokenizer.TT_WORD:
						if(targetName != null)
						{
							if((targetOp == null) && (opMap.containsKey(in.sval)))
							{
								targetOp = opMap.get(in.sval);
							}
							else
							{
								valid = false;
							}
						}
						else
						{
							if(nameMap.containsKey(in.sval))
							{
								targetName = in.sval;
							}
							else
							{
								valid = false;
							}
						}
						break;
					
					case StreamTokenizer.TT_NUMBER:
						if((targetName != null) && (targetOp != null))
						{
							double value = targetOp.operate(nameMap.get(targetName), in.nval);
							nameMap.put(targetName, value);
							
							targetName = null;
							targetOp = null;
						}
						else
						{
							valid = false;
						}
						break;
						
					default:
						break;
				}
			}
			
			if(!valid)
			{
				System.out.println("Syntax Error.");
			}
			else
			{
				Set<String> keys = nameMap.keySet();
				
				for(String key : keys)
				{
					System.out.printf("%s : %.3f%n", key, nameMap.get(key));
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(reader != null)
			{
				try
				{
					reader.close();
				}
				catch(IOException e)
				{ }
			}
		}
	}
}

abstract class Operator
{
	abstract public String getKeyString();
	abstract public double operate(double src, double operand);
	
	@Override
	public String toString()
	{
		return getKeyString();
	}
}

class AddOperator extends Operator
{
	@Override
	public String getKeyString()
	{
		return "+";
	}
	
	@Override
	public double operate(double src, double operand)
	{
		return src + operand;
	}
}

class SubOperator extends Operator
{
	@Override
	public String getKeyString()
	{
		return "-";
	}
	
	@Override
	public double operate(double src, double operand)
	{
		return src - operand;
	}
}

class AssignOperator extends Operator
{
	@Override
	public String getKeyString()
	{
		return "=";
	}
	
	@Override
	public double operate(double src, double operand)
	{
		return operand;
	}
}