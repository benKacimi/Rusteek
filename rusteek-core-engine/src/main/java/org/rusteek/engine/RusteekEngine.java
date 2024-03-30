package org.rusteek.engine;

import org.rusteek.engine.lexer.Lexer;
import org.rusteek.engine.lexer.RootNode;

public final class RusteekEngine {
    
    protected Lexer tokenizer = new Lexer();

    public String execute(final String instructionLine){

        if (instructionLine == null || "".equals(instructionLine))
            return instructionLine;
        
        RootNode root = tokenizer.lex(instructionLine);
        return root.apply();
    }
}
