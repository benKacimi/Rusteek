package org.accelerate.tool.interpreter.rules.engine.lexer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.accelerate.tool.interpreter.rules.engine.lexer.execption.InvalidLeafSyntaxException;

public class Lexer {

    private static Map<String,RootNode> compilationCache = new ConcurrentHashMap<>();

    public RootNode lex(final String lexem){
        String localLexem = lexem;

        if (localLexem == null)
            localLexem = "";
        RootNode root = compilationCache.get(localLexem);
        if (root == null){
            root = new RootNode();
            lex (localLexem,root);
            compilationCache.putIfAbsent(localLexem, root);
        }
        return root;
    }

    protected void lex(final String lexem, final RootNode parent){
        String current = (lexem != null) ? lexem : "";
        
        if (!"".equals(current)) {
            Leaf leaf  = initiateLeafInstance(current);
            
            parent.addChild(leaf);
            current = leaf.getNextLexem(lexem);
            lex(current, parent);
        } 
    }

    private Leaf initiateLeafInstance( final String lexem){
        
        Leaf leaf  = LeafFactory.createInstance(lexem.charAt(0));
        boolean initInstanceSuccess = false;
        for (int i=0; i<=1 && !initInstanceSuccess;i++){
            try {
                leaf.initInstance(lexem);
                initInstanceSuccess = true;
            } catch (InvalidLeafSyntaxException e) {
                if (i == 0)
                    leaf = LeafFactory.createInstance(Leaf.DEFAULT_LEAF);
            }
        }
        return leaf;
    }
}