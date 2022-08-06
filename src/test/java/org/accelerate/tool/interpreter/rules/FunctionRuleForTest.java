package org.accelerate.tool.interpreter.rules;

import org.accelerate.tool.interpreter.rules.Function;
import org.accelerate.tool.interpreter.rules.IRule;
import org.springframework.stereotype.Component;

@Component("function")
public class FunctionRuleForTest implements IRule{

    @Function (name="function")
    public String execute(){
        return "foo";
    }
    
    @Function (name="functionWithWrongReturnType")
    public boolean executeWithWrongReturnType(){
        return false;
    }

}
