package org.accelerate.tool.interpreter.rules.engine.lexer;

import java.util.ArrayList;
import java.util.Collections;
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
public class Argument extends RootNode {

    private String name = "";

    protected static List<Argument> createArgumentList(final String strArguments){
        
        String[] arrArgument = strArguments.split(",");
        Lexer lexer = new Lexer();
        List<Argument> argumentList = null;

        if ("".equals(arrArgument[0]))
            return Collections.emptyList();
        for (String aArguments : arrArgument){
            Argument argumentResult = new Argument();
            String[] paramNames = aArguments.split("=");
            if (paramNames.length == 2 && paramNames[0].length() > 0 && paramNames[1].length() > 0){
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
        if (phrase == null || phrase.isEmpty())
            return "";
        
        int openingParenthesisIndex = phrase.indexOf("(");
        int parenthesisBalance = 0;
        if (openingParenthesisIndex == -1)  {
            return "";
        }
        for(int i = openingParenthesisIndex ; i < phrase.length(); i++) {
            if (phrase.charAt(i) == '(') {
                parenthesisBalance++;
            } else if (phrase.charAt(i) == ')'){
                parenthesisBalance--;
                if (parenthesisBalance == 0)
                    return (phrase.substring(openingParenthesisIndex+1,i));
            }
        }
        return "";
    }
}
