package org.accelerate.tool.interpreter.rules;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class HashRuleTest {

    @Test
    public void testHashSHA_3_256_null_input()
    {
        Hash hashRule = new Hash();
        String result = hashRule.hash(null);
        assertEquals("Result must be empty when hashing a null value","", result);
    }

    @Test
    public void testHashSHA_3_256_blank_input()
    {
        Hash hashRule = new Hash();
        String result = hashRule.hash("");
        assertEquals("Result must be empty when hashing a null value","", result);
    }

    @Test
    public void testHashSHA_3_256_foo_input()
    {
        Hash hashRule = new Hash();
        String result = hashRule.hash("foo");
        assertEquals("Result must be 76d3bc41c9f588f7fcd0d5bf4718f8f84b1c41b20882703100b9eb9413807c01 when hashing foo",
                     "76d3bc41c9f588f7fcd0d5bf4718f8f84b1c41b20882703100b9eb9413807c01",
                     result);
    }
    
}
