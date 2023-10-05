package Lex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import Lex.Token.TokenType;

public class Parser
{
   
	//Initalize Tokens

	private Queue<Token> tokens;

	public Parser(List tokens) 
	{
		LinkedList<Token> linkedList = new LinkedList<>(tokens);
		this.tokens = (Queue) linkedList;
	//	System.out.print(tokens);
	}

	//matches the token with its token tyoe, if it is present remove the token if not keep the token by returning null
	/**
	 * 
	 * @param type
	 * @return
	 */
	private Token matchAndRemove(Token.TokenType type) 
	{
		if (tokens.isEmpty() || tokens.peek().getCharacter() != type)
		{
			return null;
		}
		return tokens.remove();
	}

	

	
	/**
	 * 
	 * @throws IOException
	 */
	private void expectEndOfLine() throws IOException 
	{
		Token eof = matchAndRemove(Token.TokenType.ENDOFLINE);
		
        if (eof == null)
        	{
        		throw new IOException("Expects end of line");
        	}
	}

	// peeks into the array to get the value issued by int i
	/**
	 * 
	 * @param i
	 * @return
	 */
	private Token peek(int i) 
	{
	if (tokens.size() <= i)
		{
		return null;
		}
	else
		{
		return (Token) tokens.toArray()[i];
		}
	}
	
	//Starts the parsing by looping through the list of tokens and calls expression, then term, then factor
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public Node parse() throws IOException 
    {
		
		FunctionNode function = function();
		ProgramNode program = new ProgramNode();
		
		
		
		
		
		
	    System.out.println(function.toString());           
	    program.addFunction(function);
	        
	        
	        
	       
		return program;
	    
		
	   
	}

	//checks if theres a minus or plus token finds a minus or plus token and calls term
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	private Node expression() throws IOException 
	{
		
		Node left = term();
		
		if (left == null) 
		{	
		return null;
		}
		

	if (peek(0).getCharacter() == TokenType.PLUS || peek(0).getCharacter() == TokenType.MINUS) 
		{

		Token op = matchAndRemove(peek(0).getCharacter());
		
		if (op == null) 
			{
			return left;
			}

		Node right = term();

		if (right == null) 
			{
			throw new IOException("Error in Parser, no right token in expression()" );
			}

		left = new MathOpNode(op.getCharacter(), left, right);
		
        }
        return left;
		}
		
		
		

	//checks if theres a times, divide or mod, if there is call factor
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
    private Node term() throws IOException
    {
    	
    	Node left = factor();
    	
		if (left == null) 
			{
				return null;
			}
		
		else if (peek(0).getCharacter() == TokenType.ENDOFLINE)
			{
				return left;
			}
  if (peek(0).getCharacter() == TokenType.TIMES || peek(0).getCharacter() == TokenType.DIVIDE || peek(0).getCharacter() == TokenType.MOD) 
		{

		Token op = matchAndRemove(peek(0).getCharacter());
		
		if (op == null) 
			{
			return left;
			}

		Node right = factor();

		if (right == null) 
			{
			throw new IOException("Error in Parser, no right token in expression()" );
			}

		left = new MathOpNode(op.getCharacter(), left, right);

		}
		return left;
	}
       
    
   
    /**
     * 
     * @return
     * @throws IOException
     */
    private Node factor() throws IOException 
    {
    	
    	Token token = peek(0);
    	
    	if (peek(0).getCharacter() == Token.TokenType.NUMBER
    	    || peek(0).getCharacter() == Token.TokenType.MINUS
    	    || peek(0).getCharacter() == Token.TokenType.LPAREN
    	    || peek(0).getCharacter() == Token.TokenType.BOOLEAN
    	    || peek(0).getCharacter() == Token.TokenType.IDENTIFIER
    	    )
     {
    		
    	    		
    		
			
    		
    	switch (token.getCharacter())
    	{
    	
    	
    		case NUMBER:
    		{	
    			token = matchAndRemove(Token.TokenType.NUMBER);
    			
    			if(Float.parseFloat(token.getValue()) % 1 == 0) 
    			{		
    				
    				return new IntergerNode(Integer.parseInt(token.getValue()));			
    			}
    			
    			else
    			{
    				return new RealNode(Float.parseFloat(token.getValue()));
    			}
    				
    			
    		}
    		case MINUS:
    		{
    			token = matchAndRemove(Token.TokenType.MINUS);
    			
    			Node inner = factor();

    			if (inner == null) 
    				{
    					throw new IOException("Expected  '-'");
    				}

    			return new MathOpNode(Token.TokenType.MINUS, new IntergerNode(0), inner);
    		}
    		
    		case LPAREN:
    		{
    			token = matchAndRemove(Token.TokenType.LPAREN);
    			
    			Node expression = expression();

    			if (expression == null)
    			{
    				throw new IOException("Expected '('");
    			}

    			if (matchAndRemove(Token.TokenType.RPAREN) == null)
    			{
    				throw new IOException("Expected ')'");
    			}

    			return expression;
    		}
    		
    		/*
    		 * if peek(0) is a boolean op then match and remove it 
    		 * then return a boolean node with the peek(0)
    		 */
    		
    		case BOOLEAN:
    		{
    			  token = matchAndRemove(Token.TokenType.BOOLEAN);
    			  
    			  return new BooleanNode( Boolean.parseBoolean(token.getValue()));
    		}
    		
    		/*
    		 * If peek 0 is an Identifier then match and remove
    		 * create a new variable node using peek(0)
    		 * go through and find '[' and remove it
    		 * then call expresion
    		 * create a new reference node using peek(0) and the return from expression
    		 */
    			
    		case IDENTIFIER:
    		{
    			
    			
    			token = matchAndRemove(Token.TokenType.IDENTIFIER);
    			Node variable = new VariableReferenceNode(token.getValue());
    			
    			
    	        if (peek(0).getCharacter() == TokenType.LEFT_BRACKET)
    	        {
    	            
    	            matchAndRemove(TokenType.LEFT_BRACKET);
    	            Node index = expression();
    	            
    	            variable = new VariableReferenceNode(token.getValue(), index);
    	            
    	            
    	            
    	            if (peek(0).getCharacter() != TokenType.RIGHT_BRACKET) 
    	            {
    	                throw new IOException("']' expected");
    	            }
    	            matchAndRemove(TokenType.RIGHT_BRACKET);
    	            return variable;
    	        }
    	        return variable;
    		}
    		
    	
    		
    		
    		default:

    			throw new IOException("Invalid Factor" + peek(0).getCharacter());
    			

    	}
      }
    	else
    		throw new IOException("Invalid Factor" + peek(0).getCharacter());
		
    }
    
