package Lex;

public class StringNode extends Node
{
	
	private String string;
	
	public StringNode()
	{
		
	}
	
	public StringNode(String string) 
	{
		this.string = string;
	}
	public String getString() 
	{
        return string;
	}
	
	public String toString() 
	{
		if(this.string == null)
			return "StringNode";
		else
			return "StringNode(" + this.string+ ")";
	}

}
