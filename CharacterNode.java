package Lex;

public class CharacterNode extends Node
{

	private char Char;
	
	public CharacterNode(char Char) 
	{
		this.Char = Char;
	}
	public CharacterNode() {
		// TODO Auto-generated constructor stub
	}
	public float getItem() 
	{
        return Char;
	}
	
	public String toString() 
	{
		return "CharacterNode(" + this.Char + ")";
	}

}
