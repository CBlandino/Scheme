package Lex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BuiltInRead extends FunctionNode
{

	public BuiltInRead(String name, ArrayList<VariableNode> parameters, ArrayList<VariableNode> constants,
			ArrayList<VariableNode> variables, ArrayList<StatementNode> statements) 
	{
		super(name, parameters, constants, variables, statements);
		// TODO Auto-generated constructor stub
	}

	public BuiltInRead() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public String getName()
	{
		return "Read";
	}

	public void execute(ArrayList<IDT> parameters) 
	{
	    Scanner scanner = new Scanner(System.in);
	    
	    for (IDT parameter : parameters)
	    {
	    	
	        Class<? extends IDT> parameterType = parameter.getClass();
	        String WordStorage = " ";
	        
	        if (parameterType == StringDataType.class) 
	        {
	            String input = scanner.next();
	            ((StringDataType) parameter).FromString(input);
	            WordStorage += parameter;
	        } 
	        
	        else if (parameterType == IntegerDataType.class) 
	        {
	            String input = scanner.next();
	            ((IntegerDataType) parameter).FromString(input);
	            WordStorage += parameter;
	        } 
	        
	        else if (parameterType == RealDataType.class) 
	        {
	            String input = scanner.next();
	            ((RealDataType) parameter).FromString(input);
	            WordStorage += parameter;
	        } 
	        
	        else if (parameterType == BooleanDataType.class)
	        {
	            String input = scanner.next();
	            ((BooleanDataType) parameter).FromString(input);
	            WordStorage += parameter;
	        } 
	        
	        else if (parameterType == CharacterDataType.class)
	        {
	            String input = scanner.next();
	            ((CharacterDataType) parameter).FromString(input);
	            WordStorage += parameter;
	        }
	        else 
	        	WordStorage += parameter;
	    }
	}
	 public boolean isVariadic() 
	 	{
	        return true;
	    }
	
	}




