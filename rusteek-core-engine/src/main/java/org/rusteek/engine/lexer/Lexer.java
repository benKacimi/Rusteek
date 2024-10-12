package org.rusteek.engine.lexer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.rusteek.engine.lexer.execption.InvalidLeafSyntaxException;

public final class Lexer {

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
        
        if (!"".equals(lexem)) {
            Leaf leaf  = initiateLeafInstance(lexem);
            
            parent.addChild(leaf);
            lex(leaf.getNextLexem(lexem), parent);
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
                leaf = LeafFactory.createInstance(Leaf.DEFAULT_LEAF);
            }
        }
        return leaf;
    }
}