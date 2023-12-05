package org.accelerate.tool.interpreter.rules.engine.lexer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FunctionCalculateFunctionNameTest {

    @Test
    public void testCalculateFunctionNameWithBlankParemeter()
    {
        String str = "";
        String parameter = UnEvaluatedFunction.calculateFunctionName(str);
        assertEquals("" , parameter);
    }
    @Test
    public void testCalculateFunctionNameWithNull()
    {
        String parameter = UnEvaluatedFunction.calculateFunctionName(null);
        assertEquals("" , parameter);
    }
    @Test
    public void testCalculateFunctionNameWithEmptyName()
    {
        String str = "@()";
        String parameter = EvaluatedFunction.calculateFunctionName(str);
        assertEquals("" , parameter);
    }
    @Test
    public void testCalculateFunctionNameWithSimpleFunctionName()
    {
        String str = "@func()";
        String functionName = UnEvaluatedFunction.calculateFunctionName(str);
        assertEquals("func",(functionName));
    }
    @Test
    public void testCalculateFunctionNameWithInvalidFunctionName()
    {
        String str = "@fu<nc()";
        String functionName = EvaluatedFunction.calculateFunctionName(str);
        assertEquals("" , functionName);
    }
    @Test
    public void testIsValideFunctioWithNullParameteer()
    {
        boolean isValid = UnEvaluatedFunction.isAValidFunction(null);
        assertEquals(false , isValid);
    }

    @Test
    public void testIsValideFunctioWithEmptyParameteer()
    {
        boolean isValid = UnEvaluatedFunction.isAValidFunction("");
        assertEquals(false , isValid);
    }
    @Test
    public void testIsValideFunctioWithInvalidFunctionName()
    {
        boolean isValid = UnEvaluatedFunction.isAValidFunction("@fu<nc()");
        assertEquals(false , isValid);
    }
    @Test
    public void testIsValideFunctioWithValidFunctionName()
    {
        boolean isValid = UnEvaluatedFunction.isAValidFunction("@func()");
        assertEquals(true , isValid);
    }
    @Test
    public void testIsValideFunctioWithValidFunctionNameWithParameter()
    {
        boolean isValid = UnEvaluatedFunction.isAValidFunction("@func(parameter)");
        assertEquals(true , isValid);
    }
    @Test
    public void testIsValideFunctioWithValidFunctionNameWithParameterAndAnnotation()
    {
        boolean isValid = UnEvaluatedFunction.isAValidFunction("@func(parameter)");
        assertEquals(true , isValid);
    }
    @Test
    public void testIsValideFunctioWithValidFunctionNameWithParameterAndAnnotationAndSpace()
    {
        boolean isValid = UnEvaluatedFunction.isAValidFunction("@func(parameter) ");
        assertEquals(true , isValid);
    }
    @Test
    public void testIsValideFunctioWithValidFunctionNameWithParameterAndAnnotationAndSpaceAndSpace()
    {
        boolean isValid = UnEvaluatedFunction.isAValidFunction("@func(parameter)  ");
        assertEquals(true , isValid);
    }  
    @Test
    public void testIsValideFunctioWithInValidFunctionNameWithParameter()
    {
        boolean isValid = EvaluatedFunction.isAValidFunction("@ (parameter)");
        assertEquals(false , isValid);
    }  
     @Test
    public void testIsValideFunctioWithAValidFunctionNameWithBlankParameter()
    {
        boolean isValid = EvaluatedFunction.isAValidFunction("@func(   )");
        assertEquals(true , isValid);
    }  
    @Test
    public void testCheckFunctionNameSyntaxWhitNull(){
        boolean isValid = UnEvaluatedFunction.checkFunctionNameSyntax(null);
        assertEquals(false , isValid);
    }
    @Test
    public void testCheckFunctionNameSyntaxWhitEmpty(){
        boolean isValid = UnEvaluatedFunction.checkFunctionNameSyntax("");
        assertEquals(false , isValid);
    }
}
