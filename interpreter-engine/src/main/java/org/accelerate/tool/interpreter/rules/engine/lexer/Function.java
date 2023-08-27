package org.accelerate.tool.interpreter.rules.engine.lexer;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.accelerate.tool.interpreter.rules.Rule;
import org.apache.commons.configuration2.Configuration; 
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class Function extends AbstractFunction {
    protected static final Logger LOGGER = LoggerFactory.getLogger(Function.class);
    private static final ApplicationContext ruleContainer ;
    static {
        try {
            ClassLoader classLoader = Function.class.getClassLoader();
            Enumeration<URL> pluginFunctionPropertiesFile = classLoader.getResources("function.properties");
            List<URL> ret = new ArrayList<>();
            while (pluginFunctionPropertiesFile.hasMoreElements()) {
                ret.add(pluginFunctionPropertiesFile.nextElement());
            }
            if (ret.isEmpty()) {
                ruleContainer = new AnnotationConfigApplicationContext("org.accelerate.tool.interpreter.rules");
            } else {
                List<String> propertieList = new ArrayList<>();
                ret.forEach(url -> {
                    Configurations configs = new Configurations();
                    try {
                        Configuration config = configs.properties(url.getFile());
                        propertieList.addAll(config.getList(String.class,"base.packages"));
                        
                    } catch (ConfigurationException e) {
                        throw new IllegalStateException(e);
                    }
                });
                ruleContainer = new AnnotationConfigApplicationContext(propertieList.toArray(new String[0]));
            }
        } catch (IOException  e) {
            throw new RuntimeException(e);
        }     
    }
    private String  functionName;
    private boolean isEvaluated;
    //private String  functionClass;
    private String  functionAnnotationName;
    private List<Argument> arguments;
    private String parameter;
    private Rule rule;

    protected static Function createInstance(final String lexem){
        if (isAValideFunction(lexem) ){
            String functionAnnotationName = calculateAnnotationFunctionName(lexem);
            
            String functionName = "";
            if ("".equals(functionAnnotationName))
                functionName = calculateFunctionName(lexem);
            else
                functionName = calculateFunctionName(lexem.substring(1+functionAnnotationName.length()));
            String functionParameter = Argument.calculateFunctionParameter(lexem.substring(lexem.indexOf("(")));
            Function functionNode = new Function();
            functionNode.setParameter(functionParameter);
            functionNode.setFunctionName(functionName);
            functionNode.setFunctionAnnotationName(functionAnnotationName);
            if (functionParameter != null)
                functionNode.setArguments(Argument.createArgumentList(functionParameter.trim()));
            try {
                //Object function =  null; 
                if ("".equals(functionAnnotationName))
                    functionNode.rule = (Rule)ruleContainer.getBean(functionName.trim());
                else
                    functionNode.rule = (Rule)ruleContainer.getBean(functionAnnotationName.trim());
               // functionNode.setFunctionClass(function.getClass().getName());
            }catch (NoSuchBeanDefinitionException e){
                LOGGER.warn("No bean found for function {}",functionName);
            }
            return functionNode;
        }
        return null;
    }

    protected static boolean isAValideFunction(final String lexem){
        if (lexem == null)
            return false;

        int openingParenthesisIndex = lexem.indexOf("(");
        int closingParenthesisIndex = lexem.indexOf(")");

        if (openingParenthesisIndex != -1 && 
            closingParenthesisIndex > openingParenthesisIndex ){
            String functionAnnotationName = calculateAnnotationFunctionName(lexem);
            String functionName = "";
            if ("".equals(functionAnnotationName))
                functionName = calculateFunctionName(lexem);
            else
                functionName = calculateFunctionName(lexem.substring(1+functionAnnotationName.length()));
            String functionParameter = Argument.calculateFunctionParameter(lexem.substring(openingParenthesisIndex));
            
            if (functionParameter != null && functionName != null )
                return true;
            }
            return false;
    }

    private Method seekMethod(){
        if (rule == null)
            return null;

        try {
            //rule = (Rule)Class.forName (functionClass).getConstructor().newInstance();
            Method[] methods = rule.getClass().getMethods();
            for(Method aMethod : methods) {
                if (aMethod.isAnnotationPresent(org.accelerate.tool.interpreter.rules.Function.class)){
                    Annotation[] arrayAnnotations = aMethod.getAnnotations();
                    for (Annotation annotation : arrayAnnotations) {
                        org.accelerate.tool.interpreter.rules.Function functionAnnotation  = (org.accelerate.tool.interpreter.rules.Function)annotation;
                        if (functionName.equals(functionAnnotation.name()) || functionName.equals(aMethod.getName()))
                            return aMethod;
                    }
                }  
            }
        } catch ( IllegalArgumentException | 
                  SecurityException  e) {
                    LOGGER.error(e.getMessage());
        }                                                                                                                                                                  
        return null;
    }

    @Override    
    public String apply() {
        Method aMethod = seekMethod();
        if (aMethod == null)
            return error();
        Object[] argumentArray = evalArguments(false);
        if (isEvaluated){
            try { 
                return  (String)aMethod.invoke(rule,argumentArray);
            }
            catch (  IllegalArgumentException | IllegalAccessException | InvocationTargetException | ClassCastException  e) {
                return error();
            }
        } else {            
            return getNonEvaluateFunction();
        }
    }
    private String[] evalArguments(boolean errorFormat){
        if (arguments == null)
            return null;
        String[] argumentArray = new String[arguments.size()];
        for (int i = 0; i< arguments.size(); i++){
            Argument arg = arguments.get(i);
            if (errorFormat){
                if (!"".equals(arg.getName()))
                    argumentArray[i] = new StringBuilder(arg.getName()).append("=").append( arg.apply()).toString();
                else
                    argumentArray[i] =  arg.apply();  
            }
            else
                argumentArray[i] = arg.apply();
        }
        return argumentArray;
    }

    private String getNonEvaluateFunction(){
        StringBuilder result = new StringBuilder("@");
        if (!"".equals(functionAnnotationName)){
            result.append(functionAnnotationName);
            result.append(".");
        }
        result.append(functionName);
        result.append("(");
        String[] argumentArray = evalArguments(true);
        if (argumentArray == null)
            result.append(parameter);
        else
            result.append(String.join(",",argumentArray));
        result.append(")");
        return result.toString();
    }

    private String error(){
       return getNonEvaluateFunction();
    }
}
