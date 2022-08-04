package org.accelerate.tool.function_engine.rules.engine.lexer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class Literal implements INode {
    private String value;

    protected static Literal createInstance(String lexem){
        if (null == lexem)
            return null;
        Literal literalNode = new Literal();
        literalNode.setValue(lexem);
        for(int i = 0 ; i < lexem.length() ; i++) {
            if (lexem.charAt(i) == '@' || lexem.charAt(i) == '#' ) {
                if (Function.isAValideFunction(lexem.substring(i))){
                    literalNode.setValue(lexem.substring(0,i));
                    return literalNode;
                }
            } else if (lexem.charAt(i) == '$'){
                if (Variable.createInstance(lexem.substring(i)) != null){
                    literalNode.setValue(lexem.substring(0,i));
                    return literalNode;
                }
            }
        }
        return literalNode;
    }

    @Override
    public String apply() {
        return value;
    }
}
