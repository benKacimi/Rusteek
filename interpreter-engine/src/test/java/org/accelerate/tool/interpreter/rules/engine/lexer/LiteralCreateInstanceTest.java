package org.accelerate.tool.interpreter.rules.engine.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.accelerate.tool.interpreter.rules.engine.lexer.execption.InvalidLeafSyntaxException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


class LiteralCreateInstanceTest {
    @Test
    void testCreateInstanceWithNullLexem()
    {
         Literal literal = new Literal();
        try {
            literal.initInstance(null);
           assertTrue(false);
        } catch (InvalidLeafSyntaxException e) {
            assertTrue(true);
        }
    }    
    
    @ParameterizedTest
    @ValueSource(strings = {""," ","a","@","#","$","#@$","   ","foo@bar.com","foo${","foo$","foo = bar","@fonc($zeerc}", "@funcé()"})
    void testCreateLiteralInstance(String str)
    {
        Literal literal = new Literal();
        try {
            literal.initInstance(str);
            assertEquals(str,literal.getValue());
        } catch (InvalidLeafSyntaxException e) {
            assertTrue(false);
        }
    }
    
    @Test
    void testCreateInstanceWithEmailAdressPlusAFunction()
    {
        String str = "foo@bar.com@func()";
        Literal literal = new Literal();
        try {
            literal.initInstance(str);
            assertEquals("foo@bar.com",literal.getValue());
        } catch (InvalidLeafSyntaxException e) {
            assertTrue(false);
        }
    }
}

