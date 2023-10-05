package Lex;

import java.io.IOException;
import java.util.ArrayList;

public class BuiltInStart extends FunctionNode
{
	public BuiltInStart()
	{
		
	}
	
	public String getName()
	{
		return "Start";
	}
	
	public void execute (ArrayList<IDT> arg) throws IOException 
	{
		
        if (arg.size() != 1)
        {
            throw new IOException("Cannot start empty array");
        }
        
        if(arg.get(0) instanceof ArrayDataType)
        {
        // Get the array from the argument
        ArrayDataType array = (ArrayDataType)arg.get(0).getType();
        
        // Set the start variable
        IDT startVar = new ArrayDataType(array.getValueAt(0));
       
        arg.set(0, startVar);
        
        }
        
        else
        	throw new IOException("Invalid argument type, expected ARRAY");
    }

	
	
}
