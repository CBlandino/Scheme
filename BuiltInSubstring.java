package Lex;

import java.io.IOException;
import java.util.ArrayList;

public class BuiltInSubstring extends FunctionNode{

	
	 public BuiltInSubstring(String name, ArrayList<VariableNode> parameters, ArrayList<VariableNode> constants,
			ArrayList<VariableNode> variables, ArrayList<StatementNode> statements) 
	 {
		super(name, parameters, constants, variables, statements);
		// TODO Auto-generated constructor stub
		
	 }
	 
	 public BuiltInSubstring()
	 {
		 
	 }
	 
	 public String getName()
		{
			return "SubString";
		}
	 
	 public void execute(ArrayList<IDT> arg) throws IOException
	 {
		 if(arg.get(0) instanceof StringDataType)
		 {
			 if(arg.get(1) instanceof IntegerDataType)
			 {
				 if(arg.get(2) instanceof IntegerDataType)
				 {
					 if(arg.get(3) instanceof StringDataType)
					 {
					 
						 	String str = (String) arg.get(0).getType();
					        int startIndex = (int) arg.get(1).getType();
					        int endIndex = (int) arg.get(2).getType();

					        String result = str.substring(startIndex, endIndex);

					        arg.set(3, new StringDataType(result));
						 
						 
					 }
					 else
						 throw new IOException("BuiltSubString: fourth arg must be a string");
				 }
				 else
					 throw new IOException("BuiltSubString: third arg must be a number");
			 }
			 else
				 throw new IOException("BuiltSubString: Second arg must be a number");
		 }
		 else
			 throw new IOException("BuiltSubString: fist arg must be a string");
	 	}
}
