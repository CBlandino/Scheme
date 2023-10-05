package Lex;

import java.io.IOException;
import java.util.ArrayList;

public class BuiltInEnd extends FunctionNode
{
	public BuiltInEnd()
	{
	
	}

	public String getName()
	{
		return "End";
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
            IDT endVar = new ArrayDataType(array.getValueAt(0));
           
            arg.set(0, endVar);
        
        
       
        }
	}

}
