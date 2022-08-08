package org.accelerate.tool.interpreter.rules.engine;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


public class RulesEngineTest {
    RulesEngine engine = new RulesEngine();
    
    @Test
    public void testRuleEngineWithNullInputParameter()
    {
       String foo = null;
       String result = engine.execute(foo);
       assertEquals("Null expeted instead of : " + result,  null, result);
    }

    @Test
    public void testRuleEngineWithEmptyStringInputParameter()
    {
       String result = engine.execute("");
       assertEquals("Empty String expeted instead of : " + result,  "", result);
    }

    @Test
    public void testRuleEngineALitteral()
    {
       String result = engine.execute("foo bar");
       assertEquals("foo bar  expeted instead of : " + result,  "foo bar",result);
    }

    @Test
    public void testRuleEngineWithAFunction()
    {
       String result = engine.execute("@function()");
       assertEquals("foo expected instead of : " + result,  "foo",result);
    }

    @Test
    public void testRuleEngineWithAFunctionThatDoesntExist()
    {
       String result = engine.execute("@nonExistentFunction()");
       assertEquals("@nonExistentFunction() expected instead of : " + result,  "@nonExistentFunction()",result);
    }
    @Test
    public void testRuleEngineWithAFunctionThatDoesntExistWithAnExistingClass()
    {
       String result = engine.execute("@function.nonExistentFunction()");
       assertEquals("@function.nonExistentFunction() expected instead of : " + result,  "@function.nonExistentFunction()",(result));
    }
    @Test
    public void testRuleEngineWithAFunctionThatDoesntExistWithAnNonExistingClass()
    {
       String result = engine.execute("@nonExistentClass.nonExistentFunction()");
       assertEquals("@nonExistentClass.nonExistentFunction() expected instead of : " + result,  "@nonExistentClass.nonExistentFunction()",(result));
    }

    @Test
    public void testRuleEngineWithAFunctionWithBackSpaceParameter()
    {
       String result = engine.execute("@function( )");
       assertEquals("foo expected instead of : " + result,  "foo",(result));
    }
    @Test
    public void testRuleEngineWithAFunctionThatContaindAWrongNumberOfParameter()
    {
       String result = engine.execute("@function(foo=bar)");
       assertEquals("@function(foo=bar) expected instead of : " + result,  "@function(foo=bar)",(result));
    }

    @Test
    public void testRuleEngineWithAFunctionThatDoesntReturnAString()
    {
       String result = engine.execute("@function.functionWithWrongReturnType( )");
       assertEquals("@functionWithWrongReturnType() expected instead of : " + result,  "@function.functionWithWrongReturnType( )",(result));
    }

    @Test
    public void testRuleEngineWithThreadlocalValue()
    {
      Map<String, String> localProperties = new HashMap<String, String>();
      localProperties.put("var1","foo");
      localProperties.put("var2","bar");
      ThreadContext.setThreadDataMap(localProperties);
       String result = engine.execute("${var2}");
       
       assertEquals("bar expected instead of : " + result ,"bar",(result));
       ThreadContext.remove();
       assertNull(ThreadContext.getVariableValue("var1"));
       

    }
    @Test
    public void testRuleEngineWithThreadlocalValueAndSpaceCaratere()
    {
      Map<String, String> localProperties = new HashMap<String, String>();
      localProperties.put("var1","foo");
      localProperties.put("var2","bar");
      
      ThreadContext.setThreadDataMap(localProperties);
      String result = engine.execute("${ var2 }");
       
      assertEquals("bar expected instead of : " + result ,"bar",(result));
      ThreadContext.remove();
      assertNull(ThreadContext.getVariableValue("var1"));
    }   
}
