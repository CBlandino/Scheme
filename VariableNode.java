package Lex;

public class VariableNode extends Node
{

	private String name;
    private Node type;
    private boolean changeable;
    private Node value;
    private int n1;
    private int n2;
    private float n3;
    private float n4;
    

    public VariableNode(String name, Node type, boolean changeable) 
    {
        this.name = name;
        this.type = type;
        this.changeable = changeable;
       
    }
    
    public VariableNode(String name, Node type, int n1, int n2, boolean changeable, Node value) 
    {
        this.name = name;
        this.type = type;
        this.n1 = n1;
        this.n2 = n2;
        this.changeable = changeable;
        this.value = value;
    }
    
    public VariableNode(String name, Node type, boolean changeable, Node value) 
    {
        this.name = name;
        this.type = type;
        this.changeable = changeable;
        this.value = value;
    }
    
    public VariableNode() {
		// TODO Auto-generated constructor stub
	}

	

	public VariableNode(String name, Node type, float realfrom, float realto, boolean  changeable) {
		this.name = name;
        this.type = type;
        this.n3 = n3;
        this.n4 = n4;
        this.changeable = changeable;
	}

	public String getVariable() 
    {
        return name;
    }

    public Node getType() 
    {
        return type;
    }

    public boolean isChangeable()
    {
        return changeable;
    }
    
    public String getName()
    {
    	return name;
    			
    }
	
    public Node getValue() 
    {
        return value;
    }
    
	public String toString() 
	{	
		if (value == null)
			return name + " " + type.toString();
			
		else
				
			if (this.n1 != -1 && this.n2 != -1 )
				return name + " " + type.toString() + " " + value + " from" + this.n1 + " to" + this.n2;
			else if (this.n3 != -1 && this.n4 != -1 )
				return name + " " + type.toString() + " " + value + " from" + this.n3 + " to" + this.n4;
			else 
				return name + " " + type.toString() + " " + value;
	}

}
