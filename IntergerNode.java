package Lex;

public class IntergerNode extends Node 
{

	private int integer;
	
	public IntergerNode(int integer) 
	{
		this.integer = integer;
	}

	public IntergerNode() 
	{
		// TODO Auto-generated constructor stub
	}

	public int getItem() 
	{
        return integer;
	}

	
	@Override
	public String toString()
	{
		if (integer == 0)
			return  "IntergerNode";
		else
			return  "IntergerNode" + "(" + integer + ")";
	}
	

	

}
	


