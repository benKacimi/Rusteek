package org.accelerate.tool.interpreter.rules.engine.lexer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.accelerate.tool.interpreter.rules.engine.lexer.execption.InvalidLeafSyntaxException;
import org.junit.Test;

public class LiteralCreateInstanceTest {
    @Test
    public void testCreateInstanceWithNullLexem()
    {
         Literal literal = new Literal();
        try {
            literal.initInstance(null);
           assertTrue(false);
        } catch (InvalidLeafSyntaxException e) {
            assertTrue(true);
        }
    }    
    
    @Test
    public void testCreateInstanceWithBlankLexem()
    {
        String str = "";
         Literal literal = new Literal();
        try {
            literal.initInstance(str);
            assertEquals("",literal.getValue());
        } catch (InvalidLeafSyntaxException e) {
            assertTrue(false);
        }
        
    }
    @Test
    public void testCreateInstanceWithSpace()
    {
        String str = " ";
        Literal literal = new Literal();
        try {
            literal.initInstance(str);
            assertEquals(" ",literal.getValue());
        } catch (InvalidLeafSyntaxException e) {
            assertTrue(false);
        }
       
    }
    @Test
    public void testCreateInstanceWithEmailAdress()
    {
        String str = "foo@bar.com";
        Literal literal = new Literal();
        try {
            literal.initInstance(str);
            assertEquals("foo@bar.com",literal.getValue());
        } catch (InvalidLeafSyntaxException e) {
            assertTrue(false);
        }
    }
    @Test
    public void testCreateInstanceWithEmailAdressPlusAFunction()
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
    @Test
    public void testCreateInstanceWithInvalidVariable()
    {
        String str = "foo${";
         Literal literal = new Literal();
        try {
            literal.initInstance(str);
            assertEquals("foo${",literal.getValue());
        } catch (InvalidLeafSyntaxException e) {
            assertTrue(false);
        }
    }
    @Test   
    public void testCreateInstanceWithDollardAtTheEnd()
    {
        String str = "foo$";
        Literal literal = new Literal();
        try {
            literal.initInstance(str);
            assertEquals("foo$",literal.getValue());
        } catch (InvalidLeafSyntaxException e) {
            assertTrue(false);
        }
        
    }
    @Test   
    public void testCreateInstanceWithOneCaractere()
    {
        String str = "a";
        Literal literal = new Literal();
        try {
            literal.initInstance(str);
            assertEquals("a",literal.getValue());
        } catch (InvalidLeafSyntaxException e) {
            assertTrue(false);
        }
    }  
    @Test   
    public void testCreateInstanceWithEqualCaractere()
    {
        String str = "foo = bar";
        Literal literal = new Literal();
        try {
            literal.initInstance(str);
            assertEquals("foo = bar expected instead of : "+literal.getValue(), "foo = bar", literal.getValue());
        } catch (InvalidLeafSyntaxException e) {
            assertTrue(false);
        }
    }     
}

