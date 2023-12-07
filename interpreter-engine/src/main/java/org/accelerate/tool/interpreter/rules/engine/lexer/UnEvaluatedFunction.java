package org.accelerate.tool.interpreter.rules.engine.lexer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter

public class UnEvaluatedFunction extends Function {

    public static final char UNEVALUATED_FUNCTION_CHAR = '#';

    protected static final Logger LOGGER = LoggerFactory.getLogger(UnEvaluatedFunction.class);

    @Override    
    public String apply() {
        return getNonEvaluateFunction();
    }

}
