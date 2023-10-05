package Lex;

import java.io.IOException;
import java.util.ArrayList;

public class BuiltInLeft extends FunctionNode{

	public BuiltInLeft(String name, ArrayList<VariableNode> parameters, ArrayList<VariableNode> constants,
			ArrayList<VariableNode> variables, ArrayList<StatementNode> statements) 
	{
		super(name, parameters, constants, variables, statements);
		// TODO Auto-generated constructor stub
	}

	 public BuiltInLeft() 
	 {
		// TODO Auto-generated constructor stub
	 }
	 
	 public String getName()
		{
			return "Left";
		}

	 /**
	  * checks if first arg is string and second is int
	  * now store arg 0 into a string and arg 1 into an int
	  * get the substring of either the min length, that being int or the strings length
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
			        
			        String result = stringArg.substring(0, Math.min(lengthArg, stringArg.length()));
			        
			        arg.set(2, new StringDataType(result));
			 }
			 else
				 throw new IOException("BuiltInLeft: Second arg must be a string");
		 }
		 else
			 throw new IOException("BuiltInLeft: First arg must be a string");
	        
	 }
	 
}
