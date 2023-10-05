package Lex;

import java.util.ArrayList;

public class IfNode extends StatementNode 
{
    
    private BooleanCompareNode condition;
    private ArrayList<StatementNode> statements;
    private IfNode elsIF;
    
    public IfNode(BooleanCompareNode condition, ArrayList<StatementNode> statements)
    {
        this.condition = condition;
        this.statements = statements;
    }
    
    public IfNode(ArrayList<StatementNode> statements)
    {
    	this.statements = statements;
    }
    
    public BooleanCompareNode getCondition()
    {
        return condition;
    }
    
    public ArrayList<StatementNode> getStatements() 
    {
        return statements;
    }

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.condition.toString() + " " + this.statements.toString();
	}

	public IfNode setElse(IfNode elsIF)
	{
		return elsIF = this.elsIF;
	}
}
