package Lex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Lex.Token.TokenType;

public class Interpreter 
{
	
	private HashMap<String, IDT> variables;
	private ProgramNode node;
	
	public Interpreter(ProgramNode root) 
	{
		node = root;
		// TODO Auto-generated constructor stub
	}


	/**
	 * The method first adds the constants and variables defined within the function to a HashMap called variables. 
	 * Then it interprets the block of statements within the function using the InterpretBlock method with the variables hashmap as input.
	 * 
	 * @param function
	 * @throws IOException
	 */
	private void InterpretFunction(FunctionNode function) throws IOException
	{
		
		 // Add local variables to the hashmap
        for (VariableNode constantNode : function.getConstants()) 
        {
            IDT idt = constantNodes(constantNode);
            variables.put(constantNode.getVariable(), idt);
        }     
       
        // Add local variables to variables map
        for (VariableNode var : function.getVariables())
        {
        	IDT idt = constantNodes(var);
            variables.put((String)var.getVariable(), idt);
        }

        // Interpret the block of statements with the local variables hashmap
        InterpretBlock(function.getStatements(), variables);
        
      
    }

	
	/**
	 * This method interprets a block of statements with local variables.
	 *  It iterates over a list of statements.
	 *  For each statement, it checks its type and calls the appropriate interpretation method.
	 * 
	 * @param statements
	 * @param variables
	 * @throws IOException
	 */
	private void InterpretBlock(ArrayList<StatementNode> statements, HashMap<String, IDT> variables) throws IOException
	{
		for (StatementNode statement : statements) 
		{
    
            if (statement instanceof IfNode) 
            {
                InterpretIf((IfNode) statement, variables);
            } 
            
            else if (statement instanceof WhileNode) 
            {
                InterpretWhile((WhileNode) statement, variables);
            } 
            
            else if (statement instanceof RepeatNode)
            {
                InterpretRepeat((RepeatNode) statement, variables);
            } 
            
            else if (statement instanceof ForNode)
            {
                InterpretFor((ForNode) statement, variables);
            }
            
            else if (statement instanceof AssignmentNode) 
            {
                AssignmentNode((AssignmentNode) statement, variables);
            } 
            
            else if (statement instanceof FunctionCallNode) 
           {
               FunctionCalls((FunctionCallNode) statement, variables);
           }
            
            else 
            {
                throw new IOException("Unknown statement type: " + statement.getClass().getSimpleName());
            }
        }
    }

	
	/**
	 * This is a method that evaluates expressions based on the given nodes and variables. 
	 * It first checks whether the node is a MathOpNode, VariableReferenceNode, or a constant node
	 *  (IntergerNode, RealNode, or StringNode). 
	 *  If it is a MathOpNode, it interprets the left and right nodes, checks their data types, and returns the result. 
	 *  If it is a VariableReferenceNode, it retrieves the variable value from the hashmap and returns it. 
	 *  If it is a constant node, it evaluates the constant and returns it.
	 * 
	 * @param node
	 * @param variables
	 * @return
	 * @throws IOException
	 */
	public IDT expression(Node node,  HashMap<String, IDT> variables) throws IOException
	{
		if (node instanceof MathOpNode) 
		{
	        MathOpNode mathOpNode = (MathOpNode) node;
	        IDT left = expression(mathOpNode.getLeft(), variables);
	        IDT right = expression(mathOpNode.getRight(), variables);
	        
	        if (left instanceof IntegerDataType && right instanceof IntegerDataType) 
	        {          
	        	IntegerDataType result = (IntegerDataType)  InterpretMathOpNode(mathOpNode, variables);
	            return result;
	        } 
	        
	        else if (left instanceof RealDataType && right instanceof RealDataType) 
	        {
	        	RealDataType result = (RealDataType)InterpretMathOpNode(mathOpNode, variables);
	            return result;
	        }
	        
	        else if (left instanceof StringDataType && right instanceof StringDataType ) 
	        {          
	        	StringDataType result = (StringDataType) InterpretMathOpNode(mathOpNode, variables);
	        	  return result;
	        } 
	        
	        else 
	        {
	            // Types are not compatible
	            throw new IOException("Invalid expression: types are not compatible");
	        }
	    } 
		
		else if (node instanceof VariableReferenceNode) 
		{
	       
			IDT variableValue = VariableReferenceNode((VariableReferenceNode) node,variables);
			
	      //  String variableName = ((VariableReferenceNode) node).getName();
	      // IDT variableValue = variables.get(variableName);
	        
	        if (variableValue instanceof IntegerDataType) 
	        {
	            return (IntegerDataType) variableValue;
	        } 
	        
	        else if (variableValue instanceof RealDataType)
	        {
	        	return (RealDataType) variableValue;
	        } 
	        
	        else if (variableValue instanceof StringDataType)
	        {
	        	return (StringDataType) variableValue;
	        } 
	        
	        else 
	        {
	            throw new IOException("Invalid variable type: " + variableValue.getClass().getSimpleName());
	        }
	    } 
		
		else if (node instanceof IntergerNode || node instanceof RealNode || node instanceof StringNode) 
		{
			IDT constant = constantNodes(node);
			
			 if (constant instanceof IntegerDataType) 
		        {
		            return (IntegerDataType) constant;
		        } 
		        
		        else if (constant instanceof RealDataType)
		        {
		        	return (RealDataType) constant;
		        } 
		        
		        else if (constant instanceof StringDataType)
		        {
		        	return (StringDataType) constant;
		        } 
			 
		        else 
		        {
		            throw new IOException("Invalid consants type: " + constant.getClass().getSimpleName());
		        }
			
	       
	    } 
		
		else
	    {
	        throw new IOException("Invalid node type: " + node.getClass().getSimpleName());
	    }
	}
	
