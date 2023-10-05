package Lex;

import java.util.ArrayList;

public class BuiltInWrite extends FunctionNode 
{

	public BuiltInWrite() 
	{}
	
			
			
	public String getName()
	{
		return "Write";
	}

	public boolean isVardic() 
	{
		return true;
	}
	
	 public void execute(ArrayList<IDT> parameters)
	 {
	        for (IDT param : parameters) 
	        {
	            if (param instanceof StringDataType) 
	            {
	                System.out.print(((StringDataType) param).getValue());
	            } 
	            
	            else if (param instanceof IntegerDataType) 
	            {
	                System.out.print(((IntegerDataType) param).getInt());
	            }
	            
	            else if (param instanceof RealDataType) 
	            {
	                System.out.print(((RealDataType) param).getFloat());
	            } 
	            
	            else if (param instanceof BooleanDataType)
	            {
	                System.out.print(((BooleanDataType) param).getBool() ? "true" : "false");
	            } 
	            
	            else if (param instanceof CharacterDataType) 
	            {
	                System.out.print(((CharacterDataType) param).getValue());
	            }
	            else
	            	System.out.print(param.toString());
	        }
	    }
	}