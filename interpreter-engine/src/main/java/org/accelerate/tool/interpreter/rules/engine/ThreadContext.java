package org.accelerate.tool.interpreter.rules.engine;

import java.util.HashMap;
import java.util.Map;

public final class ThreadContext {

    private ThreadContext() {
    }
    
    private static ThreadLocal<Map<String,String>> threadDataProperties = ThreadLocal.withInitial(HashMap::new);

    
    public static void setThreadDataMap(final Map<String, String> properties){
        threadDataProperties.set(properties);
    }

    public static String getVariableValue(final String key){
         return threadDataProperties.get().get(key);
    }
    
    public static void remove(){
        threadDataProperties.get().clear();
        threadDataProperties.remove();
    }
}