	/**
	 * This function interprets a Boolean comparison between two expressions by evaluating their values and returning 
	 * a BooleanDataType object representing the result.
	 *  It first checks if both expressions are integers, floats, or strings and then applies the corresponding
	 * comparison operator to their values.
	 * 
	 * @param node
	 * @param variables
	 * @return
	 * @throws IOException
	 */
	public BooleanDataType interpretBooleanCompare(BooleanCompareNode node, HashMap<String, IDT> variables) throws IOException
	{
	    IDT left = expression(node.getLeft(), variables);
	    IDT right = expression(node.getRight(), variables);
	    
	    if (left instanceof IntegerDataType && right instanceof IntegerDataType) 
	    {
	        int leftValue = ((IntegerDataType) left).getInt();
	        int rightValue = ((IntegerDataType) right).getInt();
	        switch (node.getType())
	        {
	            case EQUALS:
	                return new BooleanDataType(leftValue == rightValue);
	            case NOTEQUAL:
	                return new BooleanDataType(leftValue != rightValue);
	            case GREATERTHAN:
	                return new BooleanDataType(leftValue > rightValue);
	            case GREATOREQUAL:
	                return new BooleanDataType(leftValue >= rightValue);
	            case LESSTHAN:
	                return new BooleanDataType(leftValue < rightValue);
	            case LESSOREQUAL:
	                return new BooleanDataType(leftValue <= rightValue);
	            default:
	                throw new IOException("Unknown boolean operator: " + node.getType());
	        }
	    }
	    
	    else if (left instanceof RealDataType && right instanceof RealDataType) 
	    {
	        float leftValue = ((RealDataType) left).getFloat();
	        float rightValue = ((RealDataType) right).getFloat();
	        switch (node.getType()) 
	        {
	            case EQUALS:
	                return new BooleanDataType(leftValue == rightValue);
	            case NOTEQUAL:
	                return new BooleanDataType(leftValue != rightValue);
	            case GREATERTHAN:
	                return new BooleanDataType(leftValue > rightValue);
	            case GREATOREQUAL:
	                return new BooleanDataType(leftValue >= rightValue);
	            case LESSTHAN:
	                return new BooleanDataType(leftValue < rightValue);
	            case LESSOREQUAL:
	                return new BooleanDataType(leftValue <= rightValue);
	            default:
	                throw new IOException("Unknown boolean operator: " + node.getType());
	        }
	    } 
	   
	    
	    else if (left instanceof StringDataType && right instanceof StringDataType ) 
	    {
	        String leftValue = ((StringDataType ) left).getValue();
	        String rightValue = ((StringDataType ) right).getValue();
	        switch (node.getType())
	        {
	            case EQUALS:
	                return new BooleanDataType(leftValue.equals(rightValue));
	            case NOTEQUAL:
	                return new BooleanDataType(!leftValue.equals(rightValue));
	            default:
	                throw new IOException("Unknown boolean operator: " + node.getType());
	        }
	    }
	    
	    else 
	    {
	        throw new IOException("Incompatible types for boolean compare: " + left.getClass() + " and " + right.getClass());
	    }
	}
	
