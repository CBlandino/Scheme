package Lex;

import java.io.IOException;
import java.util.ArrayList;

public class BuiltInRight extends FunctionNode
{

	public BuiltInRight(String name, ArrayList<VariableNode> parameters, ArrayList<VariableNode> constants,
			ArrayList<VariableNode> variables, ArrayList<StatementNode> statements)
	{
		super(name, parameters, constants, variables, statements);
		// TODO Auto-generated constructor stub
	}

	 public BuiltInRight()
	 {
		// TODO Auto-generated constructor stub
	 }
	 
	 public String getName()
		{
			return "Right";
		}

	 /**
	  * checks if the first arg is string
	  * checks if the second arg is an int
	  * now store the arg 0 into a string variable, arg 1 into int, and get the length of the now string arg 0
	  * if the string length is less than the int given throw an error
	  * otherwise get the substring of string length - int and strore it into string
	  * @param arg
	  * @throws IOException
	  */
	public void execute(ArrayList<IDT> arg) throws IOException 
	 {
		 if(arg.get(0) instanceof StringDataType)
		 {
			 if(arg.get(1) instanceof IntegerDataType)
			 {
				 String stringArg = (String) arg.get(0).getType();
			     int lengthArg = (int) arg.get(1).getType();
			     int stringLength = stringArg.length();
			        
			        if (stringLength < lengthArg)
			        {
			            throw new IOException("The input string is shorter than the requested number of characters.");
			        }

			     String result = stringArg.substring(stringLength - lengthArg); 
			     arg.set(2, new StringDataType(result));
			 }
			 else
				 throw new IOException("BuiltInLeft: Second arg must be a string");
		}
			 	 
		 else 
			 throw new IOException("BuiltInLeft: First arg must be a string");
		 
	        
	 }
	
}
