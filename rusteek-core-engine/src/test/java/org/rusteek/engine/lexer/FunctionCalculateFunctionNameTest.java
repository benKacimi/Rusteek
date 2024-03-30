package org.rusteek.engine.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FunctionCalculateFunctionNameTest {

    @Test
    void testCalculateFunctionNameWithBlankParemeter()
    {
        String str = "";
        String parameter = UnEvaluatedFunction.calculateFunctionName(str);
        assertEquals("" , parameter);
    }
    @Test
    void testCalculateFunctionNameWithNull()
    {
        String parameter = UnEvaluatedFunction.calculateFunctionName(null);
        assertEquals("" , parameter);
    }
    @Test
    void testCalculateFunctionNameWithEmptyName()
    {
        String str = "@()";
        String parameter = EvaluatedFunction.calculateFunctionName(str);
        assertEquals("" , parameter);
    }
    @Test
    void testCalculateFunctionNameWithSimpleFunctionName()
    {
        String str = "@func()";
        String functionName = UnEvaluatedFunction.calculateFunctionName(str);
        assertEquals("func",(functionName));
    }
    @Test
    void testCalculateFunctionNameWithInvalidFunctionName()
    {
        String str = "@fu<nc()";
        String functionName = EvaluatedFunction.calculateFunctionName(str);
        assertEquals("" , functionName);
    }
    @Test
    void testIsValideFunctioWithNullParameteer()
    {
        boolean isValid = UnEvaluatedFunction.isAValidFunction(null);
        assertEquals(false , isValid);
    }

    @Test
    void testIsValideFunctioWithEmptyParameteer()
    {
        boolean isValid = UnEvaluatedFunction.isAValidFunction("");
        assertEquals(false , isValid);
    }
    @Test
    void testIsValideFunctioWithInvalidFunctionName()
    {
        boolean isValid = UnEvaluatedFunction.isAValidFunction("@fu<nc()");
        assertEquals(false , isValid);
    }
    @Test
    void testIsValideFunctioWithValidFunctionName()
    {
        boolean isValid = UnEvaluatedFunction.isAValidFunction("@func()");
        assertEquals(true , isValid);
    }
    @Test
    void testIsValideFunctioWithValidFunctionNameWithParameter()
    {
        boolean isValid = UnEvaluatedFunction.isAValidFunction("@func(parameter)");
        assertEquals(true , isValid);
    }
    @Test
    void testIsValideFunctioWithValidFunctionNameWithParameterAndAnnotation()
    {
        boolean isValid = UnEvaluatedFunction.isAValidFunction("@func(parameter)");
        assertEquals(true , isValid);
    }
    @Test
    void testIsValideFunctioWithValidFunctionNameWithParameterAndAnnotationAndSpace()
    {
        boolean isValid = UnEvaluatedFunction.isAValidFunction("@func(parameter) ");
        assertEquals(true , isValid);
    }
    @Test
    void testIsValideFunctioWithValidFunctionNameWithParameterAndAnnotationAndSpaceAndSpace()
    {
        boolean isValid = UnEvaluatedFunction.isAValidFunction("@func(parameter)  ");
        assertEquals(true , isValid);
    }  
    @Test
    void testIsValideFunctioWithInValidFunctionNameWithParameter()
    {
        boolean isValid = EvaluatedFunction.isAValidFunction("@ (parameter)");
        assertEquals(false , isValid);
    }  
     @Test
    void testIsValideFunctioWithAValidFunctionNameWithBlankParameter()
    {
        boolean isValid = EvaluatedFunction.isAValidFunction("@func(   )");
        assertEquals(true , isValid);
    }  
    @Test
    void testCheckFunctionNameSyntaxWhitNull(){
        boolean isValid = UnEvaluatedFunction.checkFunctionNameSyntax(null);
        assertEquals(false , isValid);
    }
    @Test
    void testCheckFunctionNameSyntaxWhitEmpty(){
        boolean isValid = UnEvaluatedFunction.checkFunctionNameSyntax("");
        assertEquals(false , isValid);
    }
}
