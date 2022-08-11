package org.accelerate.tool.interpreter.rules.engine.lexer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

public class LexTest {

    @Test
    public void testLexEmptyString()
    {
        String str = "";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        assertEquals(null,root.getChildren()); 
    }

    @Test
    public void testLexNull()
    {
        Lexer lexer = new Lexer();
        Node root = lexer.lex(null);
        assertEquals(null,root.getChildren());
    }
    @Test
    public void testLexStringWithBackspace()
    {
        String str = "   ";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Literal literal = (Literal)root.getChildren().get(0);
        assertEquals("   ",(literal.getValue()));
    }
    @Test
    public void testLexASimpleFunction()
    {
        String str = "@function()";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Function function = (Function)root.getChildren().get(0);
        assertEquals(Collections.emptyList(),function.getArguments());
        assertEquals("function",(function.getFunctionName()));
        assertEquals("one chhild expected instead of  : " + root.getChildren().size(),  1 ,root.getChildren().size());
    }
    @Test
    public void testLexASimpleFunctionWithBlankArgument()
    {
        String str = "@functionTest(   )";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Function function = (Function)root.getChildren().get(0);
        assertEquals(Collections.emptyList(),function.getArguments());
        assertEquals("functionTest",(function.getFunctionName()));
        assertEquals("one chhild expected instead of  : " + root.getChildren().size(),  1 , root.getChildren().size());
    }
    @Test
    public void testLexStringWithNoFunctionName()
    {
        String str = "@()";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Literal literal = (Literal)root.getChildren().get(0);
        assertEquals("@()",(literal.getValue()));
    }
    @Test
    public void testLexASimpleFunctionWithLiteralParameter()
    {
        String str = "@function(  a parameter  )";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Function function = (Function)root.getChildren().get(0);
        assertEquals("function",(function.getFunctionName()));
        Argument arg = function.getArguments().get(0);
        Literal literal =(Literal)arg.getChildren().get(0);
        assertEquals("a parameter",(literal.getValue()));
        assertEquals("one chhild expected instead of  : " + root.getChildren().size(),  1 , root.getChildren().size());
    }
    @Test
    public void testLexASimpleFunctionWithLiteralParameterAndOtherFunction()
    {
        String str = "@function(a parameter)foo@bar@function2(@functionGetParameter())$";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Function function = (Function)root.getChildren().get(0);
        assertEquals("function",(function.getFunctionName()));
        Argument arg = function.getArguments().get(0);
        Literal literal1 =(Literal)arg.getChildren().get(0);
        assertEquals("a parameter",(literal1.getValue()));

        Literal literal2 = (Literal)root.getChildren().get(1);
        assertEquals("function name containts an @", "foo@bar",(literal2.getValue()));

        assertEquals("4 nodes over : " + root.getChildren().size() , 4 ,root.getChildren().size());

        Literal literal3 = (Literal)root.getChildren().get(3);
        assertEquals("$' exepected instead of : "+ literal3.getValue(), "$",(literal3.getValue()));

        assertEquals(root,lexer.lex(str));
    }
    @Test
    public void testLexASimpleFunctionWithEmailAdressAndFunction()
    {
        String str = "foo@bar.com@function()";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Function function = (Function)root.getChildren().get(1);
        assertEquals("function",(function.getFunctionName()));
        assertEquals( "Rules Class error : "+ function.getFunctionName(),
                    "org.accelerate.tool.interpreter.rules.FunctionRuleForTest",
                    (function.getFunctionClass()));
    }
    @Test
    public void testLexASimpleFunctionWithEmailAdressAndFunctionWithANumberInFunctionName()
    {
        String str = "foo@bar.com@func23()";
        Lexer lexer = new Lexer();
        Node root = lexer.lex (str);
        Function function = (Function)root.getChildren().get(1);
        assertEquals("Function Name should be func23 instead : " + function.getFunctionName(),"func23",(function.getFunctionName()));
        assertEquals(root,lexer.lex(str));
    }