    /**
     * 
     * @return
     * @throws IOException
     */
    private IfNode parseIf() throws IOException
    {
    	
    	//Calls boolcompare to determin the condition,
    	//bool compare calls expression which solves the rest of the if statement
    	BooleanCompareNode bool = (BooleanCompareNode) boolCompare();
    	matchAndRemove(TokenType.THEN);
    	expectEndOfLine();
    	
    	//Make an arraylist for 'Statement" then call statements and store it into 'Statement'
    	ArrayList<StatementNode> Statement = new ArrayList<>();
    	Statement =	statements();

    	
    	IfNode IF = new IfNode(bool,Statement);
    	IfNode ElsIf;
    	
    	if (peek(0).getCharacter() == Token.TokenType.ELSIF)
    	{
	    	while (!tokens.isEmpty() && peek(0).getCharacter() == Token.TokenType.ELSIF )
	    	{
	    		
	    		matchAndRemove(TokenType.ELSIF); 		
	        	bool = (BooleanCompareNode) boolCompare();
	        	matchAndRemove(TokenType.THEN);
	        	expectEndOfLine();
	        	
	        	Statement = statements();
	        
	        	ElsIf = new IfNode(bool, Statement);
	        	IF.setElse(ElsIf);	        	
	        	IF = ElsIf;
	        	
	        	if (peek(0).getCharacter() == Token.TokenType.ELSE)
	        	{
	        		matchAndRemove(Token.TokenType.ELSE);
	        		expectEndOfLine();
	    			Statement = statements();
	    			break;
	        	}
	        	
	        	

	    	}
	    	
	    	if (!tokens.isEmpty() && peek(0).getCharacter() == Token.TokenType.ELSE)
	    	{
	    		matchAndRemove(Token.TokenType.ELSE);
	    		expectEndOfLine();
    			Statement.addAll(statements());
    		
	    	}
    	}
    	
    	else if (peek(0).getCharacter() == Token.TokenType.ELSE)
    	{
    		matchAndRemove(Token.TokenType.ELSE);
    		
    		expectEndOfLine();
			Statement.addAll(statements());
			
			ElsIf = new IfNode(Statement);
    	}
    		
    		
    	return IF;
    	
    	
    }
    

    private RepeatNode parseRepeat() throws IOException
    {   	
    	matchAndRemove(TokenType.UNTIL);
    	
    	BooleanCompareNode bool = (BooleanCompareNode) boolCompare();
    	
    	
    	ArrayList<StatementNode> Statement = new ArrayList<>();
    	Statement =	statements();
    	
    	return new RepeatNode(bool, Statement);
    }
    
    private WhileNode parseWhile() throws IOException
    {
    	
    	BooleanCompareNode bool = (BooleanCompareNode) boolCompare();
    	expectEndOfLine();
    	
    	
    	ArrayList<StatementNode> Statement = new ArrayList<>();
    	Statement =	statements();
    	
    	return new WhileNode(bool, Statement);
    }
    
    private ForNode parseFor() throws IOException 
    {
    	
    	matchAndRemove(Token.TokenType.IDENTIFIER);
    	
    	matchAndRemove(Token.TokenType.FROM);
    	Node from = expression();
    	
    	matchAndRemove(Token.TokenType.TO);
    	Node to = expression();

    	
    	ArrayList<StatementNode> Statement = new ArrayList<>();
    	Statement =	statements();
    	
		return new ForNode(from, to, Statement);
    }
    
    
    

