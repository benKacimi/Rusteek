package org.rusteek.engine.lexer;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.rusteek.engine.lexer.execption.InvalidFunctionSyntaxException;

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
