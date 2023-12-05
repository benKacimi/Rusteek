package org.accelerate.tool.interpreter.rules.engine.lexer;

import org.accelerate.tool.interpreter.rules.engine.lexer.execption.InvalidLeafSyntaxException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Literal implements Leaf {
    private String value;

    public void initInstance(String lexem) throws InvalidLeafSyntaxException {
        if (null == lexem)
            throw new InvalidLeafSyntaxException("A null value is not allowed");
      
        value = lexem;
        for(int i = 0 ; i < lexem.length() ; i++) {
            if (lexem.charAt(i) == EvaluatedFunction.EVALUATED_FUNCTION_CHAR || lexem.charAt(i) == UnEvaluatedFunction.UNEVALUATED_FUNCTION_CHAR ) {
                if (Function.isAValidFunction(lexem.substring(i))){
                    value = lexem.substring(0,i);
                    return ;
                }
            } else if (lexem.charAt(i) == Variable.VARIABLE_CHAR  &&  (Variable.isAValidVariable(lexem.substring(i)))){
                value = lexem.substring(0,i);
                return ;
            }
        }
    }

    @Override
    public String apply() {
        return value;
    }

    @Override
    public String getNextLexem(final String lexem) {
        return lexem.substring(value.length(),lexem.length());
    }
}
