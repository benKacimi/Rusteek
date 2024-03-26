package org.accelerate.tool.interpreter.rules.engine.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RootNodeTest {
     @Test   
    void testApplyWithNoChildren()
    {
        RootNode root = new RootNode();
        String result = root.apply();
        assertEquals("", result);
    }
}