	/**
	 * Interpret a variable reference node and retrieve its value from the HashMap of variables
	 * 
	 * @param node
	 * @param variables
	 * @return
	 * @throws IOException
	 */
	private IDT VariableReferenceNode (VariableReferenceNode node, HashMap<String, IDT> variables) throws IOException
	{
		String name = node.getName();
		
	    if (!variables.containsKey(name)) 
	    {
	        throw new IOException("Variable " + name + " is not defined.");
	    }
	    
	    IDT index = new IntegerDataType(((IntergerNode)node.getIndex()).getItem());
	    String varName =  ((StringDataType)variables.get(name)).getValue();

	    if(((IntergerNode)node.getIndex()).getItem() == 0) 
	    {
	    	return variables.get(varName);
	    }
	    
	    	return variables.getOrDefault(varName,index);
  
	   
	}
	
	/**
	 * This method interprets a mathematical operation node by evaluating the left and right operands and 
	 * performing the corresponding operation based on the operator token.
	 *  It handles integer, real, and string data types and throws exceptions for invalid types, division by zero, and invalid operators.
	 * 
	 * @param node
	 * @param variables
	 * @return
	 * @throws IOException
	 */
	public IDT InterpretMathOpNode(MathOpNode node, HashMap<String, IDT> variables) throws IOException
	{
		  IDT left = expression(node.getLeft(), variables);
		  IDT right = expression(node.getRight(), variables);
		  
		  if (!(left instanceof IntegerDataType|| left instanceof RealDataType || left instanceof StringDataType)) 
		  {
		    throw new IOException("Left side of MathOpNode is not a valid type.");
		  }
		  
		  if (!(right instanceof IntegerDataType|| right instanceof RealDataType || right instanceof StringDataType)) 
		  {
		    throw new IOException("Right side of MathOpNode is not a valid type.");
		  }
		  
		  if (left.getClass() != right.getClass()) 
		  {
		    throw new IOException("Both sides of MathOpNode are not the same type.");
		  }
		  
		  if (left instanceof IntegerDataType && right instanceof IntegerDataType) 
		  {
		    int leftValue = ((IntegerDataType) left).getInt();
		    int rightValue = ((IntegerDataType) right).getInt();
		    
		    switch (node.getToken())
		    {
		      case PLUS:
		        return new IntegerDataType(leftValue + rightValue);
		     
		      case MINUS:
		        return new IntegerDataType(leftValue - rightValue);
		     
		      case TIMES:
		        return new IntegerDataType(leftValue * rightValue);
		     
		      case DIVIDE:
		        if (rightValue == 0) 
		        {
		          throw new IOException("Division by zero.");
		        }
		        return new RealDataType((float) leftValue / rightValue);
		     
		      case MOD:
		        if (rightValue == 0) 
		        {
		          throw new RuntimeException("Modulo by zero.");
		        }
		        return new IntegerDataType(leftValue % rightValue);
		    
		      default:
		        throw new IOException("Invalid operator for MathOpNode.");
		    }
		  } 
		  
		  else if (left instanceof RealDataType && right instanceof RealDataType) 
		  {
		    float leftValue = ((RealDataType) left).getFloat();
		    float rightValue = ((RealDataType) right).getFloat();
		    
		    switch (node.getToken()) 
		    {
		      case PLUS:
		        return new RealDataType(leftValue + rightValue);
		        
		      case MINUS:
		       return new RealDataType(leftValue - rightValue);
		        
		      case TIMES:
		        return new RealDataType(leftValue * rightValue);
		        
		      case DIVIDE:
		        if (rightValue == 0)
		        {
		          throw new IOException("Division by zero.");
		        }
		        return new RealDataType(leftValue / rightValue);
		     
		      case MOD:
			        if (rightValue == 0) 
			        {
			          throw new RuntimeException("Modulo by zero.");
			        }
			        return new RealDataType(leftValue % rightValue);
			        
		      default:
		        throw new IOException("Invalid operator for MathOpNode.");
		    }
		  } 
		  
		  else if (left instanceof StringDataType && right instanceof StringDataType)
		  {
		    String leftValue = ((StringDataType) left).getValue();
		    String rightValue = ((StringDataType) right).getValue();
		    
		    switch (node.getToken()) 
		    {
		      case PLUS:
		        return new StringDataType(leftValue + rightValue);
		       
		      default:
		        throw new IOException("Invalid operator for MathOpNode.");
		    }
		  } 
		  
		  else 
		  {
		    throw new IOException("Unsupported types for MathOpNode.");
		  }
		}
	
