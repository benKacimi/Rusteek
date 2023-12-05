package org.accelerate.tool.interpreter.rules.engine.lexer;

import java.util.regex.Pattern;

import org.accelerate.tool.interpreter.rules.engine.ThreadContext;
import org.accelerate.tool.interpreter.rules.engine.lexer.execption.InvalidVariableSyntaxException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Variable implements Leaf {
    public static final char VARIABLE_CHAR = '$';
    private static final Pattern VARIABLE_SYNTAX_REGEX = Pattern.compile("^[a-zA-Z0-9.]+$");
    private String keyName;

    public  void initInstance(final String lexem) throws InvalidVariableSyntaxException {
        if (!checkAndSsetKeyNameSyntax(lexem)) {
            throw new InvalidVariableSyntaxException(lexem);
        }
    }

    protected static boolean isAValidVariable(final String lexem) {
        return new Variable().checkAndSsetKeyNameSyntax(lexem);
    }
    protected  boolean checkAndSsetKeyNameSyntax(final String lexem){
         if (lexem == null || lexem.isEmpty() || lexem.length() <= 3) {
            return false;
        }
        int closingAccolade = lexem.indexOf('}');

        if (lexem.charAt(1) == '{' && closingAccolade != -1) {
            keyName = lexem.substring(2, closingAccolade);
            if ("".equals(keyName))
                return false;
            return (VARIABLE_SYNTAX_REGEX.matcher(keyName.trim())).matches() ;
        }
        return false;        
    }

    @Override
    public String apply() {
        // Get the variable value from the ThreadContext or System properties
        String value = ThreadContext.getVariableValue(keyName);
        if (value == null) {
            value = System.getProperty(keyName);
        }
        
        // If the value is still null, return an error message
        if (value == null) {
            value = error();
        }
        
        return value;
    }

    private String error(){
        return VARIABLE_CHAR+"{"+keyName+"}";
    }

    @Override
    public String getNextLexem(final String lexem) {
        String result = lexem.substring(keyName.length()+3,lexem.length());
        keyName = keyName.trim();
        return result;
    }
}
