package Lex;

import java.util.ArrayList;
import java.util.HashMap;

public class ProgramNode extends Node
{

	private  HashMap<String, FunctionNode> functions;
	
	public ArrayList<String> Name = new ArrayList<>();
	
	public ProgramNode() 
	{
	    functions = new HashMap<>();
	}

	public void addFunction(FunctionNode function) 
	{
	    functions.put(function.getName(), function);
	    Name.add(function.getName());
	}

	public FunctionNode getFunction(String name)
	{
	    return functions.get(name);
	}
	

	
	public String toString() 
	{	
		return functions.toString();
	}

	public ArrayList<String> getName() 
	{
		return Name;
	}

	public void setName(ArrayList<String> name) 
	{
		Name = name;
	}
}
