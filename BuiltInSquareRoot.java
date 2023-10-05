package Lex;

import java.util.ArrayList;
import java.lang.Math;

public class BuiltInSquareRoot extends FunctionNode
{

	

	public BuiltInSquareRoot() 
	{
		// TODO Auto-generated constructor stub
	}

	public String getName()
	{
		return "SquareRoot";
	}
	
	public boolean isVariadic() 
	{
        return false;
    }
	
	/**
	 * Checks if the params are != 1, 
	 * if they are = 1 and is an instance of it square roots paramater 0 
	 * and sets it to the squarerooted number
	 * @param parameters
	 */
	public void execute(ArrayList<IDT> parameters)
	{
		
		IDT param = parameters.get(0);
		
		if (parameters.size() != 1) 
		{
            throw new RuntimeException("sqrt() function expects exactly one argument.");
        }

		else if (!(param instanceof RealDataType))
		{
	            throw new RuntimeException("sqrt() function expects a real number as an argument.");
	    }
		
		else
		{
			RealDataType result = new  RealDataType((float) Math.sqrt(((RealDataType) param).getFloat()));
			parameters.set(0, result);
		}
	}
	
}
