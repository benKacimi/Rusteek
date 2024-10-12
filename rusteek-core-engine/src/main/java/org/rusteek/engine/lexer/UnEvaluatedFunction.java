package org.rusteek.engine.lexer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Component("unEvaluatedFunction")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public final class UnEvaluatedFunction extends Function {

    public static final char UNEVALUATED_FUNCTION_CHAR = '#';

    protected static final Logger LOGGER = LoggerFactory.getLogger(UnEvaluatedFunction.class);

    @Override    
    public String apply() {
        return getNonEvaluateFunction();
    }

    protected String getNonEvaluateFunction() {
        return EvaluatedFunction.EVALUATED_FUNCTION_CHAR+buildFunctionString();
    }
}
