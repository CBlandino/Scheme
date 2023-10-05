package Lex;

import java.io.IOException;
import java.util.ArrayList;

public class BuiltInIntegertoReal extends FunctionNode
{

	public BuiltInIntegertoReal(String name, ArrayList<VariableNode> parameters, ArrayList<VariableNode> constants,
			ArrayList<VariableNode> variables, ArrayList<StatementNode> statements) 
	{
		super(name, parameters, constants, variables, statements);
		// TODO Auto-generated constructor stub
	}
	
	public BuiltInIntegertoReal()
	{
		
	}
	
	
	public String getName()
	{
		return "IntegerToReal";
	}
	
	/**
	 * Checks if arg is != 1
	 * if arg = 1 then cast it to an int to turn it into an int
	 * then cast it into a float and store it
	 * replace arg 0 with the now real number of arg 0
	 * @param arg
	 * @throws IOException
	 */
	public void execute(ArrayList<IDT> arg) throws IOException
	{
        if (arg.size() != 1 ) 
        {
            throw new IOException("BuiltInIntegertoReal: Invalid size.");
        }
        
        if (arg.get(0)instanceof IntegerDataType)
        {
        	int integerValue = (int) arg.get(0).getType();
            float realValue = (float) integerValue;

            arg.set(0, new RealDataType(realValue));
        }
        else
        	throw new IOException("BuiltInIntegertoReal: DataType must be an integer");
	}
}
