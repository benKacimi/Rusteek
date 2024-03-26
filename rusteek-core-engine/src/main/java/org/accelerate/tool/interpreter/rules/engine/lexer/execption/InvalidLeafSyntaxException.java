package org.accelerate.tool.interpreter.rules.engine.lexer.execption;

public class InvalidLeafSyntaxException extends Exception {
    public InvalidLeafSyntaxException(final String message) {
        super((message));
    }
}
