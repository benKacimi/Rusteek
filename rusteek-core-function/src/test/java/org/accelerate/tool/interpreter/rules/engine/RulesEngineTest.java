package org.accelerate.tool.interpreter.rules.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.rusteek.engine.RusteekEngine;
import org.rusteek.engine.ThreadContext;


class RulesEngineTest {
    RusteekEngine engine = new RusteekEngine();
    
    @Test
    void testRuleEngineWithAFunctionThatContentAFunctionInParameter()
    {
       String result = engine.execute("@anonymize.anonymiseUUID(${oldId})");
       assertNotEquals(null,result,"Result cannot be null" );
       assertEquals(38,result.length(),"Size of the key expected is 38 instead of : " + result.length());
       ThreadContext.remove();
       assertNull(ThreadContext.getVariableValue("var1"));
    }
 

    @Test
    void testRuleEngineWithThreadlocalValueAndAParameterInFunction()
    {
      Map<String, String> localProperties = new HashMap<String, String>();
      localProperties.put("customerName","zdzldfsfvklsfcsklfcsfdkl");
      localProperties.put("var2","bar");
      
      ThreadContext.setThreadDataMap(localProperties);
      String result = engine.execute("@hash.SHA3256(${customerName})");

      assertEquals("9907ee8f699afdad889273188ba392901fc1d6f8e7e9d11abdd9b9e2fa088fc0",result);
      ThreadContext.remove();
      assertNull(ThreadContext.getVariableValue("customerName"));
    }
    
    
}
