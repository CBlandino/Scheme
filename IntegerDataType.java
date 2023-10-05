package Lex;


public class IntegerDataType extends IDT 
{

	private int number;
	
	public IntegerDataType(int number)
	{
		this.number = number;
	}
	
	public void setInt(int number)
	{
		this.number = number;		
	}
	
	public int getInt()
	{
		return number;
	}
	
	@Override
	public String ToString()
	{
		return Integer.toString(number);
	}

	@Override
	public void FromString(String input) 
	{	
		number = Integer.parseInt(input);		
	}

	@Override
	public Object getType() {
		// TODO Auto-generated method stub
		return number;
	}

}
