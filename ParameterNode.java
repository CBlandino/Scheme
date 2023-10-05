package Lex;

public class ParameterNode extends Node
{

    private VariableReferenceNode variableReference;
    private Node expression;
    
    public ParameterNode(VariableReferenceNode variableReference, Node expression)
    {
        this.variableReference = variableReference;
        this.expression = expression;
    }
    
    public boolean isVariableParameter() 
    {
        return variableReference != null;
    }
    
    public VariableReferenceNode getVariableParameter()
    {
        return variableReference;
    }
    
    public Node getExpressionParameter() 
    {
        return expression;
    }

	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
