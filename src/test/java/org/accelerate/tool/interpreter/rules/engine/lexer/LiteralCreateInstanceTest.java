package org.accelerate.tool.interpreter.rules.engine.lexer;

import static org.junit.Assert.*;

import org.accelerate.tool.interpreter.rules.engine.lexer.Literal;
import org.junit.Test;

public class LiteralCreateInstanceTest {
    @Test
    public void testCreateInstanceWithNullLexem()
    {
        Literal literal = Literal.createInstance(null);
        assertTrue(null == literal);
    }    
    
    @Test
    public void testCreateInstanceWithBlankLexem()
    {
        String str = "";
        Literal literal = Literal.createInstance(str);
        assertTrue("".equals(literal.getValue()));
    }
    @Test
    public void testCreateInstanceWithSpace()
    {
        String str = " ";
        Literal literal = Literal.createInstance(str);
        assertTrue(" ".equals(literal.getValue()));
    }
    @Test
    public void testCreateInstanceWithEmailAdress()
    {
        String str = "foo@bar.com";
        Literal literal = Literal.createInstance(str);
        assertTrue("foo@bar.com".equals(literal.getValue()));
    }
    @Test
    public void testCreateInstanceWithEmailAdressPlusAFunction()
    {
        String str = "foo@bar.com@func()";
        Literal literal = Literal.createInstance(str);
        assertTrue("foo@bar.com".equals(literal.getValue()));
    }
    @Test
    public void testCreateInstanceWithInvalidVariable()
    {
        String str = "foo${";
        Literal literal = Literal.createInstance(str);
        assertTrue("foo${".equals(literal.getValue()));
    }
    @Test   
    public void testCreateInstanceWithDollardAtTheEnd()
    {
        String str = "foo$";
        Literal literal = Literal.createInstance(str);
        assertTrue("foo$".equals(literal.getValue()));
    }
    @Test   
    public void testCreateInstanceWithOneCaractere()
    {
        String str = "a";
        Literal literal = Literal.createInstance(str);
        assertTrue("a".equals(literal.getValue()));
    }     
}

