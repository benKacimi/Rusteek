package org.accelerate.tool.interpreter.rules.engine.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class  MultipleArgumentsFunctionTest {

    @Test   
    void testOneLiteralArgumentFuntion()
    {
        String str = "@function(arg1=foo)";
        Lexer lexer = new Lexer();
        RootNode root = lexer.lex(str);
        EvaluatedFunction function = (EvaluatedFunction)root.getChildren().get(0);
        assertEquals("function",(function.getFunctionName()));
        Argument arg = function.getArguments().get(0);
        Literal literal1 =(Literal)arg.getChildren().get(0);
        assertEquals("foo",(literal1.getValue()));
        assertEquals("arg1",(arg.getName()));
    }     
    
    @Test   
    void testTwoLiteralArgumentFuntion()
    {
        String str = " @function  (  arg1 =foo  , arg2= bar)";
        Lexer lexer = new Lexer();
        RootNode root = lexer.lex(str);
        EvaluatedFunction function = (EvaluatedFunction)root.getChildren().get(1);
        assertEquals("function",(function.getFunctionName()));
        
        Argument arg1 = function.getArguments().get(0);
        Literal literal1 =(Literal)arg1.getChildren().get(0);
        assertEquals("foo",(literal1.getValue()));
        assertEquals("arg1",(arg1.getName()));
       
        Argument arg2 = function.getArguments().get(1);
        Literal literal2 =(Literal)arg2.getChildren().get(0);
        assertEquals("bar",(literal2.getValue()));
        assertEquals("arg2",(arg2.getName()));
    }     
    @Test   
    void testOneVarialbelArgumentFuntion()
    {
        String str = "@function(arg1=${foo}.bar)";
        Lexer lexer = new Lexer();
        RootNode root = lexer.lex(str);
        EvaluatedFunction function = (EvaluatedFunction)root.getChildren().get(0);
        assertEquals("function",(function.getFunctionName()));
        
        Argument arg = function.getArguments().get(0);
        Variable variable =(Variable)arg.getChildren().get(0);
        assertEquals("foo",(variable.getKeyName()));
        Literal literal =(Literal)arg.getChildren().get(1);
        assertEquals(".bar",(literal.getValue()));
    }     
}