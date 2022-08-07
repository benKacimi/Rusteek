package org.accelerate.tool.interpreter.rules.engine;

import java.util.HashMap;
import java.util.Map;

public class ThreadContext {

    private ThreadContext() {
    }
    private static ThreadLocal<Map<String,String>> threadDataProperties = new ThreadLocal<Map<String, String>>(){
        @Override public Map<String,String> initialValue() {
            return new HashMap<>();
        }
    };
    
    public static void setThreadDataMap(final Map<String, String> properties){
        threadDataProperties.set(properties);
    }

    public static String getVariableValue(final String key){
        if (threadDataProperties.get() == null)
            throw new IllegalStateException("ThreadContext is not initialized or has been remove");
         return threadDataProperties.get().get(key);
    }
    
    public static void remove(){
        threadDataProperties.get().clear();
        threadDataProperties.remove();
    }
}
