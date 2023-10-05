package Lex;

public class CharacterDataType extends IDT
{
    private char value;
    
    CharacterDataType(char value)
    {
        this.value = value;
    }
    
    public char getValue() {
        return value;
    }
    
    public void setValue(char value) 
    {
        this.value = value;
    }
    
    public String ToString() {
        return "'" + value + "'";
    }
    
    public void FromString(String input)
    {
        value = input.charAt(0);
    }

	@Override
	public Object getType() {
		// TODO Auto-generated method stub
		return value;
	}
}


