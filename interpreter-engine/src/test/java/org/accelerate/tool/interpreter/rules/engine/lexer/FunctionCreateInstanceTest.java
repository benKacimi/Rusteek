package org.accelerate.tool.interpreter.rules.engine.lexer;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.accelerate.tool.interpreter.rules.engine.lexer.execption.InvalidFunctionSyntaxException;
import org.junit.jupiter.api.Test;

class FunctionCreateInstanceTest {

    @Test
    void testCalculateParamerterWitBlankParameter()
    {
        String lexem = "";
       
        try {
            new UnEvaluatedFunction().initInstance(lexem);
        } catch (InvalidFunctionSyntaxException e) {
          assertTrue(true);
        }
    }
    @Test
    void testCalculateParamerterWitNullParameter()
    {
       
        try {
            new UnEvaluatedFunction().initInstance(null);
        } catch (InvalidFunctionSyntaxException e) {
         assertTrue(true);
        }
    }
}
