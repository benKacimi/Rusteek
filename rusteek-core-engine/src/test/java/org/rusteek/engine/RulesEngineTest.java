package org.rusteek.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RulesEngineTest {
   RusteekEngine engine = new RusteekEngine();

   @Test
   void testRuleEngineWithNullInputParameter()
   {
      String foo = null;
      String result = engine.execute(foo);
      assertEquals(null, result, "Null expeted instead of : " + result);
   }

   @Test
   void testRuleEngineWithEmptyStringInputParameter()
    {
       String result = engine.execute("");
       assertEquals("", result,"Empty String expeted instead of : " + result);
    }

   @Test
   void testRuleEngineALitteral()
    {
       String result = engine.execute("foo bar");
       assertEquals("foo bar",result,"foo bar  expeted instead of : " + result);
    }

   @Test
   void testRuleEngineWithAFunction()
    {
       String result = engine.execute("@function()");
       assertEquals("foo",result,"foo expected instead of : " + result);
    }

   @Test
   void testRuleEngineWithAFunctionThatDoesntExist()
    {
       String result = engine.execute("@nonExistentFunction()");
       assertEquals("@nonExistentFunction()",result,"@nonExistentFunction() expected instead of : " + result);
    }

   @Test
   void testRuleEngineWithAFunctionThatDoesntExistWithParameterToEval()
    {
       String result = engine.execute("@nonExistentFunction(@function())");
       assertEquals("@nonExistentFunction(foo)",result,"@nonExistentFunction(foo) expected instead of : " + result);
    }

   @Test
   void testRuleEngineWithAFunctionThatDoesntExistWithParameterAndParameterName()
    {
       String result = engine.execute("@nonExistentFunction(bar = @function())");
       assertEquals("@nonExistentFunction(bar=foo)",result,"@nonExistentFunction(bar=foo) expected instead of : " + result);
    }
   @Test
   void testRuleEngineWithAFunctionThatDoesntExistWithParameterAndEqualCaratere()
    {
       String result = engine.execute("@nonExistentFunction( = @function())");
       assertEquals("@nonExistentFunction(=foo)",result,"@nonExistentFunction(=foo) expected instead of : " + result);
    }
   @Test
   void testRuleEngineWithAFunctionThatDoesntExistWithAnExistingClass()
    {
       String result = engine.execute("@function.nonExistentFunction()");
       assertEquals("@function.nonExistentFunction()",result,"@function.nonExistentFunction() expected instead of : " + result);
    }
   @Test
   void testRuleEngineWithAFunctionThatDoesntExistWithAnNonExistingClass()
    {
       String result = engine.execute("@nonExistentClass.nonExistentFunction()");
       assertEquals("@nonExistentClass.nonExistentFunction()",result,"@nonExistentClass.nonExistentFunction() expected instead of : " + result);
    }

   @Test
   void testRuleEngineWithAFunctionWithBackSpaceParameter()
    {
       String result = engine.execute("@function( )");
       assertEquals("foo",result,"foo expected instead of : " + result);
    }
   @Test
   void testRuleEngineWithAFunctionThatContaindAWrongNumberOfParameter()
    {
       String result = engine.execute("@function(foo= bar)");
       assertEquals("@function(foo=bar)",result,"@function(foo=bar) expected instead of : " + result);
       //Note : we lost the backspace caracter in the result (foo=bar instead of foo= bar)
    }

   @Test
   void testRuleEngineWithAFunctionThatDoesntReturnAString()
    {
       String result = engine.execute("@function.functionWithWrongReturnType()");
       assertEquals("@function.functionWithWrongReturnType()",result,"@functionWithWrongReturnType() expected instead of : " + result);
    }

   @Test
   void testRuleEngineWithANonEvaluatedFunctionWithoutArgument()
    {
       String result = engine.execute("#function.aFunction( )");
       assertEquals("@function.aFunction()",result,"@function.aFunction() expected instead of : " + result);
    }
   @Test
   void testRuleEngineWithANonEvaluatedFunctionWithArgumentToEvaluate()
    {
       String result = engine.execute("#function.aFunction(@function())");
       assertEquals("@function.aFunction(foo)",result,"@function.aFunction(foo) expected instead of : " + result);
    }
   @Test
   void testRuleEngineWithANonEvaluatedFunction()
    {
       String result = engine.execute("#function()");
       assertEquals("@function()",result,"@function() expected instead of : " + result);
    }

    @Test
    void testRuleEngineWithANonEvaluatedFunctionWithBlank()
    {
         String result = engine.execute("  #function()");
         assertEquals("@function()",result,"@function() expected instead of : " + result);
    }

    @Test
    void testRuleEngineWithDOubleNonEvaluatedFunctionWithBlank()
    {
         String result = engine.execute("  #function(   #function())");
         assertEquals("@function(@function())",result,"@function(@function()) expected instead of : " + result);
    }
    @Test
    void testRuleEngineWithDOubleNonEvaluatedFunctionWithBlankAndAEvalFunctionInParamater()
    {
         String result = engine.execute("  #function(   #function(@function()))");
         assertEquals("@function(@function(foo))",result,"@function(@function(foo)) expected instead of : " + result);
    }
   
   @ParameterizedTest
   @ValueSource(strings = {"${var2}","${ var2 }"})
   void testRuleEngineWithThreadlocalValue(String lexem)
   {
      Map<String, String> localProperties = new HashMap<String, String>();
      localProperties.put("var1","foo");
      localProperties.put("var2","bar");
      ThreadContext.setThreadDataMap(localProperties);
       String result = engine.execute(lexem);
       
       assertEquals("bar",result,"bar expected instead of : " + result);
       ThreadContext.remove();
       assertNull(ThreadContext.getVariableValue("var1"));
   }

   @Test
   void testRecursivityWithVarible()
   {
      Map<String, String> localProperties = new HashMap<String, String>();
      localProperties.put("var1","${var2}");
      localProperties.put("var2","bar");
      
      ThreadContext.setThreadDataMap(localProperties);
      String result = engine.execute("${ var1 }");
       
      assertEquals("bar",result,"bar expected instead of : " + result);
      ThreadContext.remove();
      assertNull(ThreadContext.getVariableValue("var1"));
      
   } 

   @Test
   void testRuleEngineWithVariableWithoutValue()
   {
      String result = engine.execute("${ var2 }");
      assertEquals("${var2}",result,"${var2} expected instead of : " + result);
   } 

   @Test
   void testRuleEngineWithDoubleSPecialCarFunction()
    {
       String result = engine.execute("@@function()");
       assertEquals("@foo",result,"foo expected instead of : " + result);
    }

   
    @Test
    void testRecursivityWithFunction()
    {
       Map<String, String> localProperties = new HashMap<String, String>();
       localProperties.put("var1","${var2}");
       localProperties.put("var2","function");
       localProperties.put("var3","()");
       
       ThreadContext.setThreadDataMap(localProperties);
       String result = engine.execute("@${ var1 }${var3}");
        
       assertEquals("foo",result,"foo expected instead of : " + result);
       ThreadContext.remove();
       assertNull(ThreadContext.getVariableValue("var1"));

      result = engine.execute("  #function()");
      assertEquals("@function()",result,"@function() expected instead of : " + result);
      
      //check if the cache has no effect if we change variable values
      Map<String, String> localProperties2 = new HashMap<String, String>();
      localProperties2.put("var1","bar");
      localProperties2.put("var2","function");
      localProperties2.put("var3","()");
      ThreadContext.setThreadDataMap(localProperties2);
      result = engine.execute("@${ var1 }${var3}");
      assertEquals("@bar()",result,"@bar() expected instead of : " + result);

    } 
}