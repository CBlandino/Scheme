package Lex;

import java.io.IOException;
import java.util.ArrayList;

public class BuiltInRealtoInteger extends FunctionNode
{

	public BuiltInRealtoInteger(String name, ArrayList<VariableNode> parameters, ArrayList<VariableNode> constants,
			ArrayList<VariableNode> variables, ArrayList<StatementNode> statements) 
	{
		super(name, parameters, constants, variables, statements);
		// TODO Auto-generated constructor stub
	}
	
	public BuiltInRealtoInteger()
	{
		
	}

	public String getName()
	{
		return "RealToInteger";
	}
	
	/**
	 *  Checks if arg is != 1
	 * if arg = 1 then cast it to an flaot to turn it into an float
	 * then cast it into a int and store it
	 * replace arg 0 with the now int number of arg 0
	 * @param arg
	 * @throws IOException
	 */
	public void execute(ArrayList<IDT> arg) throws IOException
	{
        if (arg.size() != 1 ) 
        {
            throw new IOException("BuiltInRealtoInteger: Invalid size.");
        }
        
        if (arg.get(0)instanceof RealDataType)
        {
        	 float realValue = (float) arg.get(0).getType();
             int integerValue = (int) realValue ;

            arg.set(0, new IntegerDataType(integerValue));
        }
        else
        	throw new IOException("BuiltInRealtoInteger: DataType must be a real");
	}
}

