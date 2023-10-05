package Lex;

public class VariableReferenceNode extends Node
	{
		private String name;
		private Node index;

    public VariableReferenceNode(String name, Node index)
    {
        this.name = name;
        this.index = index;
    }

    public VariableReferenceNode(String name) 
    {
        this(name, null);
    }

    public String getName() 
    {
        return name;
    }

    public Node getIndex() 
    {
        return index;
    }

    @Override
    public String toString() 
    {
        if (index != null)
        {
            return String.format("%s[%s]", name, index);
        } 
        
        else 
        {
            return name;
        }
    }
}