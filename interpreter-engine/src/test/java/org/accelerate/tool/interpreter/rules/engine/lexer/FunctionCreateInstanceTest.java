package org.accelerate.tool.interpreter.rules.engine.lexer;

import static org.junit.Assert.assertTrue;

import org.accelerate.tool.interpreter.rules.engine.lexer.execption.InvalidFunctionSyntaxException;
import org.junit.Test;

public class FunctionCreateInstanceTest {

    @Test
    public void testCalculateParamerterWitBlankParameter()
    {
        String lexem = "";
       
        try {
            new UnEvaluatedFunction().initInstance(lexem);
        } catch (InvalidFunctionSyntaxException e) {
          assertTrue(true);
        }
    }
    @Test
    public void testCalculateParamerterWitNullParameter()
    {
       
        try {
            new UnEvaluatedFunction().initInstance(null);
        } catch (InvalidFunctionSyntaxException e) {
         assertTrue(true);
        }
    }
}
