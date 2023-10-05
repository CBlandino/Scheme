package Lex;

public class BooleanDataType extends IDT
{
	
	private boolean bool;
    
    BooleanDataType(boolean bool)
    {
        this.bool = bool;
    }
    
    public boolean getBool()
    {
        return bool;
    }
    
    public void setBool(boolean bool)
    {
        this.bool = bool;
    }
    
    public String ToString() 
    {
        return Boolean.toString(bool);
    }
    
    public void FromString(String input)
    {
        bool = Boolean.parseBoolean(input);
    }

	@Override
	public Object getType() {
		// TODO Auto-generated method stub
		return bool;
	}
}
	

