package org.accelerate.tool.interpreter.rules;

import org.springframework.stereotype.Component;

@Component("function")
public class FunctionRuleForTest implements Rule{

    @Function (name="function")
    public String execute(){
        return "foo";
    }
    
    @Function (name="functionWithWrongReturnType")
    public boolean executeWithWrongReturnType(){
        return false;
    }

    @Function (name="functionWithWrongReturnType")
    public String executeWithWrongFunctionName(){
        return "ERROR";
    }
    
     public String function(){
        return "foo";
    }
    

}
