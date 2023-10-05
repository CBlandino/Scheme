package Lex;
/**
 *  @author Chris Blandino
 */

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Stack;

public class Shank
{

	private static int lineNumber=1;
	
    public static void main(String[] args) throws IOException 
    {

        //Error occures if less than or more than one argument
    	
       
    	 if (args.length != 1) 
    	 {
             System.out.println("ERROR: ONLY ONE FILE NAME");
             System.exit(1);
         }
    	
        //Locates file within the system and sets the charset as parameters

    	 Path filePath = Paths.get(args[0]);
         List <String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);

         // Create an instance of the Lexer class
         Lexer Lex = new Lexer();
         Lexer Par = new Lexer();
         
        
         
         for (String line : lines) 


         {
 

             try 
             {
                 Lex.Lex(line);
                 Par.Lex(line);
                 List<Token> tokens = Lex.getTokens();
                 List<Token> parsing = Lex.getTokens(); 
                
                 //gets line number
                 try 
                 {
                	 System.out.print("This Line Number : " + lineNumber + "  ");
                	 lineNumber++;
					
				} 
                 
                 catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                 
                
                 
                 for (Token token : tokens) 
                 {
                     System.out.print(token + " ");  
                    
                 }
                     
                 
                 
                 System.out.println();
                 
                
                
                 

                 // Print out the parsed Node
                tokens.clear();
                

                 //File reaches end of line, clears the rest of the token
                 
                  
                  
             } 
             
             //catches error if error occurs during lexing and tells line number
             catch (Exception e) 
             {
                 System.out.println("Exception during lexing: " + e.getMessage() + " At Line: " + lineNumber);
             }

             

               
         }	
         
         System.out.println("Lexing Complete! \nNow Parsing:");
         Parser parser = new Parser(Par.getTokens());
         Node root = parser.parse();
        
         SemanticAnalysis test = new SemanticAnalysis((ProgramNode) root);
        
         
         ((ProgramNode) root).addFunction(new BuiltInRead());
         ((ProgramNode) root).addFunction(new BuiltInWrite());
         
         ((ProgramNode) root).addFunction(new BuiltInLeft());
         ((ProgramNode) root).addFunction(new BuiltInRight());
         ((ProgramNode) root).addFunction(new BuiltInSubstring());
         
         ((ProgramNode) root).addFunction(new BuiltInSquareRoot());
         ((ProgramNode) root).addFunction(new BuiltInGetRandom());
         ((ProgramNode) root).addFunction(new BuiltInIntegertoReal());
         ((ProgramNode) root).addFunction(new BuiltInRealtoInteger());
         
         ((ProgramNode) root).addFunction(new BuiltInStart());
         ((ProgramNode) root).addFunction(new BuiltInEnd());
         
        
         
         Interpreter interpret = new Interpreter((ProgramNode) root);
         
         
        
    	 
    }
   
}
