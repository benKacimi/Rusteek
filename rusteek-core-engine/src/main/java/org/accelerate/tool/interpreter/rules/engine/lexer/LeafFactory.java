package org.accelerate.tool.interpreter.rules.engine.lexer;

public final class LeafFactory {

    private LeafFactory() {
    }
    
    public static Leaf createInstance(final char specialCharacter){ 
        switch (specialCharacter){
            case (EvaluatedFunction.EVALUATED_FUNCTION_CHAR):
                return new EvaluatedFunction();
            case (UnEvaluatedFunction.UNEVALUATED_FUNCTION_CHAR):
                return new UnEvaluatedFunction();
            case (Variable.VARIABLE_CHAR):
                return new Variable();
            default:
                return new Literal();
        }
    }
}
