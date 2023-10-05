package Lex;
import Lex.Token.TokenType;

public class BooleanCompareNode extends Node 
{
	private TokenType type;
    private Node left;
    private Node right;

    public BooleanCompareNode(TokenType type, Node left, Node right) 
    {
        this.type = type;
        this.left = left;
        this.right = right;
    }

    public BooleanCompareNode(TokenType compareType, Node left) {
    	 this.type = type;
         this.left = left;
	}

	public TokenType getType() 
    {
        return type;
    }

    public Node getLeft() 
    {
        return left;
    }

    public Node getRight()
    {
        return right;
    }

    @Override
    public String toString()
    {
        return String.format("(%s %s %s)", left, type, right);
    }
}