   /**
    * Checks the type of statement then goes to the coresponding statement method
    * @return
    * @throws IOException
    */
    private StatementNode statement() throws IOException 
    {
       
    	if(peek(0).getCharacter() == TokenType.IF || peek(0).getCharacter() == TokenType.ELSIF || peek(0).getCharacter() == TokenType.ELSE)
    	{
    		matchAndRemove(Token.TokenType.IF);
			return parseIf();

    	}
    	
    	else if (peek(0).getCharacter() == TokenType.FOR)
    	{
    		matchAndRemove(TokenType.FOR);
			return parseFor();
    	}
    	
    	else if (peek(0).getCharacter() == TokenType.WHILE)
    	{
    		matchAndRemove(TokenType.WHILE);
			return parseWhile();
    	}
    	
    	else if (peek(0).getCharacter() == TokenType.REPEAT)
    	{
    		matchAndRemove(TokenType.WHILE);
			return parseRepeat();
    	}
    	
        if (peek(0).getCharacter() == TokenType.NUMBER) 
        {
            matchAndRemove(peek(0).getCharacter());
            Node expr = expression();
            return null;
        } 
        
        else if (peek(0).getCharacter() == TokenType.IDENTIFIER) 
        {
            AssignmentNode varRef = assignment();
            return varRef;
            
        }
        return null;
    }
    
    private  ArrayList<StatementNode> statements() throws IOException 
    {
    	
    	matchAndRemove(TokenType.INDENT);
		
		ArrayList<StatementNode> Statement = new ArrayList<>();
		StatementNode state = statement();

    	
    	while (state != null)
		{
			Statement.add(state);
			
			if(tokens.isEmpty())
			{
				break;
			}
			
			state = statement();
		}
    	
    	
    	matchAndRemove(TokenType.DEDENT);
		return Statement;
		
	}
    
    
    /*
     * Gets the left of the operand
     * gets the compare type
     * finally gets the right of the operand
     * returns a booleannode of left, operand/comparetype, right
     */
    private Node boolCompare() throws IOException 
    {
        Node left = expression();
        TokenType compareType = null;
        switch (peek(0).getCharacter()) 
        {
            case EQUALS:
                compareType = TokenType.EQUALS;
                break;
            case NOTEQUAL:
                compareType = TokenType.NOTEQUAL;
                break;
            case LESSTHAN:
                compareType = TokenType.LESSTHAN;
                break;
            case LESSOREQUAL:
                compareType = TokenType.LESSOREQUAL;
                break;
            case GREATERTHAN:
                compareType = TokenType.GREATERTHAN;
                break;
            case GREATOREQUAL:
                compareType = TokenType.GREATOREQUAL;
                break;
            
        }
        if (compareType != null)
        {
        matchAndRemove(compareType);
        Node right = expression();
        return new BooleanCompareNode(compareType, left, right);
        }
        else if(compareType == null)
        {
        	return left;
        }
        
        return new BooleanCompareNode(compareType, left);
    }

    /*
     * Still a bit iffy, need some work
     */
	private AssignmentNode assignment() throws IOException 
    {
    	
        VariableReferenceNode variable = (VariableReferenceNode) factor();
        
        if (peek(0).getCharacter() != TokenType.COLEQUAL) 
        {
            throw new IOException("Expected ':=' after target in assignment, not" + peek(0).getValue());
        }
        matchAndRemove(TokenType.COLEQUAL);
        
        Node value = boolCompare();
        
        expectEndOfLine();
        
        return new AssignmentNode(variable, value);
        
    }
    

	private FunctionCallNode parseFunctionCalls() throws IOException
	{
		
		ArrayList<ParameterNode> parameters = new ArrayList<>();
		String funcName;
		ParameterNode Para = null;
		
		
		funcName = peek(0).getValue();
		matchAndRemove(TokenType.IDENTIFIER);
		
		while (peek(0).getCharacter() != TokenType.ENDOFLINE)
		{
			if (peek(0).getCharacter() == TokenType.VAR)
			{
				
				
				
				Para.isVariableParameter();
				
				parameters.add(Para);
				matchAndRemove(TokenType.VAR);
				
				Node Fact = factor();
				
				
				
				
				parameters.add( (ParameterNode) Fact);
					
				
				
			}
			else if (peek(0).getCharacter() == TokenType.IDENTIFIER)
			{
				
				Node exp = expression();
				parameters.add((ParameterNode) exp);
				
			}
			else
			{
				if(peek(0).getCharacter() == TokenType.COMMA)
				{
					matchAndRemove(TokenType.COMMA);
				}
				
				else 
					throw new IOException ("Comma expected in FunctionCalls");
			}
		}
		
		return new FunctionCallNode(funcName, parameters);
		
		
	}
	
	
	/**
     * 
     * @return FunctionNode
     * @throws IOException
     */
    private FunctionNode function() throws IOException
    {
    	ArrayList<VariableNode> parameters = new ArrayList<>();
    	ArrayList<VariableNode> variables = new ArrayList<>();
    	ArrayList<VariableNode> constants = new ArrayList<>();
    	ArrayList<StatementNode> Statement = new ArrayList<>();
    	Token token = peek(0);
    	
    	//gets the define token, removes it, and coninues with code
    		if (token.getCharacter() == Token.TokenType.DEFINE)
    		{
	    		  
	    		matchAndRemove(Token.TokenType.DEFINE);
	    		
	    		//sets the name of the function with the word after define, thens removes the name from the list
	    		String name = peek(0).getValue();     
	    		matchAndRemove(Token.TokenType.IDENTIFIER);
	    		
	    		//removes the expected left paren then gets the parameter and stores it in the a variable node arraylist 
	    		//then removes the expected right parent
	    		matchAndRemove(Token.TokenType.LPAREN);	   		
	        	parameters = parameterDeclarations();  
	           	matchAndRemove(Token.TokenType.RPAREN);
	           		
	        	expectEndOfLine();
	           	
	           	//checks if the token list is not empty if isnt then check if the current character is VARIABLE or CONSTANT
	           	//and call the corresponding method and store it in a variablenode arraylist
	           	while(!tokens.isEmpty())
	           	{
	           		
	           		
	           		
	            	if(peek(0).getCharacter() == Token.TokenType.VARIABLES)
	            	{
	            		matchAndRemove(Token.TokenType.VARIABLES);
		           		variables.addAll(variableDeclarations());
		           		expectEndOfLine();
		           		
	            	}
		           	else if (peek(0).getCharacter() == Token.TokenType.CONSTANTS)
		           	{
		           		matchAndRemove(Token.TokenType.CONSTANTS);
		           		constants.addAll(constantDeclarations());
		           		expectEndOfLine();
	            	}
	            	
		           	else
		           		
		           		break;
	            	
	           	}
	           	
	           	if(peek(0) != null && peek(0).getCharacter() == Token.TokenType.INDENT)
	           	{
	           		Statement.addAll(statements());

	           	}
	           	
	           
	           	
           	return new FunctionNode(name, parameters, constants, variables, Statement);
           	
	           	
            
           
            
          
            
            
    	}	
    	
    	
    	
    		
    		
    	else if(token.getCharacter() != Token.TokenType.DEFINE)
    	{
    		throw new IOException("Cannot start a function with " + peek(0).getCharacter()); 
    	}
		return null;
    }



