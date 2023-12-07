package org.accelerate.tool.interpreter.rules.engine.lexer;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.accelerate.tool.interpreter.rules.Rule;
import org.accelerate.tool.interpreter.rules.engine.lexer.execption.InvalidFunctionSyntaxException;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class EvaluatedFunction extends Function{
    
    public static final char EVALUATED_FUNCTION_CHAR = '@';
    protected static final Logger LOGGER = LoggerFactory.getLogger(EvaluatedFunction.class);
   
    private Method method;
    private Rule rule;

    private static final ApplicationContext ruleContainer ;
    static {
        try {
            ClassLoader classLoader = UnEvaluatedFunction.class.getClassLoader();
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
            throw new IllegalStateException(e);
        }     
    }

    @Override
    public  void initInstance(final String lexem) throws InvalidFunctionSyntaxException {
        super.initInstance(lexem);
        try {
            String beanName = ("".equals(getFunctionAnnotationName())) ? getFunctionName().trim() : getFunctionAnnotationName().trim();
            setRule((Rule)ruleContainer.getBean(beanName));
            setMethod(seekMethod());
        } catch (NoSuchBeanDefinitionException e) {
            LOGGER.warn("No bean found for function {}", getFunctionName());
        }
    }

    private Method seekMethod() {
        if (rule == null) {
            return null;
        }

        Method[] methods = rule.getClass().getMethods();
        for (Method ruleMethod : methods) {
            if (ruleMethod.isAnnotationPresent(org.accelerate.tool.interpreter.rules.Function.class)) {
                Annotation[] annotations = ruleMethod.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof org.accelerate.tool.interpreter.rules.Function) {
                        org.accelerate.tool.interpreter.rules.Function functionAnnotation = (org.accelerate.tool.interpreter.rules.Function) annotation;
                        if (getFunctionName().equals(functionAnnotation.name()) || getFunctionName().equals(ruleMethod.getName())) {
                            return ruleMethod;
                        }
                    }
                }
            }
        }
        
        return null;
    }

    @Override
    public String apply() {
        if (method == null) {
            return error();
        }
    
        Object[] arguments = evalArguments(false);
        try {
            return (String) method.invoke(rule, arguments);
        } catch (Exception e) {
            return error();
        }
    }

    private String error(){
        return getNonEvaluateFunction();
    }
}
