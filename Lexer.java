package Lex;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import Lex.Token.TokenType;

/**
 * 
 * @author Chris Blandino
 *
 */

/*
 * 1.)Figure out how to implement a hash map
 * 1a.)Provide the hash map with 
 */
public class Lexer 
{
	 
	
	  //the different state this machine could be in 
		private enum State
		{
			INITIAL,
			PUNCTUATION,
			NUMBER,
			DECIMAL,
			ENDOFLINE, 
			IDENTIFIER,
			KEYWORD,
			STRINGLITERAL,
			CHARACTERLITERAL,
			COMMENTS,
			PLUS,
			MINUS,
			GREATEROREQUAL,
			LESSOREQUAL,
			COLEQUAL,
			MOD;
	
		}
		
		
		
		//Assigns tokens to an arraylist
		private List<Token> tokens;
		
		//Initializing HashMap
		private  HashMap<String, TokenType> keyWords = new HashMap<String, TokenType>();
		//private  HashMap<String, State> punctuation = new HashMap<String, State>();
		

		
	    public Lexer()
	    {
	    	tokens = new ArrayList<>();
	       
	        
	        //Inserting keyWords to a Hashmap
	        keyWords.put("Start",TokenType.START);
	        keyWords.put("then",TokenType.THEN);
	        keyWords.put("End", TokenType.END);
	        keyWords.put("var", TokenType.VAR);
	        keyWords.put("Read", TokenType.READ);
//	        keyWords.put("readOnly", TokenType.READONLY);
//	        keyWords.put("alsoReadOnly", TokenType.ALSOREADONLY);
	        keyWords.put("Write", TokenType.WRITE);
//	        keyWords.put("Left", TokenType.LEFT);
//	        keyWords.put("Right", TokenType.RIGHT);
//	        keyWords.put("Substring", TokenType.SUBSTRING);
//	        keyWords.put("SquareRoot", TokenType.SQRT);
	        keyWords.put("GetRandom", TokenType.GETRANDOM);
//	        keyWords.put("IntegerToReal", TokenType.INTEGERTOREAL);
//	        keyWords.put("RealToInteger", TokenType.REALTOINTEGER); 
	        keyWords.put("integer", TokenType.INTEGER);
	        keyWords.put("if", TokenType.IF);
	        keyWords.put("repeat", TokenType.REPEAT);
	        keyWords.put("until", TokenType.UNTIL);
	        keyWords.put("while", TokenType.WHILE);
	        keyWords.put("for", TokenType.FOR);
	        keyWords.put("elsif", TokenType.ELSIF);
	        keyWords.put("else", TokenType.ELSE);
	        keyWords.put("variables", TokenType.VARIABLES);
	        keyWords.put("array", TokenType.ARRAY);
	        keyWords.put("of", TokenType.OF);
	        keyWords.put("real", TokenType.REAL);
	        keyWords.put("mod", TokenType.MOD);
	        keyWords.put("boolean", TokenType.BOOLEAN);
	        keyWords.put("define", TokenType.DEFINE);
	        keyWords.put("constants", TokenType.CONSTANTS);
	        keyWords.put("from", TokenType.FROM);
	        keyWords.put("to", TokenType.TO);
	        keyWords.put("string", TokenType.STRING);
	        
	        //Checks if keyWords is present
	        boolean doWeHaveStart = keyWords.containsKey("Start");
	        boolean doWeHaveThen = keyWords.containsKey("then");
	        boolean doWeHaveEnd = keyWords.containsKey("End");
	        boolean doWeHaveVar = keyWords.containsKey("var");
	        boolean doWeHaveRead = keyWords.containsKey("Read");
//	        boolean doWeHaveReadOnly = keyWords.containsKey("readOnly");
//	        boolean doWeHaveAlsoReadOnly = keyWords.containsKey("alsoReadOnly");
//	        boolean doWeHaveLeft = keyWords.containsKey("Left");
//	        boolean doWeHaveRight = keyWords.containsKey("Right");
//	        boolean doWeHaveSubString = keyWords.containsKey("Substring");
//	        boolean doWeHaveSquareRoot = keyWords.containsKey("SqareRoot");
	        boolean doWeHaveGetRandom = keyWords.containsKey("GetRandom");
//	        boolean doWeHaveRealToInteger = keyWords.containsKey("RealToInteger");
//	        boolean doWeHaveIntegerToReal = keyWords.containsKey("IntegerToReal");
	        boolean doWeHaveInteger = keyWords.containsKey("integer");
	        boolean doWeHaveIf = keyWords.containsKey("if");
	        boolean doWeHaveRepeat = keyWords.containsKey("repeat");
	        boolean doWeHaveUntil = keyWords.containsKey("until");
	        boolean doWeHaveWhile = keyWords.containsKey("while");
	        boolean doWeHaveFor = keyWords.containsKey("for");
	        boolean doWeHaveElsIf = keyWords.containsKey("elsif");
	        boolean doWeHaveElse = keyWords.containsKey("else");
	        boolean doWeHaveVariables = keyWords.containsKey("variables");
	        boolean doWeHaveArray = keyWords.containsKey("array");
	        boolean doWeHaveOf = keyWords.containsKey("of");
	        boolean doWeHaveReal = keyWords.containsKey("real");
	        boolean doWeHaveTokenType = keyWords.containsKey("TokenType");
	        boolean doWeHaveBoolean = keyWords.containsKey("boolean");
	        boolean doWeHaveDefine = keyWords.containsKey("define");
	        boolean doWeHaveConstants = keyWords.containsKey("constants");
	        boolean doWeHaveFrom = keyWords.containsKey("from");
	        boolean doWeHaveto = keyWords.containsKey("to");
	        boolean doWeHavemod = keyWords.containsKey("mod");
	        boolean doWeHavestring = keyWords.containsKey("string");
	        
	        //gets TokenType of keyword
	        TokenType startType = keyWords.get("Start");
	        TokenType thenType = keyWords.get("then");
	        TokenType endType = keyWords.get("End");
	        TokenType varType = keyWords.get("var");
	        TokenType readType = keyWords.get("Read");
//	        TokenType readOnlyType = keyWords.get("readOnly");
//	        TokenType alsoReadOnlyType = keyWords.get("alsoReadOnly");
//	        TokenType leftType = keyWords.get("Left");
//	        TokenType rightType = keyWords.get("Right");
//	        TokenType substringType = keyWords.get("Substring");
//	        TokenType squareRootType = keyWords.get("SquareRoot");
	        TokenType getRandomType = keyWords.get("GetRandom");
//	        TokenType integertToRealType = keyWords.get("IntegerToReal");
//	        TokenType realToIntegerType = keyWords.get("RealToInteger");
	        TokenType IntegerType = keyWords.get("integer");        
	        TokenType ifType = keyWords.get("if");
	        TokenType repeatType = keyWords.get("repeat");
	        TokenType untilType = keyWords.get("until");
	        TokenType whileType = keyWords.get("while");
	        TokenType forType = keyWords.get("for");
	        TokenType elsifType = keyWords.get("elsif");
	        TokenType elseType = keyWords.get("else");
	        TokenType variablesType = keyWords.get("variables");
	        TokenType arrayType = keyWords.get("array");
	        TokenType ofType = keyWords.get("of");
	        TokenType realType = keyWords.get("real");
	        TokenType  TokenTypeType = keyWords.get("TokenType");
	        TokenType booleanType = keyWords.get("boolean");
	        TokenType defineType = keyWords.get("define");
	        TokenType constantsType = keyWords.get("constants");
	        TokenType fromType = keyWords.get("from");
	        TokenType toType = keyWords.get("to");
	        TokenType modType = keyWords.get("mod");
	        TokenType stringType = keyWords.get("string");
	    }

	    
State state = State.INITIAL;

int prevIndent = 0; // previous indentation level

/**
 * 
 * @param line
 * @throws Exception
 */
public void Lex(String lines) throws Exception
{
	// Sets the initall state to INITIAL
	StringBuilder builder = new StringBuilder();

	
	for (int i = 0; i < lines.length(); i++)
	{
		char currentChar = lines.charAt(i);

        // calculate the indentation level
        int curIndent = getIndent(lines); // current indentation level
       
    	
        
    
        for(int o = 0; o < curIndent - prevIndent; o++)
        {
        	tokens.add( new Token (Token.TokenType.INDENT, ""));
        }
      
        for(int o = 0; o < prevIndent - curIndent; o++)
        {
        	tokens.add( new Token (Token.TokenType.DEDENT, ""));
        }
      
		prevIndent = curIndent;
        
		//Cycles through state for each char at selected line
		switch(state)
		{
			//Install state of the machine
			case INITIAL:
				
			//if the machine detects a digit at selected char move the state to NUMBER
			if (Character.isDigit(currentChar))
				{
					state = State.NUMBER;
					builder.append(currentChar);
				}
				
				
				//if the machine detects a '.' at selected char move the state to DECIMAL
			else if (currentChar == '.')
				{
					state = State.DECIMAL;
					builder.append(currentChar);
				}
				
				
				//if the machine detects a letter at selected char move the state to WORD
			else if (Character.isLetter(currentChar)) 
				{
					state = State.IDENTIFIER;
					builder.append(currentChar);
					
				}
				
				//if the machine detects a '}' at selected char move the state to COMMENTS
			else if (currentChar == '{') 
				{
					state = State.COMMENTS;
				}
			
				
				//if the machine detects a shank punctuation at char move to punctuation
			else if (currentChar ==  '+'
						||currentChar == '-'
						||currentChar == '*'
						||currentChar == '/'
						||currentChar == '='
						||currentChar == '>'
						||currentChar == '<'
						||currentChar == ':'
						||currentChar == '('
						||currentChar == ')'
						||currentChar == ','
						||currentChar == ';'
						||currentChar == ']'
						||currentChar == '[')
						
				{
					state = state.PUNCTUATION;
					builder.append(currentChar);
					i--;
				}
				
				
				//If the state detects 
			else if (currentChar == '"')
				{
					builder.append(currentChar);
				
					state = State.STRINGLITERAL;
				}
				
				
			else if (currentChar == '\'')
				{
					builder.append(currentChar);
					
					state = State.CHARACTERLITERAL;
				}
	
				
				//Once the machine has gone through all states go to ENDOFLINE state
			else if (Character.isWhitespace(currentChar))
				{
					state = State.INITIAL;	
					
				}
				
				
				//If the state detects and unknown an error occurs
			else
				{
					throw new Exception("Invalid character: " + currentChar);
				}
				
				
				
			break;
			
				
			
				

			case NUMBER:
				//if the character is a digit add it to string builder
				if (Character.isDigit(currentChar))
				{
					builder.append(currentChar);
				}
				//if the machine detects a '.' at selected char move the state to DECIMAL to form a decimal number
				else if (currentChar == '.')
				{
					state = State.DECIMAL;
					builder.append(currentChar);
				}
				else
				{
					//adds a token in states, stores it in enum, and formats it to string inside of token
					tokens.add(new Token(Token.TokenType.NUMBER, builder.toString()));
					builder.setLength(0);;
					state = State.INITIAL;
					i--;
				}
				break;
				
			case DECIMAL:
			{//if the character is a '.' add it to string builder
				if(Character.isDigit(currentChar))
				{
					builder.append(currentChar); 
					
				}
				else
				{
					//adds a token in states, stores it in enum, and formats it to string inside of token
					tokens.add(new Token(Token.TokenType.NUMBER, builder.toString()));
					builder.setLength(0);
					state = State.INITIAL;
					i--;
				}
				break;
			}	
			
			case IDENTIFIER:
			//if the character is a letter or digit add it to string builder
				if(Character.isLetterOrDigit(currentChar))
				{
					builder.append(currentChar);
				}	
				
				
				else if(keyWords.containsKey(builder.toString()))
				{					
					tokens.add(new Token(keyWords.get(builder.toString()), builder.toString()));
					builder.setLength(0);
					state = State.INITIAL;
					i--;
				}
					
				else
				{
					//adds a token in states, stores it in enum, and formats it to string inside of token	
						tokens.add(new Token(Token.TokenType.IDENTIFIER, builder.toString()));
						builder.setLength(0);
						state = State.INITIAL;
						i--;			
				}
				
				break;
			
			
			case PUNCTUATION:
					
				if(currentChar == '+')
				{
					state = State.PLUS;
				}
				
				else if(currentChar == '-')
				{	
					 state = State.MINUS;
				}
				 
				else if(currentChar == '<')
				{
					state = State.LESSOREQUAL;
				}
				
				else if(currentChar == '>')
				{
					state = State.GREATEROREQUAL;
				}
				 
				else if(currentChar == '=')
				{
				tokens.add(new Token(Token.TokenType.EQUALS, builder.toString()));
				builder.setLength(0);;
				state = State.INITIAL;
				}
				
				else if(currentChar == ',')
				{
				tokens.add(new Token(Token.TokenType.COMMA, builder.toString()));
				builder.setLength(0);;
				state = State.INITIAL;
				}
				
				else if(currentChar == ')')
				{
				tokens.add(new Token(Token.TokenType.RPAREN, builder.toString()));
				builder.setLength(0);;
				state = State.INITIAL;
				}
				
				else if(currentChar == '(')
				{
				tokens.add(new Token(Token.TokenType.LPAREN, builder.toString()));
				builder.setLength(0);;
				state = State.INITIAL;
				}
				 
				else if(currentChar == '*')
				{
					tokens.add(new Token(Token.TokenType.TIMES, builder.toString()));
					builder.setLength(0);;
					state = State.INITIAL;
				}
				else if(currentChar == '/')
				{
					tokens.add(new Token(Token.TokenType.DIVIDE, builder.toString()));
					builder.setLength(0);;
					state = State.INITIAL;
				}
				else if(currentChar == ':')
				{
					state = State.COLEQUAL;
				}
				
				else if(currentChar == ';')
				{
				tokens.add(new Token(Token.TokenType.SEMICOL, builder.toString()));
				builder.setLength(0);;
				state = State.INITIAL;
				}
				
				else if (currentChar == '[')
				{
					tokens.add(new Token(Token.TokenType.LEFT_BRACKET, builder.toString()));
					builder.setLength(0);;
					state = State.INITIAL;
				}
				
				else if (currentChar == ']')
				{
					tokens.add(new Token(Token.TokenType.RIGHT_BRACKET, builder.toString()));
					builder.setLength(0);;
					state = State.INITIAL;
				}
				
		break;
		
			case PLUS:	
				//Checks if current char is '+' if not then add '+' if it is then add '++'
				if (currentChar == '+')
				{
					builder.append(currentChar);
					tokens.add(new Token(Token.TokenType.INCREMENT, builder.toString()));
					builder.setLength(0);
					state = State.INITIAL;
				}
				else 
				{
					tokens.add(new Token(Token.TokenType.PLUS, builder.toString()));
					builder.setLength(0);
					state = State.INITIAL;
				}
		break;
		//Checks if current char is '-' if not then add '-' if it is then add '--'. If the current char is a number then add '-' to the number token and state.
			case MINUS:		
				if (currentChar == '-')
				{
					builder.append(currentChar);
					tokens.add(new Token(Token.TokenType.DECREMENT, builder.toString()));
					builder.setLength(0);
					state = State.INITIAL;
				}
				else if (Character.isDigit(currentChar))
				{
					builder.append(currentChar);
					state = State.NUMBER;
				}
				else 
				{
					tokens.add(new Token(Token.TokenType.MINUS, builder.toString()));
					builder.setLength(0);
					state = State.INITIAL;
				}
		break;	
			
			case GREATEROREQUAL:
				//Checks if current char is '=' if not then add '>' if it is then add '>='
				if (currentChar == '=')
				{
					builder.append(currentChar);
					tokens.add(new Token(Token.TokenType.GREATOREQUAL, builder.toString()));
					builder.setLength(0);
					state = State.INITIAL;
				}
				else 
				{
					tokens.add(new Token(Token.TokenType.GREATERTHAN, builder.toString()));
					builder.setLength(0);
					state = State.INITIAL;
				}
				
		break;
			case LESSOREQUAL:	
				//Checks if current char is '>', if it is then add '<>'. Also checks if current char is '=' if it is then add '<='. Lastly if none of these apply then add '<'
				if (currentChar == '>')
				{
					builder.append(currentChar);
					tokens.add(new Token(Token.TokenType.NOTEQUAL, builder.toString()));
					builder.setLength(0);
					state = State.INITIAL;
				}
				
				else if (currentChar == '=')
				{
					builder.append(currentChar);
					tokens.add(new Token(Token.TokenType.GREATOREQUAL, builder.toString()));
					builder.setLength(0);
					state = State.INITIAL;
				}
				
				else 
				{
					tokens.add(new Token(Token.TokenType.LESSTHAN, builder.toString()));
					builder.setLength(0);
					state = State.INITIAL;
				}

		break;
		//Checks if current char is '=' if not then add ':' if it is then add ':='
			case COLEQUAL:
				if (currentChar == '=')
				{
					builder.append(currentChar);
					tokens.add(new Token(Token.TokenType.COLEQUAL, builder.toString()));
					builder.setLength(0);
					state = State.INITIAL;
				}
				
				else 
				{
					tokens.add(new Token(Token.TokenType.COL, builder.toString()));
					builder.setLength(0);
					state = State.INITIAL;
				}
				
		break;
		//If currentChar is not ' then keep buffer appending until ' is reached. Once ' is reached return to state initial
			case CHARACTERLITERAL:
			{
				if(currentChar !='\'') 
				{
				builder.append(currentChar);
				} 
				
				else if (builder.length() >= 3)
				{
					throw new Exception("Only Instert One Character!");
				}
				
				else if (currentChar == '\'')
				{
					builder.append(currentChar);	
					tokens.add(new Token(Token.TokenType.CHARACTERLITERAL, builder.toString()));
					state = State.INITIAL;
					builder.setLength(0);
				}
			}
			break;
			
			
			case STRINGLITERAL:
			{
			//If currentChar is not " then keep buffer appending until " is reached. Once " is reached return to state initial
			if(currentChar !='"') 
				{
				builder.append(currentChar);
				} 
			
			
			else if(currentChar == '"') 
				{
					builder.append(currentChar);	
					tokens.add(new Token(Token.TokenType.STRINGLITERAL, builder.toString()));
					builder.setLength(0);
					state = State.INITIAL;
				}
				
				
			}
			
			
		break;
		
			case COMMENTS: 
			{
				//If currentChar is not } then keep throwing currecntChar until } is reached. Once } is reached return to state initial
					
					 if(currentChar == '{') 
					{
						throw new Exception("To many brackets!");
					}
					
					else if(currentChar == '}') 
					{	
						builder.setLength(0);
					}
				
			}
			break;
		
		//if a non-existant state it called an error occures
			default:
				throw new Exception("Invalid state");	
		}
	}
	
	// Data structure that prints last token depending on which state we were in last
	
	if(builder.length() > 0) 
	{
		switch (state)
		{
		
		case IDENTIFIER:
		{
			
			if (keyWords.containsKey(builder.toString()))
				{
					tokens.add(new Token(keyWords.get(builder.toString()), builder.toString()));
					builder.setLength(0);
					state = State.INITIAL;
				} 
			
			else 
				{
					tokens.add(new Token(Token.TokenType.IDENTIFIER, builder.toString()));
					builder.setLength(0);
					state = State.INITIAL;
				}
		}
			break;
		
		case NUMBER:
			{
				tokens.add(new Token(Token.TokenType.NUMBER, builder.toString()));
				builder.setLength(0);
				state = State.INITIAL;
			}
			break;
		
		case DECIMAL:
			{
				tokens.add(new Token(Token.TokenType.NUMBER, builder.toString()));
				builder.setLength(0);
				state = State.INITIAL;
			}
			break;
			
		case PUNCTUATION:
        {
            builder.setLength(0);
            state = State.PUNCTUATION;
        }
        break;
		
			
		default:
			state = State.INITIAL;
			break;

		
			
		}
		
	}	
	
	builder.setLength('\0');
	tokens.add(new Token(Token.TokenType.ENDOFLINE, "\0"));
	
	}


/**
 * gets Indent
 * @param lines
 * @return
 */
public int getIndent(String lines)
{
	int n = 0;
	
	for(char i : lines.toCharArray())
	{
		if (i == ' ')
		{
			n++;
		}
		
		else if (i == '\t')
		{
			n += 4;
		}
		
		else 
			return n/4;
	}
	
	return n/4;
}

/**
 * 
 * @return
 */
public List<Token> getTokens()
	{
	return tokens;
	}
	
/**
 * Clears Tokens
 */
public void clearTokens() 
	{
    tokens.clear();
	}


}

