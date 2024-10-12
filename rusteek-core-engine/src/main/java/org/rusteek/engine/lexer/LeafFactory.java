package org.rusteek.engine.lexer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public final class LeafFactory {

    private static final ApplicationContext languageContainer ;

    private LeafFactory() {
    }
    static {
        languageContainer = new AnnotationConfigApplicationContext("org.rusteek.engine.lexer");
    }
    public static Leaf createInstance(final char specialCharacter){ 

        String leafName = null;

        switch (specialCharacter){
            case ('@'):
                leafName = "evaluatedFunction";
                break;
            case ('#'):
                leafName = "unEvaluatedFunction";
                break;
            case ('$'):
                 leafName = "variable";
                 break;
            default :
                leafName = "literal";
                break;
        }
        return (Leaf)(languageContainer.getBean(leafName));    
    }
}