	public void FunctionCalls(FunctionCallNode FunctionNode, HashMap<String, IDT> variables) throws IOException
	{
		String functionName = FunctionNode.getFunctionName();
	    FunctionNode functionDefinition = node.getFunction(functionName);

	    // locate the function definition
	    if (functionDefinition == null)
	    {
	        throw new IOException("Function " + functionName + " is not defined");
	    }

	    // check number of parameters
	    ArrayList<ParameterNode> parameterNodes = FunctionNode.getParameters();
	    ArrayList<VariableNode> variableDeclarations = functionDefinition.getParameters();
	    
	    if (variableDeclarations.size() != parameterNodes.size() && !functionDefinition.isVariadic())
	    {
	        throw new IOException("Function " + functionName + " expects " + variableDeclarations.size() + " , " + parameterNodes.size() + " was provided");
	    }
	    
	    ArrayList<IDT> parameterValues = new ArrayList<>();
	    
	    for (int i = 0; i < parameterNodes.size(); i++) 
	    {
	        ParameterNode parameterNode = parameterNodes.get(i);
	        IDT parameterValue = expression(parameterNode, variables);
	        
	        if(parameterValue instanceof IntegerDataType)
	        {
	        	 IDT copyPValue =  new IntegerDataType(((IntegerDataType) parameterValue).getInt());
	        	 parameterValues.add(copyPValue);
	        }
	        
	        else if(parameterValue instanceof RealDataType)
	        {
	        	IDT copyPValue =  new RealDataType(((RealDataType) parameterValue).getFloat());
	        	 parameterValues.add(copyPValue);
	        }
	        
	        else if(parameterValue instanceof BooleanDataType)
	        {
	        	 IDT copyPValue =  new BooleanDataType(((BooleanDataType) parameterValue).getBool());	  
	        	 parameterValues.add(copyPValue);
	        }
	        
	        else if(parameterValue instanceof StringDataType)
	        {
	        	 IDT copyPValue =  new StringDataType(((StringDataType) parameterValue).getValue());
	        	 parameterValues.add(copyPValue);
	        }
	        
	        else if(parameterValue instanceof CharacterDataType)
	        {
	        	IDT copyPValue =  new CharacterDataType(((CharacterDataType) parameterValue).getValue());
	        	parameterValues.add(copyPValue);
	        }
	       
	       
	    }
	    
	    IDT result;
	    
	    if (functionDefinition instanceof FunctionNode) 
	    {
	       InterpretFunction((FunctionNode) functionDefinition);
	    } 
	    
	    else 
	    {
	        ((FunctionNode) functionDefinition).execute(parameterValues);
	    }
	  
	    
	    
	    ArrayList<String> parameterNames = new ArrayList<>(); 
	    parameterNames.add(functionDefinition.getName());
	    
	    boolean isVardic = functionDefinition.isVariadic();
	    boolean isVar = FunctionCallNode.isVar();
	    
	    for (int i = 0; i < parameterNames.size(); i++)
	    {
	        String parameterName = parameterNames.get(i);
	        IDT parameterValue = variables.get(parameterName);
	        IDT argumentValue = ( parameterValues).get(i);
	        
	       // StringDataType(((StringNode)constant).getString())    
	        
	        if (isVardic || (functionDefinition.isVariadic() && isVar))
	        {
	            ((StringDataType) parameterValue).setValue(argumentValue.ToString());
	        }
	    }
	    
	   
	}
	   

	
	
