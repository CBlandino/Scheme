package Lex;

import java.util.ArrayList;

public class FunctionCallNode extends StatementNode 
	{
    private String functionName;
    private ArrayList<ParameterNode> parameters;
    
    public FunctionCallNode(String functionName, ArrayList<ParameterNode> parameters) 
    {
        this.functionName = functionName;
        this.parameters = parameters;
    }
    
    public String getFunctionName() {
        return functionName;
    }
    
    public ArrayList<ParameterNode> getParameters()
    {
        return parameters;
    }

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	public static boolean isVar() {
		// TODO Auto-generated method stub
		return false;
	}
}

