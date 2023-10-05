package Lex;

import java.util.ArrayList;

class ArrayDataType<A extends IDT> extends IDT
{
    private ArrayList<A> value;
    private int from;
    private	int to;
    
    
    ArrayDataType(ArrayList<A> value) 
    {
        this.value = value;
    }
    
    public ArrayDataType(IDT valueAt) 
    {
		// TODO Auto-generated constructor stub
	}

	public A getValueAt(int i) 
    {
		// TODO Auto-generated method stub
		return value.get(i);
	}
    
    public ArrayList<A> getValue() 
    {
        return value;
    }
    
    public void setValue(ArrayList<A> value) 
    {
        this.value = value;
    }
    
    public String ToString() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        
        for (A element : value) 
        {
            sb.append(element.ToString()).append(" ");
        }
        
        sb.append("]");
        
        return sb.toString();
    }
    
    public void FromString(String input)
    {
     
    }

	@Override
	public Object getType() 
	{
		
		return value;
	}

	
}