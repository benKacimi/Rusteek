package org.accelerate.tool.interpreter.rules.engine.lexer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class LeafFactoryTest {
    @Test
    void testEvaluatedFunctionCreation()
    {
        Leaf aLeaf =LeafFactory.createInstance('@');
        assertTrue(aLeaf instanceof EvaluatedFunction);
    }

    @ParameterizedTest
    @ValueSource(chars = {'@','#','$','a'})
    void testCreateLeafInstance(Character specialCharacter)
    {
         Leaf aLeaf =LeafFactory.createInstance(specialCharacter);
         assertNotNull(aLeaf);  
    }
}
