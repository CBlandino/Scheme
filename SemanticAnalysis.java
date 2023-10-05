package Lex;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;



public class SemanticAnalysis 
{
	private HashMap<String, IDT> variables;
	private ProgramNode root;
	private FunctionNode function;

	
	
	public SemanticAnalysis(ProgramNode root) throws IOException 
	{
		this.root = root;
		getAllFunctions();
		// TODO Auto-generated constructor stub
	}
	
	

	 private void getAllFunctions() throws IOException
	 {
		 
		// TODO Auto-generated method stub
		for(int i = 0; i < root.getName().size(); i++)
		{
			function= root.getFunction(root.Name.get(i));
			CheckAssignmentBlock(function.getStatements(), variables);
		}
	}


	/**
	 * Checks if a block of statements containing assignment operations is valid by
	 * recursively checking each statement in the block.
	 * @param statements An ArrayList of StatementNode objects representing the statements to be checked.
	 * @param variables  A HashMap representing the current variable assignments.
	 * @throws IOException If an invalid statement is encountered, or if an assignment operation is invalid.
	 */
	private void CheckAssignmentBlock(ArrayList<StatementNode> statements, HashMap<String, IDT> variables)
			throws IOException
	{
		// Iterate through each statement in the block
		for (StatementNode statement : statements) 
		{
			// If the statement is an IfNode, recursively check the statements in the
			// IfNode's block and any else block
			if (statement instanceof IfNode)
			{
				IfNode IF = (IfNode) statement;
				CheckAssignmentBlock(IF.getStatements(), variables);

				IF.setElse(IF);
				if (IF.setElse(IF) != null) 
				{
					CheckAssignmentBlock(IF.getStatements(), variables);
				}
			}

			// If the statement is a RepeatNode, recursively check the statements in the
			// RepeatNode's block
			else if (statement instanceof RepeatNode) 
			{
				RepeatNode repeat = (RepeatNode) statement;
				CheckAssignmentBlock(repeat.getStatements(), variables);
			}

			// If the statement is a ForNode, recursively check the statements in the
			// ForNode's block
			else if (statement instanceof ForNode) 
			{
				ForNode For = (ForNode) statement;
				CheckAssignmentBlock(For.getStatements(), variables);
			}

			// If the statement is a WhileNode, recursively check the statements in the
			// WhileNode's block
			else if (statement instanceof WhileNode)
			{
				WhileNode While = (WhileNode) statement;
				CheckAssignmentBlock(While.getStatements(), variables);
			}

			// If the statement is an AssignmentNode, check if the assignment operation is
			// valid
			else if (statement instanceof AssignmentNode)
			{
				AssignmentNode Assign = (AssignmentNode) statement;
				CheckAssignment(Assign, variables);
			}

			// If the statement is not a valid statement, throw an exception with an
			// appropriate message
			else
				throw new IOException("Invalid Statement: " + statement.toString());

		}
	}

