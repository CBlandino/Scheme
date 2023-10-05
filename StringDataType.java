package Lex;

public class StringDataType extends IDT
{
    private String value;
    
    StringDataType(String value) 
    {
        this.value = value;
    }
    
    public String getValue() 
    {
        return value;
    }
    
    public void setValue(String value) 
    {
        this.value = value;
    }
    
    public String ToString() {
        return "\"" + value + "\"";
    }
    
    public void FromString(String input) 
    {    	
        value = input;
    }

	@Override
	public Object getType() {
		// TODO Auto-generated method stub
		return value;
	}
}



