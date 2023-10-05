package Lex;

import java.util.ArrayList;

public class ForNode extends StatementNode 
{
	private String name;
    private Node from;
    private Node to;
    private Node stepValue;
    private ArrayList<StatementNode> statements;
    
    public ForNode(Node from, Node to, ArrayList<StatementNode> statements)
    {
        this.from = from;
        this.to = to;
        this.statements = statements;
    }
    
      
    public Node getFrom() 
    {
        return from;
    }
    
    public Node getTo() 
    {
        return to;
    }
    
    public ArrayList<StatementNode> getStatements() 
    {
        return statements;
    }
    
    public String getName()
    {
    	return name;
    }

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.from.toString() + " " + this.to.toString() + " "+ this.statements.toString();
	}


	public Node getStepValue() 
	{
		// TODO Auto-generated method stub
		return stepValue;
	}
}