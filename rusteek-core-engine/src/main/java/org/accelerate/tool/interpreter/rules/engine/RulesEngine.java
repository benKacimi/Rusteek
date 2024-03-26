package org.accelerate.tool.interpreter.rules.engine;

import org.accelerate.tool.interpreter.rules.engine.lexer.Lexer;
import org.accelerate.tool.interpreter.rules.engine.lexer.RootNode;

public final class RulesEngine {
    
    protected Lexer tokenizer = new Lexer();

    public String execute(final String instructionLine){

        if (instructionLine == null || "".equals(instructionLine))
            return instructionLine;
        
        RootNode root = tokenizer.lex(instructionLine);
        return root.apply();
    }
}
