package org.rusteek.engine.lexer;

import org.rusteek.engine.lexer.execption.InvalidLeafSyntaxException;

public interface Leaf extends Node {
    public String getNextLexem(final String lexem);
    public void   initInstance(final String lexem) throws InvalidLeafSyntaxException;
    public static char DEFAULT_LEAF = ' ';
}
