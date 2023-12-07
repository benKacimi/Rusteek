package org.accelerate.tool.interpreter.rules.engine.lexer.execption;

public class InvalidFunctionSyntaxException extends InvalidLeafSyntaxException {
    
    public InvalidFunctionSyntaxException(final String message) {
        super((new StringBuilder("WARNING [" )
            .append( message)
            .append(  "]: ")
            .append(" is not a valid function syntax"))
            .toString());
    }
    
}
