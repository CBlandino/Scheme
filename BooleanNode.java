package Lex;

public class BooleanNode extends Node
{
	
	private boolean bool;
	
	public BooleanNode(boolean bool) 
	{
		this.bool = bool;
	}
	public BooleanNode() 
	{
		// TODO Auto-generated constructor stub
	}
	public boolean getBoolean() 
	{
        return bool;
	}
	
	public String toString() 
	{
		return "BooleanNode(" + this.bool+ ")";
	}

}
