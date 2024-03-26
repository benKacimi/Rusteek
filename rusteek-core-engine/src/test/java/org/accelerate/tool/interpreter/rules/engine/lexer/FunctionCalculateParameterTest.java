package org.accelerate.tool.interpreter.rules.engine.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class FunctionCalculateParameterTest {
    
    @ParameterizedTest
    @ValueSource(strings = {"", "   ", " ","@func()", "func(param()"})
    void testCalculateParamerterWitBlankParemeterOrWithInvalidParemeter(String str)
    {
        String parameter = Argument.calculateFunctionParameter(str);
        assertEquals("" , parameter);
    }
    @Test
    void testCalculateParamerterWitNullParemeter()
    {
        String parameter = Argument.calculateFunctionParameter(null);
        assertEquals("" , parameter);
    }
    @Test
    void testCalculateParamerterWithBackspaceArgument()
    {
        String str = "@func( )";
        String parameter = Argument.calculateFunctionParameter(str);
        assertEquals(" ",parameter,"One backspace caractere expected instead of : " + parameter);
    }
    @ParameterizedTest
    @CsvSource({
        "@func(param=true), param=true",
        "@func(param=true@func2()), param=true@func2()",
        "(a)(b)(c), a",
        "((foo(bar)john(do)))()func(), (foo(bar)john(do))",
        "@function(arg1=${foo}),arg1=${foo}",
        "@(param),param"
    })
    void testCalculateParamerterWithUnbalancedParenthesis(String str, String result)
    {
        String parameter = Argument.calculateFunctionParameter(str);
        assertEquals(result,parameter);
    }
}