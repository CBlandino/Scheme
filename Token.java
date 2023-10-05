package Lex;
/**
 * 
 * @author Chris Blandino
 *
 */

public class Token 
{
	
	
	 
	private TokenType token;
	private String value;
	
	
	/**
	 * enum : Character
	 */
	public enum TokenType
	{
		//STATES
		NUMBER,
		DECIMAL,
		ENDOFLINE, 
		IDENTIFIER,
		STRINGLITERAL,
		CHARACTERLITERAL,
		INDENT,
		DEDENT,
		KEYWORD,
		PUNCTUATION,
		LINENUMBER,
		COMMENTS,
		
		
		START,
		END,
		VAR,
		READ,
		WRITE,
		LEFT,
		RIGHT,
		SUBSTRING,
		SQRT,
		GETRANDOM,
		INTEGERTOREAL,
		REALTOINTEGER,
		INTEGER,
		IF,
		WHILE,
		FOR,
		BLOCK,
		ELSIF,
		ELSE,
		VARIABLES,
		ARRAYOF,
		REAL,
		CHARACTER,
		BOOLEAN,
		DEFINE,
		CONSTANTS,
		FROM,
		TO,
		READONLY,
		ALSOREADONLY, 
		UNTIL, 
		REPEAT,
		OF, 
		ARRAY,
		COMMA,
		THEN,
		
		
		//PUNCTUATION
		PLUS,
		MINUS,
		TIMES,
		DIVIDE,
		EQUALS,
		LESSTHAN,
		NOTEQUAL,
		GREATERTHAN,
		GREATOREQUAL,
		LESSOREQUAL,
		COLEQUAL,
		COL,
		INCREMENT,
		DECREMENT,
		RIGHT_BRACKET,
		LEFT_BRACKET,
		
		MOD, LPAREN, RPAREN, SEMICOL, STRING;
		
		


	}

		
	/**
	 * Parameterized constructor
	 * @param enumChar
	 * @param value
	 */
	public Token(TokenType token, String value)
	{
	this.token = token;
	this.value = value;
	}
	
	
	public Token(TokenType token)
	{
		this.token = token;
	}


	/**
	 * 
	 * @return
	 */
	public TokenType getCharacter()
	{
		return token;
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public String getValue()
	{
		return value;
	}
	
	
	/**
	 * @Override
	 */
	public String toString()
	{
		if (value == "" 
			|| value == "\0")
			return token + " ";
		else
			return token + "(" + value + ")";
	}
}
