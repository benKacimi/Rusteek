package org.accelerate.tool.interpreter.rules.engine.lexer;

import java.util.regex.Pattern;

import org.accelerate.tool.interpreter.rules.engine.ThreadContext;

import lombok.AccessLevel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class Variable implements INode{
    private static final Pattern VARIABLE_SYNTAX_REGEX = Pattern.compile("^[a-zA-Z0-9.]+$");
    private String keyName;

    protected static Variable createInstance(final String lexem){
        if (lexem == null || "".equals(lexem))
            return null;
        else if (lexem.length() < 2)
            return null;
        int closingAccolade = lexem.indexOf('}');
        if (lexem.charAt(1) == '{' && closingAccolade != -1){
            Variable variable = new Variable();
            variable.setKeyName((lexem.substring(2,closingAccolade)));
            if (checkKeyNameSyntax(variable.getKeyName().trim()))
                return variable;
        }
        return null;
    }

    protected static boolean checkKeyNameSyntax(final String keyName){
        if (keyName == null || "".equals(keyName))
            return false;
        
        return (VARIABLE_SYNTAX_REGEX.matcher(keyName)).matches() ;
    }

    @Override
    public String apply() {
        String result = null;
        
        result = ThreadContext.getVariableValue(keyName);
        if (result == null)
            result = System.getProperty(keyName);
        if (result == null)
            result = error();
        return result;
    }

    private String error(){
        return "${"+keyName+"}";
    }
}
