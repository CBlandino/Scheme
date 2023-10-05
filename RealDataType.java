package Lex;

public class RealDataType extends IDT
{

	private float number;
    
    RealDataType(float number)
    {
        this.number = number;
    }
    
	public float getFloat()
    {
        return number;
    }
    
    public void setFloat(float number)
    {
        this.number = number;
    }
    
    public String ToString() 
    {
        return Float.toString(number);
    }
    
    public void FromString(String input) 
    {
        number = Float.parseFloat(input);
    }

	@Override
	public Object getType() 
	{
		return number;
	}
}	

