package org.rusteek.engine;

import org.rusteek.engine.lexer.Lexer;
import org.rusteek.engine.lexer.RootNode;

public final class RusteekEngine {
    
    protected Lexer tokenizer = new Lexer();

    public String execute(final String instructionLine){

        if (instructionLine == null || "".equals(instructionLine))
            return instructionLine;
        String result = instructionLine;

        RootNode root = null;
        for (int recursiveloop = 0;  recursiveloop <= 2;recursiveloop++){
            root = tokenizer.lex(result,true);
            result = root.apply();
        }        
        root = tokenizer.lex(result,false);
        return root.apply();
    }
}
