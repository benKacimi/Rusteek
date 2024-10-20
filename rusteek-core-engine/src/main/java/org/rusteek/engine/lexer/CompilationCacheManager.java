package org.rusteek.engine.lexer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class CompilationCacheManager {
    private static Map<String,RootNode> preCompilationCache = new ConcurrentHashMap<>();
    private static Map<String,RootNode> compilationCache = new ConcurrentHashMap<>();

    private CompilationCacheManager(){
    }

    public static void setEntryInCache(final String lexem, final RootNode aNode, final boolean preCompilation){
        if (preCompilation)
            preCompilationCache.putIfAbsent(lexem, aNode);
        else
            compilationCache.putIfAbsent(lexem, aNode);
    }

    public static RootNode getEntryInCache(final String lexem, final boolean preCompilation){
        if (preCompilation)
            return preCompilationCache.get(lexem);
        else
            return compilationCache.get(lexem);
   }
}