    @Test
    public void testLexASimpleFunctionWithBlancInFunctionName()
    {
        String str = "foo@bar.com@func23    ()";
        Lexer lexer = new Lexer();
        Node root = lexer.lex (str);
        Function function = (Function)root.getChildren().get(1);
        assertEquals("Function Name should be func23 instead : " + function.getFunctionName(),"func23",(function.getFunctionName()));
    }

    @Test
    public void testLexASimpleFunctionWittEqualParameter()
    {
        String str = "foo@bar.com@func23(foo=bar)";
        Lexer lexer = new Lexer();
        Node root = lexer.lex (str);
        Function function = (Function)root.getChildren().get(1);
        assertEquals("Function Name should be func23 instead : " + function.getFunctionName(),"func23",(function.getFunctionName()));
        function.getArguments().get(0).getChildren().get(0);
        String argumentName = function.getArguments().get(0).getName();
        assertEquals("Parameter Name should be foo instead : " + argumentName,"foo",argumentName);
        String argumentValue = ((Literal)function.getArguments().get(0).getChildren().get(0)).getValue();
        assertEquals("Parameter Value should be bar instead : " + argumentValue,"bar",argumentValue);
    }
    @Test
    public void testLexASimpleFunctionWittWrongEqualParameter()
    {
        String str = "@function(bar=,foo=fuzz,=buzz)";
        Lexer lexer = new Lexer();
        Node root = lexer.lex (str);
        Function function = (Function)root.getChildren().get(0);
        assertEquals("Function Name should be function instead : " + function.getFunctionName(),"function",(function.getFunctionName()));
        
        String parameterValue1 = ((Literal)function.getArguments().get(0).getChildren().get(0)).getValue();
        assertEquals("Parameter Value should be [bar=]instead : " + parameterValue1,"bar=",parameterValue1);
        
        String argumentName2 = function.getArguments().get(1).getName();
        assertEquals("Parameter 2 Name should be [foo] instead : " + argumentName2,"foo",argumentName2);
        String argumentValue2 = ((Literal)function.getArguments().get(1).getChildren().get(0)).getValue();
        assertEquals("Parameter Value should be fuzz instead : " + argumentValue2,"fuzz",argumentValue2);

        String parameterValue3 = ((Literal)function.getArguments().get(2).getChildren().get(0)).getValue();
        assertEquals("Parameter Value should be [=buzz] instead : " + parameterValue3,"=buzz",parameterValue3);
        
    }
    @Test
    public void testLexASimpleVariale()
    {
        String str = "${aVariable}";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Variable aVar = (Variable)root.getChildren().get(0);
        assertEquals("aVariable",(aVar.getKeyName()));
    }
    @Test
    public void testLexASimpleVarialeWithBlankCaratere()
    {
        String str = "${ aVariable }";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Variable aVar = (Variable)root.getChildren().get(0);
        assertEquals("Variable Key should be aVariable instead : " + aVar.getKeyName(),"aVariable",(aVar.getKeyName()));
    
        assertEquals("aVariable",(aVar.getKeyName()));
    }
    @Test
    public void testLexASimpleFunctionPlusAVariable()
    {
        String str = "@function(${aVariable}).bar";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Function function = (Function)root.getChildren().get(0);
        Argument arg = function.getArguments().get(0);
        Variable aVar =(Variable)arg.getChildren().get(0);
        assertEquals("aVariable",(aVar.getKeyName()));
        Literal literal = (Literal)root.getChildren().get(1);
        assertEquals("literal should be .bar instead of : " + literal.getValue(), ".bar",(literal.getValue()));
        assertNotEquals(null,function.getFunctionClass());
    }
    @Test
    public void testLexASimpleFunctionWithEqual()
    {
        String str = "@function(foo=${aVariable}).bar";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Function function = (Function)root.getChildren().get(0);
        Argument arg = function.getArguments().get(0);
        Variable aVar =(Variable)arg.getChildren().get(0);
        assertEquals("Parameter na should be foo instead of : " + arg.getName(), "foo",(arg.getName()));
        assertEquals("aVariable",(aVar.getKeyName()));
        Literal literal = (Literal)root.getChildren().get(1);
        assertEquals("literal should be .bar instead of : " + literal.getValue(), ".bar",(literal.getValue()));
        assertNotEquals( null,function.getFunctionClass());
    }
}