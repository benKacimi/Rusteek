package org.rusteek.engine.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.rusteek.engine.lexer.execption.InvalidVariableSyntaxException;

class VariableCreateInstanceTest {
    @Test
    void testCreateInstanceWithNullLexem()
    {
        Variable var = new Variable();
        try {
            var.initInstance(null);
            assertTrue(false);
        } catch (InvalidVariableSyntaxException e) {
            assertTrue(true);
        }
    }    
    
    @ParameterizedTest
    @ValueSource(strings = {""," ","   ","$","${","${}","${ }"})
    void testCreateVariableInstanceWithWrongSyntax(String str)
    {
        Variable var = new Variable();
    
        try {
            var.initInstance(str);
            assertTrue(false);
        } catch (InvalidVariableSyntaxException e) {
            assertTrue(true);
        }
    }
   
    @ParameterizedTest
    @CsvSource({
        "${ aVar },' aVar '",
        "${aVariable},aVariable",
         "${foo.bar},foo.bar"
    })
    void testCreateSimpleVariableInstance(String str, String expectedKeyName)
    {
        Variable var = new Variable();
         try {
            var.initInstance(str);
            assertEquals(expectedKeyName,(var.getKeyName()));
        } catch (InvalidVariableSyntaxException e) {
            assertTrue(false);
        }    
    } 
   
    @Test
    void testCreateVariableInvalidInstanceWithParenthesis()
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
    void testCreateVariableInvalidInstanceWithSpaceIntoKeyName()
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
    @ParameterizedTest
    @ValueSource(strings = {"${foo bar","${}","${ }","${}a"})
    void testValidateAnInValidVariable(String str)
    {
        boolean isValid =Variable.isAValidVariable(str);
        assertTrue(!isValid);
    }  
    @Test
    void testValidateAValidVariable()
    {
        String str = "${foo}";
        boolean isValid =Variable.isAValidVariable(str);
        assertTrue(isValid);
    }   
    @Test
    void testValidateAValidVariableWithOneChar()
    {
        String str = "${a}";
        boolean isValid =Variable.isAValidVariable(str);
        assertTrue(isValid);
    } 
    @Test
    void testNextLexem()
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
