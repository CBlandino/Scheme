package Lex;

import java.util.ArrayList;

public class RepeatNode extends StatementNode
{
    
    private final BooleanCompareNode condition;
    private final ArrayList<StatementNode> statements;
    
    public RepeatNode(BooleanCompareNode condition, ArrayList<StatementNode> statements) 
    {
        this.condition = condition;
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
	public String toString() 
	{
		// TODO Auto-generated method stub
		return this.condition.toString() + " " + this.statements.toString();
	}
}
