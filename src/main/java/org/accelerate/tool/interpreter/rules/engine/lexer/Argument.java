package org.accelerate.tool.interpreter.rules.engine.lexer;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class Argument extends Node {

    private String name;

    protected static List<Argument> createArgumentList(final String strArguments){
        
        String[] arrArgument = strArguments.split(",");
        Lexer lexer = new Lexer();
        List<Argument> argumentList = null;

        if ("".equals(arrArgument[0]))
            return null;
        for (String aArguments : arrArgument){
            Argument argumentResult = new Argument();
            String[] paramNames = aArguments.split("=");
            if (paramNames.length == 2){
                argumentResult.name = paramNames[0].trim();
                aArguments = paramNames[1];
            }
            lexer.lex(aArguments.trim(),argumentResult);
            if(argumentList == null)
                argumentList = new ArrayList<>();
            argumentList.add(argumentResult);
        }
        return argumentList;
    }

    protected static String calculateFunctionParameter(final String phrase){
        if (phrase == null)
            return null;
        
        int openingParenthesisIndex = phrase.indexOf("(");
        int parenthesisBalance = -1;
        boolean initCount = false;

        for(int i = 0 ; i < phrase.length() && parenthesisBalance != 0; i++) {
            if (phrase.charAt(i) == '(') {
                if (!initCount){
                    parenthesisBalance = 0;
                    initCount = true;
                }
                parenthesisBalance++;
            } else if (phrase.charAt(i) == ')'){
                if (!initCount)
                    return null;
                parenthesisBalance--;
                if (parenthesisBalance == 0)
                    return (phrase.substring(openingParenthesisIndex+1,i));
            }
        }
        return null;
    }
}