	/**
	 * Interprets a for loop by getting the start, end, and step values from the nodes, 
	 * iterating over the values, and executing the block for each iteration. 
	 * The current iteration value is stored in the variableName in the variables HashMap.
	 * @param node
	 * @param variables
	 * @throws IOException
	 */
	public void InterpretFor(ForNode node, HashMap<String, IDT> variables) throws IOException 
	{
	    String variableName = node.getName();
	    
	    IDT start = (IntegerDataType) expression(node.getFrom(), variables);
	    IDT end = (IntegerDataType) expression(node.getTo(), variables);
	    IDT step = (IntegerDataType) expression(node.getStepValue(), variables);
	    
	    if (!(start.getType() instanceof IntegerDataType && end.getType() instanceof IntegerDataType && step.getType() instanceof IntegerDataType)) 
	    {
	        throw new IOException("For loop requires integer start, end, and step values.");
	    }
	    
	    int startValue = (int) start.getType();
	    int endValue = (int) end.getType();
	    int stepValue = (int) step.getType();
	    
	    for (int i = startValue; i <= endValue; i += stepValue)
	    {
	        variables.put(variableName, new IntegerDataType(i));
	        InterpretBlock(node.getStatements(), variables);
	    }
	}
	
	
	/**
	 * first extracts the condition from the IfNode object, 
	 * and evaluates it by calling the expression method.
	 *  If the condition evaluates to true, 
	 *  the method calls the InterpretBlock method to interpret the statements in the if block.
	 *  If the condition evaluates to false and there is an "else if" block present, 
	 *  the method calls itself recursively with the "else if" block. If there is no "else if" block, 
	 *  the method simply returns without interpreting any further statements.
	 *  
	 * @param node
	 * @param variables
	 * @throws IOException
	 */
	public void InterpretIf(IfNode node, HashMap<String, IDT> variables) throws IOException 
	{
		
	    BooleanCompareNode boolNode = node.getCondition();
	    boolean result = (boolean)expression(boolNode, variables).getType();
	    
	    if (result) 
	    {
	        InterpretBlock(node.getStatements(), variables);
	    } 
	    
	    else
	    {
	        IfNode nextNode = null;
	        nextNode = node.setElse(nextNode);
	        if (nextNode != null) 
	        {
	            InterpretIf(nextNode, variables);
	        }
	        
	    }
	}

	/**
	 * This method executes a while loop by interpreting its condition and block of statements. 
	 * The condition is extracted from the WhileNode object, 
	 * and the block is executed repeatedly as long as the condition evaluates to true. 
	 * The statements inside the block are interpreted by calling the InterpretBlock method.
	 * 
	 * @param node
	 * @param variables
	 * @throws IOException
	 */
	public void InterpretWhile(WhileNode node, HashMap<String, IDT> variables) throws IOException 
	{
	    BooleanCompareNode boolNode = node.getCondition();
	    
	    while ((boolean)expression(boolNode, variables).getType())
	    {
	        InterpretBlock(node.getStatements(), variables);
	    }
	}

	/**
	 * This method interprets a repeat-until loop by interpreting its block of statements a specified number of times. 
	 * The number of times to repeat is determined by evaluating the condition stored in the RepeatNode object.
	 *  The method enters a loop that iteratively executes the block of statements, reducing the count by 1 with each iteration, until the count reaches 0. 
	 *  The statements inside the block are interpreted by calling the InterpretBlock method.
	 *  
	 * @param node
	 * @param variables
	 * @throws IOException
	 */
	public void InterpretRepeat(RepeatNode node, HashMap<String, IDT> variables) throws IOException 
	{
	    int count = (int)expression(node.getCondition(), variables).getType();
	    
	    while (count > 0) 
	    {
	        InterpretBlock(node.getStatements(), variables);
	        count--;
	    }
	}
	
	/**
	 * The method first extracts the name of the variable and the new value from the AssignmentNode object. 
	 * It then checks if the variable is present in the HashMap of variables. 
	 * If the variable is present, the method updates its value in the HashMap with the new value.
	 * 
	 * @param node
	 * @param variables
	 * @throws IOException
	 */
	private void AssignmentNode (AssignmentNode node, HashMap<String, IDT> variables) throws IOException
	{
			String variableName = ((StringNode)node.getValue()).getString();
		    IDT newValue = expression(node.getTarget(), variables);

		    if (!variables.containsKey(variableName)) 
		    {
		        throw new IOException("Variable " + variableName + " not found!");
		    }

		    variables.put(variableName, newValue);
	}
	
	/**
	 * The method checks the type of the constant value , 
	 * and creates a new IDT object of the corresponding type
	 * (IntegerDataType, RealDataType, StringDataType, or BooleanDataType).
	 * 
	 * @param constant
	 * @return
	 * @throws IOException
	 */
	private IDT constantNodes(Node constant) throws IOException 
	{
	    if (constant instanceof IntergerNode) 
	    {
	        return new IntegerDataType(((IntergerNode)constant).getItem());
	    } 
	    
	    else if (constant instanceof RealNode) 
	    {
	        return new RealDataType(((RealNode) constant).getItem());
	    } 
	    
	    else if (constant instanceof StringNode) 
	    {
	        return new StringDataType(((StringNode)constant).getString());
	    } 
	    
	    else if (constant instanceof BooleanNode) 
	    {
	        return new BooleanDataType(((BooleanNode)constant).getBoolean());	    } 
	    
	    else 
	    {
	        throw new IOException("Unknown constant type: " + constant.getClass().getName());
	    }
	}
	
}