	/**
	 * Checks if an assignment operation is valid by comparing the type of the
	 * target and value expressions. 
	 * @param assignment The AssignmentNode representing the assignment operation to be checked.
	 * @param variables  A HashMap representing the current variable assignments. 
	 * @throws IOException If the types of the target and value expressions are not compatible.
	 */
	private void CheckAssignment(AssignmentNode assignment, HashMap<String, IDT> variables) throws IOException 
	{
		// Get the name of the variable being assigned
		String variableName = (String) ((VariableReferenceNode) assignment.getValue()).getName();

		// Evaluate the right-hand side expression and the left-hand side expression
		Node rightValue = expression(assignment.getValue(), variables);
		Node leftValue = expression(assignment.getTarget(), variables);

		// Check if the types of the target and value expressions are compatible
		if ((leftValue.getClass() != rightValue.getClass()))
		{
			// Types are not compatible, so throw an exception with an appropriate message
			throw new IOException("Invalid assignment: types are not compatible for variable ' " + variableName + 
					" ', comparing " + leftValue.getClass() + " to " + rightValue.getClass());
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
	public Node expression(Node node,  HashMap<String, IDT> variables) throws IOException
	{
		if (node instanceof MathOpNode) 
		{
	        MathOpNode mathOpNode = (MathOpNode) node;
	        Node left = expression(mathOpNode.getLeft(), variables);
	        Node right = expression(mathOpNode.getRight(), variables);
	        
	        if (left instanceof IntergerNode && right instanceof IntergerNode) 
	        {          
	        	IntergerNode result = (IntergerNode)  InterpretMathOpNode(mathOpNode, variables);
	            return result;
	        } 
	        
	        else if (left instanceof RealNode && right instanceof RealNode) 
	        {
	        	RealNode result = (RealNode)InterpretMathOpNode(mathOpNode, variables);
	            return result;
	        }
	        
	        else if (left instanceof StringNode && right instanceof StringNode) 
	        {          
	        	StringNode result = (StringNode) InterpretMathOpNode(mathOpNode, variables);
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
	       
			VariableNode variableValue = VariableReferenceNode((VariableReferenceNode) node,variables);
			
	      //  String variableName = ((VariableReferenceNode) node).getName();
	      // IDT variableValue = variables.get(variableName);
	        
	        if (variableValue.getType() instanceof IntergerNode) 
	        {
	            return variableValue.getType();
	        } 
	        
	        else if (variableValue.getType() instanceof RealNode)
	        {
	        	return variableValue.getType();
	        } 
	        
	        else if (variableValue.getType() instanceof StringNode)
	        {
	        	return variableValue.getType();
	        } 
	        
	        else 
	        {
	            throw new IOException("Invalid variable type: " + variableValue.getClass().getSimpleName());
	        }
	    } 
		
		else if (node instanceof IntergerNode || node instanceof RealNode || node instanceof StringNode) 
		{
			Node constant = constantNodes(node);
			
			 if (constant instanceof IntergerNode) 
		        {
		            return (IntergerNode) constant;
		        } 
		        
		        else if (constant instanceof RealNode)
		        {
		        	return (RealNode) constant;
		        } 
		        
		        else if (constant instanceof StringNode)
		        {
		        	return (StringNode) constant;
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
	 * Interpret a variable reference node and retrieve its value from the HashMap of variables
	 * 
	 * @param node
	 * @param variables
	 * @return
	 * @throws IOException
	 */
	private VariableNode VariableReferenceNode (VariableReferenceNode node, HashMap<String, IDT> variables) throws IOException
	{
		String name = node.getName();
		
		
	   for(int i = 0; i < function.getParameters().size(); i++)
	   {
		   if(name.equals(function.getParameters().get(i).getName()))
		   {
			return function.getParameters().get(i);   
		   }
	   }
	   
	   for(int i = 0; i < function.getConstants().size(); i++)
	   {
		   if(name.equals(function.getConstants().get(i).getName()))
		   {
			return function.getConstants().get(i);   
		   }
	   }
	   
	   for(int i = 0; i < function.getVariables().size(); i++)
	   {
		   
		   if(name.equals(function.getVariables().get(i).getName()))
		   {
			return function.getVariables().get(i);   
		   }
	   }
	   
	  throw new IOException("Variable doesn't exist");
	
//	    IDT index = new IntegerDataType(((IntergerNode)node.getIndex()).getItem());
//	    String varName =  ((StringDataType)variables.get(name)).getValue();
//
//	    if(((IntergerNode)node.getIndex()).getItem() == 0) 
//	    {
//	    	return variables.get(varName);
//	    }
//	    
//	    	return variables.getOrDefault(varName,index);
  
	   
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
	public Node InterpretMathOpNode(MathOpNode node, HashMap<String, IDT> variables) throws IOException
	{
		  Node left = expression(node.getLeft(), variables);
		  Node right = expression(node.getRight(), variables);
		  
		  if (!(left instanceof IntergerNode|| left instanceof RealNode || left instanceof StringNode)) 
		  {
		    throw new IOException("Left side of MathOpNode is not a valid type.");
		  }
		  
		  if (!(right instanceof IntergerNode|| right instanceof RealNode || right instanceof StringNode)) 
		  {
		    throw new IOException("Right side of MathOpNode is not a valid type.");
		  }
		  
		  if (left.getClass() != right.getClass()) 
		  {
		    throw new IOException("Both sides of MathOpNode are not the same type.");
		  }
		  
		  if (left instanceof IntergerNode && right instanceof IntergerNode) 
		  {
		    int leftValue = ((IntergerNode) left).getItem();
		    int rightValue = ((IntergerNode) right).getItem();
		    
		    switch (node.getToken())
		    {
		      case PLUS:
		        return new IntergerNode(leftValue + rightValue);
		     
		      case MINUS:
		        return new IntergerNode(leftValue - rightValue);
		     
		      case TIMES:
		        return new IntergerNode(leftValue * rightValue);
		     
		      case DIVIDE:
		        if (rightValue == 0) 
		        {
		          throw new IOException("Division by zero.");
		        }
		        return new RealNode((float) leftValue / rightValue);
		     
		      case MOD:
		        if (rightValue == 0) 
		        {
		          throw new IOException("Modulo by zero.");
		        }
		        return new IntergerNode(leftValue % rightValue);
		    
		      default:
		        throw new IOException("Invalid operator for MathOpNode.");
		    }
		  } 
		  
		  else if (left instanceof RealNode && right instanceof RealNode ) 
		  {
		    float leftValue = ((RealNode) left).getItem();
		    float rightValue = ((RealNode) right).getItem();
		    
		    switch (node.getToken()) 
		    {
		      case PLUS:
		        return new RealNode(leftValue + rightValue);
		        
		      case MINUS:
		       return new RealNode(leftValue - rightValue);
		        
		      case TIMES:
		        return new RealNode(leftValue * rightValue);
		        
		      case DIVIDE:
		        if (rightValue == 0)
		        {
		          throw new IOException("Division by zero.");
		        }
		        return new RealNode(leftValue / rightValue);
		     
		      case MOD:
			        if (rightValue == 0) 
			        {
			          throw new RuntimeException("Modulo by zero.");
			        }
			        return new RealNode(leftValue % rightValue);
			        
		      default:
		        throw new IOException("Invalid operator for MathOpNode.");
		    }
		  } 
		  
		  else if (left instanceof StringNode && right instanceof StringNode)
		  {
		    String leftValue = ((StringNode) left).getString();
		    String rightValue = ((StringNode) right).getString();
		    
		    switch (node.getToken()) 
		    {
		      case PLUS:
		        return new StringNode(leftValue + rightValue);
		       
		      default:
		        throw new IOException("Invalid operator for MathOpNode.");
		    }
		  } 
		  
		  else 
		  {
		    throw new IOException("Unsupported types for MathOpNode.");
		  }
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
	private Node constantNodes(Node constant) throws IOException 
	{
	    if (constant instanceof IntergerNode) 
	    {
	        return new IntergerNode(((IntergerNode)constant).getItem());
	    } 
	    
	    else if (constant instanceof RealNode) 
	    {
	        return new RealNode(((RealNode) constant).getItem());
	    } 
	    
	    else if (constant instanceof StringNode) 
	    {
	        return new StringNode(((StringNode)constant).getString());
	    } 
	    
	    else if (constant instanceof BooleanNode) 
	    {
	        return new BooleanNode(((BooleanNode)constant).getBoolean());	    
	     } 
	    
	    else 
	    {
	        throw new IOException("Unknown constant type: " + constant.getClass().getName());
	    }
	}
	
}
	



