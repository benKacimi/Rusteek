package org.accelerate.tool.interpreter.rules.engine.lexer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FunctionCreateInstanceTest {

    @Test
    public void testCalculateParamerterWitBlankParameter()
    {
        String lexem = "";
        Function aFunc = Function.createInstance(lexem);
        assertEquals(null , aFunc);
    }
    @Test
    public void testCalculateParamerterWitNullParameter()
    {
        Function aFunc = Function.createInstance(null);
        assertEquals(null , aFunc);
    }
}
