package org.accelerate.tool.function_engine.rules.engine.lexer;

import static org.junit.Assert.*;
import org.junit.Test;


public class FunctionCalculateParameterTest {
    
    @Test
    public void testCalculateParamerterWitBlankParemeter()
    {
        String str = "";
        String parameter = Argument.calculateFunctionParameter(str);
        assertEquals(null , parameter);
    }
    @Test
    public void testCalculateParamerterWitMultipleBackspaceParameter()
    {
        String str = "   ";
        String parameter = Argument.calculateFunctionParameter(str);
        assertEquals(null , parameter);
    }
    @Test
    public void testCalculateParamerterWitBackspaceParameter()
    {
        String str = " ";
        String parameter = Argument.calculateFunctionParameter(str);
        assertEquals(null , parameter);
    }
    @Test
    public void testCalculateParamerterWitNullParemeter()
    {
        String parameter = Argument.calculateFunctionParameter(null);
        assertEquals(null , parameter);
    }
    @Test
    public void testCalculateParamerterWithNoArgument()
    {
        String str = "@func()";
        String parameter = Argument.calculateFunctionParameter(str);
        assertEquals("",(parameter) );
    }
    @Test
    public void testCalculateParamerterWithBackspaceArgument()
    {
        String str = "@func( )";
        String parameter = Argument.calculateFunctionParameter(str);
        assertEquals("One backspace caractere expected instead of : " + parameter," ",(parameter) );
    }
    @Test
    public void testCalculateParamerterWithSimpleArguments()
    {
        String str = "@func(param=true)";
        String parameter = Argument.calculateFunctionParameter(str);
        assertEquals("param=true",(parameter) );
    }
    @Test
    public void testCalculateParamerterWithEmbedArguments()
    {
        String str = "@func(param=true@func2())";
        String parameter = Argument.calculateFunctionParameter(str);
        assertEquals("param=true@func2()",(parameter) );
    }
    @Test
    public void testCalculateParamerterWithComplexArguments()
    {
        String str = "(a)(b)(c)";
        String parameter = Argument.calculateFunctionParameter(str);
        assertEquals("a",(parameter) );
    }
    @Test
    public void testCalculateParamerterWithComplexEmbedArguments()
    {
        String str = "((foo(bar)john(do)))()func()";
        String parameter = Argument.calculateFunctionParameter(str);
        assertEquals("(foo(bar)john(do))",(parameter) );
    }
    @Test
    public void testCalculateParamerterWithUnbalancedParenthesis()
    {
        String str = "func(param()";
        String parameter = Argument.calculateFunctionParameter(str);
        assertEquals(null,parameter);
    }
    @Test
    public void testCalculateParamerterWithUnbalancedParenthesis2()
    {
        String str = ")func(param()";
        String parameter = Argument.calculateFunctionParameter(str);
        assertEquals(null,parameter);
    }

    @Test
    public void testCalculateParamerterWithVariableArgument()
    {
        String str = "@function(arg1=${foo})";
        String parameter = Argument.calculateFunctionParameter(str);
        assertEquals("arg1=${foo}",(parameter) );
    }
}
