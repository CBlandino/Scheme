package Lex;

public class RealNode extends Node {

	private float value;
	
	public RealNode()
	{
		
	}
	
	public RealNode(float value) 
	{
		value = this.value;
	}

	public float getItem() 
	{
        return value;
	}

	
	@Override
	public String toString()
	{
		if(value == 0)
			return "RealNode";
		else
			return "RealNode(" + this.value + ")";
	}
	
	

}
