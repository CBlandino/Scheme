package Lex;

import java.io.IOException;
import java.util.ArrayList;

public class FunctionNode extends Node 
{

	private String name;
    private ArrayList<VariableNode> parameters;
    private ArrayList<VariableNode> constants;
    private ArrayList<VariableNode> variables;
    private ArrayList<StatementNode> statements;

	public FunctionNode(String name, ArrayList<VariableNode> parameters, ArrayList<VariableNode> constants, ArrayList<VariableNode> variables, ArrayList<StatementNode> statements) 
	{
		this.name = name;
		this.parameters = parameters;
		this.constants = constants;
		this.variables = variables;
		this.statements = statements;
	}

	public FunctionNode() 
	{
		
	}

	
	public String getName()
	{
		return name;
	}

	public ArrayList<VariableNode> getParameters() 
	{
		return parameters;
	}

	public ArrayList<VariableNode> getConstants() 
	{
		return constants;
	}

	public ArrayList<VariableNode> getVariables()
	{
		return variables;
	}
	
	public ArrayList<StatementNode> getStatements()
	{
		return statements;
	}

	//should it be a string builder since its an array list?
	public String toString() 
	{

		if(this.statements == null)
		{
			return "Parameters" + this.parameters + "\n Variables" + this.variables + "\n Constants " + this.constants;
		}
		
		else
	return "Parameters" + this.parameters + "\n Variables" + this.variables +"\n Constants " + this.constants +"\n Statements " + this.statements;
			
			
		
	}

	public boolean isVariadic() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	
	
	public void execute(ArrayList<IDT> parameterValues) throws IOException 
	{
		
		
	}

	public boolean isVardic(String parameterName) 
	{
		// TODO Auto-generated method stub
		return false;
	}

}
