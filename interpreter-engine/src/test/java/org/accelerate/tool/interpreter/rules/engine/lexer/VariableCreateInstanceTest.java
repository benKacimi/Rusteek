package org.accelerate.tool.interpreter.rules.engine.lexer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.accelerate.tool.interpreter.rules.engine.lexer.execption.InvalidVariableSyntaxException;
import org.junit.Test;

public class VariableCreateInstanceTest {
    @Test
    public void testCreateInstanceWithNullLexem()
    {
        Variable var = new Variable();
        try {
            var.initInstance(null);
            assertTrue(false);
        } catch (InvalidVariableSyntaxException e) {
            assertTrue(true);
        }
    }    
    
    @Test
    public void testCreateInstanceWithBlankLexem()
    {
        Variable var = new Variable();
        String str = "";
        try {
            var.initInstance(str);
            assertTrue(false);
        } catch (InvalidVariableSyntaxException e) {
            assertTrue(true);
        }
    }
    @Test
    public void testCreateInstanceWithSpace()
    {
        String str = "    ";
        Variable var = new Variable();
         try {
            var.initInstance(str);
            assertTrue(false);
        } catch (InvalidVariableSyntaxException e) {
            assertTrue(true);
        }
    }  
     @Test
    public void testCreateInstanceWithOnlyTheSpecialCaractere()
    {
        String str = "$";
        Variable var = new Variable();
         try {
            var.initInstance(str);
            assertTrue(false);
        } catch (InvalidVariableSyntaxException e) {
            assertTrue(true);
        }
    }  
    @Test
    public void testCreateInstanceWithOnlyAAccolade()
    {
        String str = "${";
       Variable var = new Variable();
         try {
            var.initInstance(str);
            assertTrue(false);
        } catch (InvalidVariableSyntaxException e) {
            assertTrue(true);
        }
    }  
    @Test
    public void testCreateInstanceWithNoKeyName()
    {
        String str = "${}";
       Variable var = new Variable();
         try {
            var.initInstance(str);
            assertTrue(false);
        } catch (InvalidVariableSyntaxException e) {
            assertTrue(true);
        }
    } 
    @Test
    public void testCreateInstanceWithSpaceAsKeyName()
    {
        String str = "${ }";
        Variable var = new Variable();
        try {
            var.initInstance(str);
            assertTrue(false);
        } catch (InvalidVariableSyntaxException e) {
            assertTrue(true);
        }
    } 
    @Test
    public void testCreateInstanceWithSpaceBeforeAndAfterKeyName()
    {
        String str = "${ aVar }";
        Variable var = new Variable();
         try {
            var.initInstance(str);
             assertEquals(" aVar ",(var.getKeyName()));
        } catch (InvalidVariableSyntaxException e) {
            assertTrue(false);
        }
       
    } 
    @Test
    public void testCreateVariableValidInstance()
    {
        String str = "${aVariable}";
        Variable var = new Variable();
        try {
            var.initInstance(str);
            assertEquals("aVariable",(var.getKeyName()));
        } catch (InvalidVariableSyntaxException e) {
            assertTrue(false);
        }
    }
    @Test
    public void testCreateVariableValidInstanceWithPoint()
    {
        String str = "${foo.bar}";
        Variable var = new Variable();
        try {
            var.initInstance(str);
            assertEquals("foo.bar",(var.getKeyName()));
        } catch (InvalidVariableSyntaxException e) {
            assertTrue(false);
        }
       
    }
    @Test
    public void testCreateVariableInvalidInstanceWithParenthesis()
    {
        String str = "${foo(bar}";
       Variable var = new Variable();
        try {
            var.initInstance(str);
            assertTrue(false);
        } catch (InvalidVariableSyntaxException e) {
            assertTrue(true);
        }
    }   
    @Test
    public void testCreateVariableInvalidInstanceWithSpaceIntoKeyName()
    {
        String str = "${foo bar}";
        Variable var = new Variable();
        try {
            var.initInstance(str);
            assertTrue(false);
        } catch (InvalidVariableSyntaxException e) {
            assertTrue(true);
        }
    }      
    @Test
    public void testValidateAnInValidVariable()
    {
        String str = "${foo bar";
        boolean isValid =Variable.isAValidVariable(str);
        assertTrue(!isValid);
    }  
    @Test
    public void testValidateAValidVariable()
    {
        String str = "${foo}";
        boolean isValid =Variable.isAValidVariable(str);
        assertTrue(isValid);
    }  
    @Test
    public void testValidateAnInValidVariableLenghtLessThanFour()
    {
        String str = "${}";
        boolean isValid =Variable.isAValidVariable(str);
        assertTrue(!isValid);
    }  
    @Test
    public void testValidateAnInValidVariableWithBlankName()
    {
        String str = "${ }";
        boolean isValid =Variable.isAValidVariable(str);
        assertTrue(!isValid);
    } 
    @Test
    public void testValidateAValidVariableWithOneChar()
    {
        String str = "${a}";
        boolean isValid =Variable.isAValidVariable(str);
        assertTrue(isValid);
    } 
    @Test
    public void testValidateAnInValidVariableWithOneChar()
    {
        String str = "${}a";
        boolean isValid =Variable.isAValidVariable(str);
        assertTrue(!isValid);
    } 
     @Test
    public void testNextLexem()
    {
        String lexem = "${foo}bar";
        Variable var = new Variable();
         try {
            var.initInstance(lexem);
            assertEquals("foo",(var.getKeyName()));
            String nextLexem = var.getNextLexem(lexem);
            assertEquals("bar",nextLexem);
        } catch (InvalidVariableSyntaxException e) {
            assertTrue(false);
        }
    } 
}