    /**
     * 
     * @return
     * @throws IOException
     */
	private ArrayList<VariableNode> variableDeclarations() throws IOException 
	{		
		ArrayList<VariableNode> variable = new ArrayList<>();
		ArrayList<String> names = new ArrayList<>();
		
		int from = -1;
		int to = -1;
		
		float realfrom = -1;
		float realto = -1;
		
		
		
		 if (peek(0).getCharacter() == TokenType.IDENTIFIER)
	        {
	        
	        	names.add(peek(0).getValue());
	            matchAndRemove(TokenType.IDENTIFIER);
	           
	            while (peek(0).getCharacter() == Token.TokenType.COMMA)
	            {
	            	
  	            matchAndRemove(TokenType.COMMA);
  	            
	            	if (peek(0).getCharacter() == TokenType.IDENTIFIER)
	            		{            		
	            			names.add(peek(0).getValue());
	            			matchAndRemove(TokenType.IDENTIFIER);	
	            		}
	            	else if(peek(0).getCharacter() != TokenType.IDENTIFIER)
	            		{
	            			throw new IOException("Names are expected after a comma, what was seen was: " + peek(0).getCharacter());
	            		}
	            }
	            
	            if (peek(0).getCharacter() == Token.TokenType.COL);
	            {
	            	matchAndRemove(TokenType.COL);
	            	
	            	switch(peek(0).getCharacter())
	            	{
	            	case INTEGER:
	            	{
	            		matchAndRemove(TokenType.INTEGER); 	
	            		if(peek(0).getCharacter() == TokenType.FROM)
	            		{
	            			matchAndRemove(TokenType.FROM);
	            			
	            			if(peek(0).getCharacter() == TokenType.NUMBER)
	            			{
	            				
	            				from = Integer.parseInt(peek(0).getValue());
	            				matchAndRemove(TokenType.NUMBER);
	            				
	            				if(peek(0).getCharacter() == TokenType.TO)
								{
	            					matchAndRemove(TokenType.TO);
	    	            			
	    	            			if(peek(0).getCharacter() == TokenType.NUMBER)
	    	            			{
	    	            				
	    	            				to = Integer.parseInt(peek(0).getValue());
	    	            				matchAndRemove(TokenType.NUMBER);
	    	            				for(String name : names)
	    	            				{
	    	            				variable.add(new VariableNode(name.toString(), new IntergerNode(), from, to , false, null));
	    	            				}
	    	            				names.clear();
	    	            			}
	    	            			
	    	            			else if(peek(0).getCharacter() != TokenType.NUMBER)
	    	            			{
	    	            				throw new IOException("A number is excpeted for inializing an array");
	    	            			}
								}
	            				
	            				else if(peek(0).getCharacter() != TokenType.TO)
		            			{
		            				throw new IOException("An array needs a 'to' to set the size of array");
		            			}
	            			
	            			}
	            			
	            			else if(peek(0).getCharacter() != TokenType.NUMBER)
	            			{
	            				throw new IOException("A number is excpeted for inializing an array");
	            			}
	            		
	            		}
	            		else
	            		{
	            			for(String name : names)
	            			{
	            			variable.add(new VariableNode(name.toString(), new IntergerNode() , false));
	            			}
	            			names.clear();
	            			
	            			matchAndRemove(TokenType.INTEGER); 	
	            		}
	            	}
	            	
	            		break;
	            		
	            	case REAL:
	            	{
	            		matchAndRemove(TokenType.REAL);	
	            		if(peek(0).getCharacter() == TokenType.FROM)
	            		{
	            			matchAndRemove(TokenType.FROM);
	            			
	            			if(Float.parseFloat(peek(0).getValue()) % 1 == 0) 
	            			{		

		            			if(peek(0).getCharacter() == TokenType.NUMBER || peek(0).getCharacter() == TokenType.DECIMAL)
		            			{
		            				
		            				realfrom = Float.parseFloat(peek(0).getValue());
		            				matchAndRemove(TokenType.NUMBER);
		            				
		            				
		            				
		            				if(peek(0).getCharacter() == TokenType.TO)
									{
		            					matchAndRemove(TokenType.TO);
		    	            			
		    	            			if(peek(0).getCharacter() == TokenType.NUMBER)
		    	            			{
		    	            				realto = Float.parseFloat(peek(0).getValue());
		    	            				matchAndRemove(TokenType.NUMBER);
		    	            				for(String name : names)
		    		            			{
		    	            				variable.add(new VariableNode(name.toString(), new RealNode(), realfrom, realto , false));
		    		            			}
		    	            				names.clear();
		    	            			}
		    	            			else if(peek(0).getCharacter() != TokenType.NUMBER)
		    	            			{
		    	            				throw new IOException("A number is excpeted for inializing an array");
		    	            			}
									}
		            				
		            				else if(peek(0).getCharacter() != TokenType.TO)
			            			{
			            				throw new IOException("An array needs a 'to' to set the size of array");
			            			}
		            			
		            			}
		            			
		            			else if(peek(0).getCharacter() != TokenType.NUMBER)
		            			{
		            				throw new IOException("A number is excpeted for inializing an array");
		            			}
	            			}
	            		}
	            		
	            		else
	            		{
	            			for(String name : names)
	            			{
	            			variable.add(new VariableNode(name, new RealNode() , false));
	            			}
	            			names.clear();
	            			matchAndRemove(TokenType.REAL);	
	            		}
	            		
	            	}
	            	
	            	break;
	            	
	            	case BOOLEAN:
	            	{
	            		for(String name : names)
        				{
	            		variable.add(new VariableNode(name.toString(), new BooleanNode() , false));
        				}
	            		names.clear();
         	 			matchAndRemove(TokenType.BOOLEAN);		
	            	}
	            	
	            	break;
	            	
	            	case CHARACTER:
	            	{
	            		for(String name : names)
        				{
	            			variable.add(new VariableNode(name.toString(), new CharacterNode() , false));
        				}
	            			names.clear();
	            			matchAndRemove(TokenType.CHARACTER);
	            	}
	            	
	            	break;
	            	
	            	case STRING:
	            	{
	            		matchAndRemove(TokenType.STRING);	
	            		if(peek(0).getCharacter() == TokenType.FROM)
	            		{
	            			matchAndRemove(TokenType.FROM);
	            			
	            			if(peek(0).getCharacter() == TokenType.NUMBER)
	            			{
	            				from = Integer.parseInt(peek(0).getValue());
	            				matchAndRemove(TokenType.NUMBER);
	            				
	            				
	            				if(peek(0).getCharacter() == TokenType.TO)
								{
	            					matchAndRemove(TokenType.TO);
	    	            			
	    	            			if(peek(0).getCharacter() == TokenType.NUMBER)
	    	            			{
	    	            				to = Integer.parseInt(peek(0).getValue());
	    	            				matchAndRemove(TokenType.NUMBER);
	    	            				
	    	            				for(String name : names)
	    	            				{
	    	            				variable.add(new VariableNode(name, new StringNode(), from, to ,false, null));
	    	            				}
	    	            				names.clear();
	    	            			}
	    	            			else if(peek(0).getCharacter() != TokenType.NUMBER)
	    	            			{
	    	            				throw new IOException("A number is excpeted for inializing an array");
	    	            			}
								}
	            				
	            				else if(peek(0).getCharacter() != TokenType.TO)
		            			{
		            				throw new IOException("An array needs a 'to' to set the size of array");
		            			}
	            			
	            			}
	            			
	            			else if(peek(0).getCharacter() != TokenType.NUMBER)
	            			{
	            				throw new IOException("A number is excpeted for inializing an array");
	            			}
	            		}
	            		else
	            		{
	            			for(String name : names)
            				{
	            			variable.add(new VariableNode(name.toString(), new StringNode() , false));
            				}
	            			names.clear();
	            			matchAndRemove(TokenType.STRING);	
	            		}
	            	}
	            	
	            	break;
	            	
	            	case ARRAY:
	            	{
	            		matchAndRemove(TokenType.ARRAY);	
	            		if(peek(0).getCharacter() == TokenType.FROM)
	            		{
	            			matchAndRemove(TokenType.FROM);
	            			
	            			if(peek(0).getCharacter() == TokenType.NUMBER)
	            			{
	            				from = Integer.parseInt(peek(0).getValue());
	            				matchAndRemove(TokenType.NUMBER);
	            				
	            				if(peek(0).getCharacter() == TokenType.TO)
								{
	            					matchAndRemove(TokenType.TO);
	    	            			
	    	            			if(peek(0).getCharacter() == TokenType.NUMBER)
	    	            			{
	    	            				to = Integer.parseInt(peek(0).getValue());
	    	            				matchAndRemove(TokenType.NUMBER);
	    	            				
	    	            				matchAndRemove(TokenType.OF);
	    	            				
	    	            				switch(peek(0).getCharacter())
	    	        	            	{
	    	        	            	case INTEGER:
	    	        	            	{
	    	        	            		variable.add(new VariableNode(names.toString(), new IntergerNode() , false));
	    	                    	 		names.clear();
	    	                    		 	matchAndRemove(TokenType.INTEGER); 	
	    	        	            	}
	    	        	            	
	    	        	            		break;
	    	        	            		
	    	        	            	case REAL:
	    	        	            	{
	    	        	            		variable.add(new VariableNode(names.toString(), new RealNode() , false));
	    	                    	 		names.clear();
	    	                    	 		matchAndRemove(TokenType.REAL);	
	    	        	            	}
	    	        	            	
	    	        	            	break;
	    	        	            	
	    	        	            	case BOOLEAN:
	    	        	            	{
	    	        	            		variable.add(new VariableNode(names.toString(), new BooleanNode() , false));
	    	                    	 		names.clear();
	    	                    	 		matchAndRemove(TokenType.BOOLEAN);		
	    	        	            	}
	    	        	            	
	    	        	            	break;
	    	        	            	
	    	        	            	case CHARACTER:
	    	        	            	{
	    	                    	 		variable.add(new VariableNode(names.toString(), new CharacterNode() , false));
	    	                    	 		names.clear();
	    	                    	 		matchAndRemove(TokenType.CHARACTER);
	    	        	            	}
	    	        	            	
	    	        	            	break;
	    	        	            	
	    	        	            	case STRING:
	    	        	            	{
	    	        	            		variable.add(new VariableNode(names.toString(), new StringNode() , false));
	    	                    		 	names.clear();
	    	                    		 	matchAndRemove(TokenType.STRING);	
	    	        	            	}
	    	        	            	
	    	        	            	break;
										default:
											throw new IOException("Array must contain a primative datatype not" + peek(0).getCharacter());
	    	        	            	}
	    	            				
	    	            			}
	    	            			 
	    	            			else if(peek(0).getCharacter() != TokenType.NUMBER)
	    	            			{
	    	            				throw new IOException("A number is excpeted for inializing an array");
	    	            			}
								}
	            				
	            				else if(peek(0).getCharacter() != TokenType.TO)
		            			{
		            				throw new IOException("An array needs a 'to' to set the size of array");
		            			}
	            			
	            			}
	            			
	            			else if(peek(0).getCharacter() != TokenType.NUMBER)
	            			{
	            				throw new IOException("A number is excpeted for inializing an array");
	            			}
	            		}
	            		else
	            		{	
	            			variable.add(new VariableNode(names.toString(), new VariableNode() , false));
	            			names.clear();
	            			matchAndRemove(TokenType.ARRAY);	
         	 		
         	 		
     	            	
     	            	
     	            	}
	            	}
	            	
	            	default:
	            		throw new IOException("Variables must contain a primative datatype not " + peek(0).getCharacter());
		
	            	}
	            }
	            }
		 
		 else if(peek(0).getCharacter() != TokenType.IDENTIFIER)
		 {
			 throw new IOException("Variables must start with a name to be initialized not " + peek(0).getCharacter());
		 }
		 return variable;
	        }
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	private ArrayList<VariableNode> constantDeclarations() throws IOException
	{	
		ArrayList<VariableNode> constant = new ArrayList<>();
		ArrayList<String> names = new ArrayList<>();
		String string;
		char Char;
		boolean Booleans;
		int value;
	
	while(peek(0).getCharacter() == TokenType.IDENTIFIER)	
	{
		 if (peek(0).getCharacter() == TokenType.IDENTIFIER)
	        {        
	        	names.add(peek(0).getValue());
	            matchAndRemove(TokenType.IDENTIFIER);
	            if (peek(0).getCharacter() == Token.TokenType.EQUALS);
	            {
	            	matchAndRemove(TokenType.EQUALS);
	            	
	            	switch(peek(0).getCharacter())
	            	{
		            	case NUMBER:
		            	{
		            		value = Integer.parseInt(peek(0).getValue());
		            		constant.add(new VariableNode(names.toString(), new IntergerNode() , false, new IntergerNode(value)));
	            	 		names.clear();
	            		 	matchAndRemove(TokenType.NUMBER); 	
		            	}
		            	
		            		break;
		            		
		            	case STRINGLITERAL:
		            	{
		            		//System.out.println(peek(0).getValue());
		            		string = peek(0).getValue();
		            		constant.add(new VariableNode(names.toString(), new StringNode() , false, new StringNode(string)));
	            	 		names.clear();
	            	 		matchAndRemove(TokenType.STRINGLITERAL);	
		            	}
		            	
		            	break;
		            	
		            	case BOOLEAN:
		            	{
		            		Booleans = Boolean.parseBoolean(peek(0).getValue());
		            		constant.add(new VariableNode(names.toString(), new BooleanNode() , false, new BooleanNode(Booleans)));
	            	 		names.clear();
	            	 		matchAndRemove(TokenType.BOOLEAN);		
		            	}
		            	
		            	break;
		            	
		            	case CHARACTERLITERAL:
		            	{
		            		Char = peek(0).getValue().charAt(0);
	            	 		constant.add(new VariableNode(names.toString(), new CharacterNode() , false, new CharacterNode(Char)));
	            	 		names.clear();
	            	 		matchAndRemove(TokenType.CHARACTER);
		            	}
		            	
		            	break;
		            	
		            	default:
		            		throw new IOException("Constants must contain a primative datatype not " + peek(0).getCharacter());
		            	
	            	}
	         
		 
	            }
	        }
		 	if(peek(0).getCharacter() == TokenType.COMMA && peek(0).getCharacter() != TokenType.IDENTIFIER)
		 		{
		 			matchAndRemove(TokenType.COMMA);
		 		}
		 	
		
	}
return constant;
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	private ArrayList<VariableNode> parameterDeclarations() throws IOException
	{		
	    ArrayList<VariableNode> parameters = new ArrayList<>();
	    
	    ArrayList<String> names = new ArrayList<>();
	    
	    while (peek(0).getCharacter() != TokenType.RPAREN)
	    {
	        if (peek(0).getCharacter() == TokenType.IDENTIFIER)
	        {
	        
	        	names.add(peek(0).getValue());
	            matchAndRemove(TokenType.IDENTIFIER);
	           
	            while (peek(0).getCharacter() == Token.TokenType.COMMA)
	            {
	            	
     	            matchAndRemove(TokenType.COMMA);
     	            
	            	if (peek(0).getCharacter() == TokenType.IDENTIFIER)
	            		{            		
	            			names.add(peek(0).getValue());
	            			matchAndRemove(TokenType.IDENTIFIER);	
	            		}
	            	
	            	else if(peek(0).getCharacter() != TokenType.IDENTIFIER)
	            	{
	            		throw new IOException("Names are expected after a comma, what was seen was: " + peek(0).getCharacter());
	            	}
	            }
	            
	            if (peek(0).getCharacter() == Token.TokenType.COL);
	            {
	            	matchAndRemove(TokenType.COL);
	            	
	            	switch(peek(0).getCharacter())
	            	{
	            	case INTEGER:
	            	{
	            		parameters.add(new VariableNode(names.toString(), new IntergerNode() , false));
            	 		names.clear();
            		 	matchAndRemove(TokenType.INTEGER); 	
	            	}
	            	
	            		break;
	            		
	            	case REAL:
	            	{
	            		parameters.add(new VariableNode(names.toString(), new RealNode() , false));
            	 		names.clear();
            	 		matchAndRemove(TokenType.REAL);	
	            	}
	            	
	            	break;
	            	
	            	case BOOLEAN:
	            	{
	            		parameters.add(new VariableNode(names.toString(), new BooleanNode() , false));
            	 		names.clear();
            	 		matchAndRemove(TokenType.BOOLEAN);		
	            	}
	            	
	            	break;
	            	
	            	case CHARACTER:
	            	{
            	 		parameters.add(new VariableNode(names.toString(), new CharacterNode() , false));
            	 		names.clear();
            	 		matchAndRemove(TokenType.CHARACTER);
	            	}
	            	
	            	break;
	            	
	            	case STRING:
	            	{
	            		parameters.add(new VariableNode(names.toString(), new StringNode() , false));
            		 	names.clear();
            		 	matchAndRemove(TokenType.STRING);	
	            	}
	            	
	            	break;
	            	
	            	case ARRAY:
	            	{
	            		
	            		
            	 		
            	 		matchAndRemove(TokenType.ARRAY);	
            	 		
            	 		if (peek(0).getCharacter() == Token.TokenType.OF)
            	 		{
            	 			
            	 			matchAndRemove(Token.TokenType.OF);
            	 			
            	 			switch(peek(0).getCharacter())
        	            	{
        	            	case INTEGER:
        	            	{
        	            		parameters.add(new VariableNode(names.toString(), new IntergerNode(), -1, -1 , false, null));
                    	 		names.clear();
                    		 	matchAndRemove(TokenType.INTEGER); 	
        	            	}
        	            	
        	            		break;
        	            		
        	            	case REAL:
        	            	{
        	            		parameters.add(new VariableNode(names.toString(), new RealNode(),-1, -1 , false, null));
                    	 		names.clear();
                    	 		matchAndRemove(TokenType.REAL);	
        	            	}
        	            	
        	            	break;
        	            	
        	            	case BOOLEAN:
        	            	{
        	            		parameters.add(new VariableNode(names.toString(), new BooleanNode() , 1, -1 , false, null));
                    	 		names.clear();
                    	 		matchAndRemove(TokenType.BOOLEAN);		
        	            	}
        	            	
        	            	break;
        	            	
        	            	case CHARACTER:
        	            	{
                    	 		parameters.add(new VariableNode(names.toString(), new CharacterNode() , 1, -1 , false, null));
                    	 		names.clear();
                    	 		matchAndRemove(TokenType.CHARACTER);
        	            	}
        	            	
        	            	break;
        	            	
        	            	case STRING:
        	            	{
        	            		parameters.add(new VariableNode(names.toString(), new StringNode() , 1, -1 , false, null));
                    		 	names.clear();
                    		 	matchAndRemove(TokenType.STRING);	
        	            	}
        	            	
        	            	break;
        	            	
        	            	default:
        	            		throw new IOException("Array must contain a primative datatype not " + peek(0).getCharacter());
            	 		}
	            	}
	            	
	            }
	            	break;
	            	
	            	default:
	            		throw new IOException("Cannot Initialize a parameter with" + peek(0).getCharacter());
	            	}
	            }
	            
	            
	            if(peek(0).getCharacter() == Token.TokenType.SEMICOL)
	            {
	            	
	            	matchAndRemove(TokenType.SEMICOL);
	            	
	            }
	      }
	            
	            
	      
	            
	        
	     
	        else if (peek(0).getCharacter() == TokenType.VAR)
	        {
	            
	        	 matchAndRemove(TokenType.VAR);
	        	
	        	names.add(peek(0).getValue());
	            matchAndRemove(TokenType.IDENTIFIER);
	           // System.out.println(peek(0));
	            
	            while (peek(0).getCharacter() == Token.TokenType.COMMA)
	            {
	            	
	            	//parameters.add(new VariableNode(peek(0).getValue(), new StringNode() , false));
     	            matchAndRemove(TokenType.COMMA);
     	            
	            	if (peek(0).getCharacter() == TokenType.IDENTIFIER)
	            		{            		
	            			names.add(peek(0).getValue());
	            			matchAndRemove(TokenType.IDENTIFIER);	
	            		}
	            	
	            		
	            }
	            
	            if (peek(0).getCharacter() == Token.TokenType.COL);
	            {
	            	matchAndRemove(TokenType.COL);
	            
     	            matchAndRemove(TokenType.COMMA);
     	            
	            	if (peek(0).getCharacter() == TokenType.IDENTIFIER)
	            		{            		
	            			names.add(peek(0).getValue());
	            			matchAndRemove(TokenType.IDENTIFIER);	
	            		}
	            }
	            
	            if (peek(0).getCharacter() == Token.TokenType.COL);
	            {
	            	matchAndRemove(TokenType.COL);
	            	
	            	switch(peek(0).getCharacter())
	            	{
	            	case INTEGER:
	            	{
	            		parameters.add(new VariableNode(names.toString(), new IntergerNode() , true));
            	 		names.clear();
            		 	matchAndRemove(TokenType.INTEGER); 	
	            	}
	            	
	            		break;
	            		
	            	case REAL:
	            	{
	            		parameters.add(new VariableNode(names.toString(), new RealNode() , true));
            	 		names.clear();
            	 		matchAndRemove(TokenType.REAL);	
	            	}
	            	
	            	break;
	            	
	            	case BOOLEAN:
	            	{
	            		parameters.add(new VariableNode(names.toString(), new BooleanNode() , true));
            	 		names.clear();
            	 		matchAndRemove(TokenType.BOOLEAN);		
	            	}
	            	
	            	break;
	            	
	            	case CHARACTER:
	            	{
            	 		parameters.add(new VariableNode(names.toString(), new CharacterNode() , true));
            	 		names.clear();
            	 		matchAndRemove(TokenType.CHARACTER);
	            	}
	            	
	            	break;
	            	
	            	case STRING:
	            	{
	            		parameters.add(new VariableNode(names.toString(), new StringNode() , true));
            		 	names.clear();
            		 	matchAndRemove(TokenType.STRING);	
	            	}
	            	
	            	break;
	            	
	            	case ARRAY:
	            	{
	            		
	            		
            	 		
            	 		if (peek(0).getCharacter() == Token.TokenType.OF)
            	 		{
            	 			
            	 			matchAndRemove(Token.TokenType.OF);
            	 			
            	 			switch(peek(0).getCharacter())
        	            	{
        	            	case INTEGER:
        	            	{
        	            		parameters.add(new VariableNode(names.toString(), new IntergerNode() , 1, -1 , true, null));
                    	 		names.clear();
                    		 	matchAndRemove(TokenType.INTEGER); 	
        	            	}
        	            	
        	            		break;
        	            		
        	            	case REAL:
        	            	{
        	            		parameters.add(new VariableNode(names.toString(), new RealNode() , 1, -1 , true, null));
                    	 		names.clear();
                    	 		matchAndRemove(TokenType.REAL);	
        	            	}
        	            	
        	            	break;
        	            	
        	            	case BOOLEAN:
        	            	{
        	            		parameters.add(new VariableNode(names.toString(), new BooleanNode() , 1, -1 , true, null));
                    	 		names.clear();
                    	 		matchAndRemove(TokenType.BOOLEAN);		
        	            	}
        	            	
        	            	break;
        	            	
        	            	case CHARACTER:
        	            	{
                    	 		parameters.add(new VariableNode(names.toString(), new CharacterNode() , 1, -1 , true, null));
                    	 		names.clear();
                    	 		matchAndRemove(TokenType.CHARACTER);
        	            	}
        	            	
        	            	break;
        	            	
        	            	case STRING:
        	            	{
        	            		parameters.add(new VariableNode(names.toString(), new StringNode() , 1, -1 , true, null));
                    		 	names.clear();
                    		 	matchAndRemove(TokenType.STRING);	
        	            	}
        	            	
        	            	break;
            	 		
        	            	default:
        	            		throw new IOException("Array must contain a primative datatype not " + peek(0).getCharacter());
            	 		}
	            	}
	            	
	            	break;
	            	}
	            	
	            	default:
	            		throw new IOException("Cannot Initialize a parameter with" + peek(0).getCharacter());
	            	}
	            }      
	            
	            if(peek(0).getCharacter() == Token.TokenType.SEMICOL && peek(0).getCharacter() != Token.TokenType.IDENTIFIER)
	            {
	            	
	            	matchAndRemove(TokenType.SEMICOL);
	            	
	            }
	      
	            
	        }  
	      
	}
		
	    return parameters;
	
	}
}

