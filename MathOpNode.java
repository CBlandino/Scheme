package Lex;

import Lex.Token.TokenType;;



public class MathOpNode extends Node {

	
	
	private TokenType token;
	private Node left;
	private Node right;
	
	
	
	public MathOpNode(TokenType token, Node left, Node right)
	{
		this.token = token;
		this.left = left;
		this.right = right;
	}
	
	public TokenType getToken()
	{
		return token;
	}
	
	public Node getLeft()
	{
		return left;
	}
	
	public Node getRight()
	{
		return right;
	}
	
	public String toString() 
	{	
		return  "(" + left.toString() + " " + token.name() + " " + right.toString() + ")";
	}



}
