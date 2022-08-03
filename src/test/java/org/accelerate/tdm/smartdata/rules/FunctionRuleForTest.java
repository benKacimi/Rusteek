package org.accelerate.tdm.smartdata.rules;

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
