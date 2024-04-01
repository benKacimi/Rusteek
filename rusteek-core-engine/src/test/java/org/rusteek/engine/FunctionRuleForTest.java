package org.rusteek.engine;

import org.rusteek.function.Function;
import org.rusteek.function.Rule;
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
    
     public String function(){
        return "foo";
    }
    

}
