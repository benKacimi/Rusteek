package org.accelerate.tool.interpreter.rules.engine.lexer;

import static org.junit.Assert.*;

import org.accelerate.tool.interpreter.rules.engine.lexer.Function;
import org.junit.Test;

public class FunctionCalculateFunctionNameTest {

    @Test
    public void testCalculateFunctionNameWithBlankParemeter()
    {
        String str = "";
        String parameter = Function.calculateFunctionName(str);
        assertEquals(null , parameter);
    }
    @Test
    public void testCalculateFunctionNameWithNull()
    {
        String parameter = Function.calculateFunctionName(null);
        assertEquals(null , parameter);
    }
    @Test
    public void testCalculateFunctionNameWithEmptyName()
    {
        String str = "@()";
        String parameter = Function.calculateFunctionName(str);
        assertEquals(null , parameter);
    }
    @Test
    public void testCalculateFunctionNameWithSimpleFunctionName()
    {
        String str = "@func()";
        String functionName = Function.calculateFunctionName(str);
        assertEquals("func",(functionName));
    }
    @Test
    public void testCalculateFunctionNameWithInvalidFunctionName()
    {
        String str = "@fu<nc()";
        String functionName = Function.calculateFunctionName(str);
        assertEquals(null , functionName);
    }
}
