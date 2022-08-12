package org.accelerate.tool.interpreter.rules.engine.lexer;

import static org.junit.Assert.assertEquals;

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
        assertEquals("" , parameter);
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
    @Test
    public void testIsValideFunctioWithNullParameteer()
    {
        boolean isValid = Function.isAValideFunction(null);
        assertEquals(false , isValid);
    }

    @Test
    public void testIsValideFunctioWithEmptyParameteer()
    {
        boolean isValid = Function.isAValideFunction("");
        assertEquals(false , isValid);
    }
    @Test
    public void testIsValideFunctioWithInvalidFunctionName()
    {
        boolean isValid = Function.isAValideFunction("@fu<nc()");
        assertEquals(false , isValid);
    }
    @Test
    public void testIsValideFunctioWithValidFunctionName()
    {
        boolean isValid = Function.isAValideFunction("@func()");
        assertEquals(true , isValid);
    }
    @Test
    public void testIsValideFunctioWithValidFunctionNameWithParameter()
    {
        boolean isValid = Function.isAValideFunction("@func(parameter)");
        assertEquals(true , isValid);
    }
    @Test
    public void testIsValideFunctioWithValidFunctionNameWithParameterAndAnnotation()
    {
        boolean isValid = Function.isAValideFunction("@func(parameter)");
        assertEquals(true , isValid);
    }
    @Test
    public void testIsValideFunctioWithValidFunctionNameWithParameterAndAnnotationAndSpace()
    {
        boolean isValid = Function.isAValideFunction("@func(parameter) ");
        assertEquals(true , isValid);
    }
    @Test
    public void testIsValideFunctioWithValidFunctionNameWithParameterAndAnnotationAndSpaceAndSpace()
    {
        boolean isValid = Function.isAValideFunction("@func(parameter)  ");
        assertEquals(true , isValid);
    }  
    @Test
    public void testCheckFunctionNameSyntaxWhitNull(){
        boolean isValid = Function.checkFunctionNameSyntax(null);
        assertEquals(false , isValid);
    }
    @Test
    public void testCheckFunctionNameSyntaxWhitEmpty(){
        boolean isValid = Function.checkFunctionNameSyntax("");
        assertEquals(false , isValid);
    }
}
