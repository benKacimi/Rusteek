package org.accelerate.tool.interpreter.rules.engine.lexer;


import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.accelerate.tool.interpreter.rules.IRule;
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
    private static final ApplicationContext ruleContainer =  new AnnotationConfigApplicationContext("org.accelerate.tool.interpreter.rules");
    private String  functionName;
    private boolean isEvaluated;
    private String  functionClass;
    private String  functionAnnotationName;
    private List<Argument> arguments;
    private String parameter;
    private IRule rule;

    protected static Function createInstance(final String lexem){

        if (isAValideFunction(lexem) ){
            String functionAnnotationName = calculateAnnotationFunctionName(lexem);
            
            String functionName = "";
            if (functionAnnotationName == null || "".equals(functionAnnotationName))
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
                Object function =  null; 
                if (functionAnnotationName == null || "".equals(functionAnnotationName))
                    function = ruleContainer.getBean(functionName.trim());
                else
                    function = ruleContainer.getBean(functionAnnotationName.trim());
                functionNode.setFunctionClass(function.getClass().getName());
            }catch (NoSuchBeanDefinitionException e){
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
            if (functionAnnotationName == null || "".equals(functionAnnotationName))
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
        if (functionClass == null || "".equals(functionClass))
            return null;

        try {
            rule = (IRule)Class.forName (functionClass).getConstructor().newInstance();
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
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }                                                                                                                                                                  
        return null;
    }

    @Override    
    public String apply() {
        Method aMethod = seekMethod();
        if (aMethod == null)
            return error();
        Object[] argumentArray = null;
        try {
            if (arguments != null){
                argumentArray = new String[arguments.size()];
                for (int i = 0; i< arguments.size(); i++){
                    Argument arg = arguments.get(i);
                    argumentArray[i] = arg.apply();
                }
            } 
            return  (String)aMethod.invoke(rule,argumentArray);
        }
        catch (  IllegalArgumentException | IllegalAccessException | InvocationTargetException | ClassCastException  e) {
            return error();
        }
    }

    private String error(){
        StringBuilder result = new StringBuilder("@");
        if (functionAnnotationName != null && !"".equals(functionAnnotationName)){
            result.append(functionAnnotationName);
            result.append(".");
        }
        result.append(functionName);
        result.append("(");
        result.append(parameter);
        result.append(")");
        return result.toString();
    }
}
