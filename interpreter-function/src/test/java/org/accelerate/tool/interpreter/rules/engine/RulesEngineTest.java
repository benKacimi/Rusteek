package org.accelerate.tool.interpreter.rules.engine;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


public class RulesEngineTest {
    RulesEngine engine = new RulesEngine();
    
    @Test
    public void testRuleEngineWithAFunctionThatContentAFunctionInParameter()
    {
       String result = engine.execute("@anonymize.anonymiseUUID(${oldId})");
       assertNotEquals("Result cannot be null"  ,result,null);
       assertEquals("Size of the key expected is 38 instead of : " + result.length() ,38,result.length());
       assertNull(ThreadContext.getVariableValue("var1"));
    }
 

    @Test
    public void testRuleEngineWithThreadlocalValueAndAParameterInFunction()
    {
      Map<String, String> localProperties = new HashMap<String, String>();
      localProperties.put("customerName","zdzldfsfvklsfcsklfcsfdkl");
      localProperties.put("var2","bar");
      
      ThreadContext.setThreadDataMap(localProperties);
      String result = engine.execute("@encrypt.AESEncrypt($customerName)");
      assertNotEquals("@encrypt.AESEncrypt($customerName)",(result));
      ThreadContext.remove();
      assertNull(ThreadContext.getVariableValue("customerName"));
    }
    
    
}
