package Lex;

public class AssignmentNode extends StatementNode 
	{
    	private VariableReferenceNode target;
    	private Node value;

    public AssignmentNode(VariableReferenceNode target, Node value)
    {
        this.target = target;
        this.value = value;
    }

    public VariableReferenceNode getTarget() 
    {
        return target;
    }

    public Node getValue()
    {
        return value;
    }

    @Override
    public String toString() 
    {
        return String.format("%s := %s", target, value);
    }
}