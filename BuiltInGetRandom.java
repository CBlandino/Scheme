package Lex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class BuiltInGetRandom extends FunctionNode
{
	public BuiltInGetRandom(String name, ArrayList<VariableNode> parameters, ArrayList<VariableNode> constants,
			ArrayList<VariableNode> variables, ArrayList<StatementNode> statements) 
	{
		super(name, parameters, constants, variables, statements);
		// TODO Auto-generated constructor stub
	}

	public BuiltInGetRandom() 
	{
		
		// TODO Auto-generated constructor stub
	}

	public String getName()
	{
		return "GetRandom";
	}
	
	public boolean isVariadic() 
	{
        return false;
    }

	/**
	 * Checks if arg is != 1
	 * if arg = 1 then check if it is of a type int
	 * then generate a random number and store it into an int
	 * replace arg 0 with the now random number of arg 0
	 * @param args
	 * @throws IOException
	 */
    public void execute(ArrayList<IDT> args) throws IOException
    {
        if (args.size() == 1)
        {
            IDT arg1 = args.get(0);
            
            if (arg1 instanceof IntegerDataType)
            {           	
                Random rand = new Random();
                int result = rand.nextInt();
                
                IDT resultInt = new IntegerDataType(result);
                args.set(0,resultInt);
            }
        }
        
      
        else
        	throw new IOException("To get a random number you must have one (max)");
    }
}

