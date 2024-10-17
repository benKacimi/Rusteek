package org.rusteek.engine.lexer;

import org.rusteek.engine.lexer.execption.InvalidLeafSyntaxException;
public final class Lexer {

    public RootNode lex(final String lexem){
        return lex(lexem,false);
    }
    public RootNode lex(final String lexem, final boolean recursiveResolution){
        String localLexem = lexem;

        if (localLexem == null)
            localLexem = "";
        RootNode root = CompilationCacheManager.getEntryInCache(localLexem, recursiveResolution);
        if (root == null){
            root = new RootNode();
            lex (localLexem,root,recursiveResolution);
            CompilationCacheManager.setEntryInCache(localLexem, root, recursiveResolution);
        }
        return root;
    }

    protected void lex(final String lexem, final RootNode parent, final boolean recursiveResolution){ 
        
        if (!"".equals(lexem)) {
            Leaf leaf  = initiateLeafInstance(lexem, recursiveResolution);
            
            parent.addChild(leaf);
            lex(leaf.getNextLexem(lexem), parent, recursiveResolution);
        } 
    }

    private Leaf initiateLeafInstance( final String lexem, final boolean recursiveResolution){
        
        Leaf leaf  = LeafFactory.createInstance(lexem.charAt(0),recursiveResolution);
        boolean initInstanceSuccess = false;
        for (int i=0; i<=1 && !initInstanceSuccess;i++){
            try {
                leaf.initInstance(lexem);
                initInstanceSuccess = true;
            } catch (InvalidLeafSyntaxException e) {
                leaf = LeafFactory.createInstance(Leaf.DEFAULT_LEAF,recursiveResolution);
            }
        }
        return leaf;
    }
}