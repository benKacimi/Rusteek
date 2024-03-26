package org.accelerate.tool.interpreter.rules.engine.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;



class LexTest {
    @Test
    void testLexEmptyString()
    {
        String str = "";
        Lexer lexer = new Lexer();
        RootNode root = lexer.lex(str);
        assertNull(root.getChildren()); 
    }

    @Test
    void testLexNull()
    {
        Lexer lexer = new Lexer();
        RootNode root = lexer.lex(null);
        assertNull(root.getChildren());
    }
    @ParameterizedTest
    @ValueSource(strings = {" ","   ","@()","foo.bar${aVariable"})
    void testLexLiteral(String str)
    {
        Lexer lexer = new Lexer();
        RootNode root = lexer.lex(str);
        Literal literal = (Literal)root.getChildren().get(0);
        assertEquals(str,(literal.getValue()));
        assertEquals(1, root.getChildren().size(), "one chhild expected instead of  : " + root.getChildren().size() );
    }
    @ParameterizedTest
    @CsvSource({
        "@function(), function",
        "@functionTest(   ), functionTest",
        "@func     (), func"
    })
    void testLexASimpleFunction(String str, String functionName)
    {
        Lexer lexer = new Lexer();
        RootNode root = lexer.lex(str);
        EvaluatedFunction function = (EvaluatedFunction)root.getChildren().get(0);
        assertEquals(Collections.emptyList(),function.getArguments());
        assertEquals(functionName,(function.getFunctionName()));
        assertEquals(1, root.getChildren().size(), "one chhild expected instead of  : " + root.getChildren().size() );
    }

    @Test
    void testLexASimpleFunctionWithLiteralParameter()
    {
        String str = "@function(  a parameter  )";
        Lexer lexer = new Lexer();
        RootNode root = lexer.lex(str);
        EvaluatedFunction function = (EvaluatedFunction)root.getChildren().get(0);
        assertEquals("function",(function.getFunctionName()));
        Argument arg = function.getArguments().get(0);
        Literal literal =(Literal)arg.getChildren().get(0);
        assertEquals("a parameter",(literal.getValue()));
        assertEquals(1, root.getChildren().size(),  "one chhild expected instead of  : " + root.getChildren().size() );
    }
    @Test
    void testLexASimpleFunctionWithLiteralParameterAndOtherFunction()
    {
        String str = "@function(a parameter)foo@bar@function2(@functionGetParameter())$";
        Lexer lexer = new Lexer();
        RootNode root = lexer.lex(str);
        EvaluatedFunction function = (EvaluatedFunction)root.getChildren().get(0);
        assertEquals("function",(function.getFunctionName()));
        Argument arg = function.getArguments().get(0);
        Literal literal1 =(Literal)arg.getChildren().get(0);
        assertEquals("a parameter",(literal1.getValue()));

        Literal literal2 = (Literal)root.getChildren().get(1);
        assertEquals("foo@bar", literal2.getValue(),"function name containts an @");

        assertEquals(4 ,root.getChildren().size(), "4 nodes over : " + root.getChildren().size() );

        Literal literal3 = (Literal)root.getChildren().get(3);
        assertEquals("$",literal3.getValue(), "$' exepected instead of : "+ literal3.getValue());

        assertEquals(root,lexer.lex(str));
    }
    @Test
    void testLexASimpleFunctionWithEmailAdressAndFunction()
    {
        String str = "foo@bar.com@function()";
        Lexer lexer = new Lexer();
        RootNode root = lexer.lex(str);
        EvaluatedFunction function = (EvaluatedFunction)root.getChildren().get(1);
        assertEquals("function",(function.getFunctionName()));
        assertEquals( "org.accelerate.tool.interpreter.rules.FunctionRuleForTest",
                    function.getRule().getClass().getName(),
                    "Rules Class error : "+ function.getFunctionName());
    }
    @Test
    void testLexASimpleFunctionWithEmailAdressAndFunctionWithANumberInFunctionName()
    {
        String str = "foo@bar.com@func23()";
        Lexer lexer = new Lexer();
        RootNode root = lexer.lex (str);
        EvaluatedFunction function = (EvaluatedFunction)root.getChildren().get(1);
        assertEquals("func23",function.getFunctionName(),"Function Name should be func23 instead : " + function.getFunctionName());
        assertEquals(root,lexer.lex(str));
    }

    @Test
    void testLexASimpleFunctionWithBlancInFunctionName()
    {
        String str = "foo@bar.com@func23    ()";
        Lexer lexer = new Lexer();
        RootNode root = lexer.lex (str);
        EvaluatedFunction function = (EvaluatedFunction)root.getChildren().get(1);
        assertEquals("func23",function.getFunctionName(),"Function Name should be func23 instead : " + function.getFunctionName());
    }

    @Test
    void testLexASimpleFunctionWittEqualParameter()
    {
        String str = "foo@bar.com@func23(foo=bar)";
        Lexer lexer = new Lexer();
        RootNode root = lexer.lex (str);
        EvaluatedFunction function = (EvaluatedFunction)root.getChildren().get(1);
        assertEquals("func23",function.getFunctionName(),"Function Name should be func23 instead : " + function.getFunctionName());
        function.getArguments().get(0).getChildren().get(0);
        String argumentName = function.getArguments().get(0).getName();
        assertEquals("foo",argumentName,"Parameter Name should be foo instead : " + argumentName);
        String argumentValue = ((Literal)function.getArguments().get(0).getChildren().get(0)).getValue();
        assertEquals("bar",argumentValue,"Parameter Value should be bar instead : " + argumentValue);
    }
    @Test
    void testLexASimpleFunctionWittWrongEqualParameter()
    {
        String str = "@function(bar=,foo=fuzz,=buzz)";
        Lexer lexer = new Lexer();
        RootNode root = lexer.lex (str);
        EvaluatedFunction function = (EvaluatedFunction)root.getChildren().get(0);
        assertEquals("function",function.getFunctionName(),"Function Name should be function instead : " + function.getFunctionName());
        
        String parameterValue1 = ((Literal)function.getArguments().get(0).getChildren().get(0)).getValue();
        assertEquals("bar=", parameterValue1,"Parameter Value should be [bar=]instead : " + parameterValue1);
        
        String argumentName2 = function.getArguments().get(1).getName();
        assertEquals("foo",argumentName2,"Parameter 2 Name should be [foo] instead : " + argumentName2);
        String argumentValue2 = ((Literal)function.getArguments().get(1).getChildren().get(0)).getValue();
        assertEquals("fuzz",argumentValue2,"Parameter Value should be fuzz instead : " + argumentValue2);

        String parameterValue3 = ((Literal)function.getArguments().get(2).getChildren().get(0)).getValue();
        assertEquals("=buzz",parameterValue3,"Parameter Value should be [=buzz] instead : " + parameterValue3);
        
    }
    @ParameterizedTest
    @CsvSource({
        "${aVariable}, aVariable",
        "${ aVariable }, aVariable"
    })
    void testLexASimpleVarialeWithBlankCaratere(String str, String varName)
    {
        Lexer lexer = new Lexer();
        RootNode root = lexer.lex(str);
        Variable aVar = (Variable)root.getChildren().get(0);
        assertEquals(varName, aVar.getKeyName(), "Variable Key should be "+varName+" instead : " + aVar.getKeyName());
    
        assertEquals(varName,(aVar.getKeyName()));
    }
    @Test
    void testLexASimpleFunctionPlusAVariable()
    {
        String str = "@function(${aVariable}).bar";
        Lexer lexer = new Lexer();
        RootNode root = lexer.lex(str);
        EvaluatedFunction function = (EvaluatedFunction)root.getChildren().get(0);
        Argument arg = function.getArguments().get(0);
        Variable aVar =(Variable)arg.getChildren().get(0);
        assertEquals("aVariable",(aVar.getKeyName()));
        Literal literal = (Literal)root.getChildren().get(1);
        assertEquals(".bar", literal.getValue(), "literal should be .bar instead of : " + literal.getValue());
        assertNotEquals(null,function.getRule().getClass().getName());
    }
    @Test
    void testLexASimpleFunctionWithEqual()
    {
        String str = "@function(foo=${aVariable}).bar";
        Lexer lexer = new Lexer();
        RootNode root = lexer.lex(str);
        EvaluatedFunction function = (EvaluatedFunction)root.getChildren().get(0);
        Argument arg = function.getArguments().get(0);
        Variable aVar =(Variable)arg.getChildren().get(0);
        assertEquals("foo",arg.getName(), "Parameter should be [foo] instead of : " + arg.getName());
        assertEquals("aVariable",(aVar.getKeyName()));
        Literal literal = (Literal)root.getChildren().get(1);
        assertEquals(".bar",literal.getValue(), "literal should be .bar instead of : " + literal.getValue());
        assertNotEquals( null,function.getRule().getClass().getName());
    }

    @Test
    void testLexLiteralPlusAValidVariable()
    {
        String str = "foo.bar${aVariable}b";
        Lexer lexer = new Lexer();
        RootNode root = lexer.lex(str);
        Literal literal = (Literal)root.getChildren().get(0);
        assertEquals("foo.bar",(literal.getValue()));
        Variable aVar = (Variable)root.getChildren().get(1);
        assertEquals("aVariable",(aVar.getKeyName()));
        Literal literal2 = (Literal)root.getChildren().get(2);
        assertEquals("b",(literal2.getValue()));
    }
}