package org.rusteek.engine.lexer;

import java.util.List;
import java.util.regex.Pattern;

import org.rusteek.engine.lexer.execption.InvalidFunctionSyntaxException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PROTECTED)
@Getter
@Setter
public abstract class Function  implements Leaf {

    private static final Pattern FUNCTION_NAME_REGEX = Pattern.compile("^[a-zA-Z0-9]+$");
    private List<Argument> arguments;
    private String functionName;
    private String functionAnnotationName;
    private String parameter;

    protected Function(){}

    public  void initInstance(final String lexem) throws InvalidFunctionSyntaxException {
        if (!checkAndSetFunction(lexem)) {
            throw new InvalidFunctionSyntaxException(lexem) ;       
        }
        arguments = Argument.createArgumentList(parameter.trim());
    }
    
    protected static boolean isAValidFunction(final String lexem) {
        return checkFunction(lexem);
    }
    
    protected static boolean checkFunction(final String lexem) {
        if (lexem == null) {
            return false;
        }
        int openingParenthesisIndex = lexem.indexOf("(");
        int closingParenthesisIndex = lexem.indexOf(")");

        if (openingParenthesisIndex != -1 && closingParenthesisIndex > openingParenthesisIndex) {
            String functionAnnotationName = calculateAnnotationFunctionName(lexem);
            String functionName = "".equals(functionAnnotationName) ? 
                                calculateFunctionName(lexem) : 
                                calculateFunctionName(lexem.substring(1 + functionAnnotationName.length()));
            return !functionName.isEmpty();
        }
        return false;
    }

    protected boolean checkAndSetFunction(final String lexem) {
        if (lexem == null) {
            return false;
        }

        int openingParenthesisIndex = lexem.indexOf("(");
        int closingParenthesisIndex = lexem.indexOf(")");

        if (openingParenthesisIndex != -1 && closingParenthesisIndex > openingParenthesisIndex) {
            functionAnnotationName = calculateAnnotationFunctionName(lexem);
            functionName = "".equals(functionAnnotationName) ? 
                                calculateFunctionName(lexem) : 
                                calculateFunctionName(lexem.substring(1 + functionAnnotationName.length()));
            parameter = Argument.calculateFunctionParameter(lexem.substring(openingParenthesisIndex));
           
            return !functionName.isEmpty();
        }
        return false;
    }

    protected static String calculateAnnotationFunctionName(final String phrase) {
        int pointIndex = phrase.indexOf(".");
        String calculatedClassName = "";
        if (pointIndex != -1){
            calculatedClassName = phrase.substring(1, pointIndex);
            if (checkFunctionNameSyntax(calculatedClassName))
                return calculatedClassName;
        }
        return "";
    }

    protected static String calculateFunctionName(final String phrase){
        if (phrase == null || phrase.isEmpty()) {
            return "";
        }
  
        int openingParenthesisIndex = phrase.indexOf("(");
      
        String calculatedName = phrase.substring(1, openingParenthesisIndex).trim();
        if (!calculatedName.isEmpty() && checkFunctionNameSyntax(calculatedName)) {
            return calculatedName;
        }
        return "";
    }

    protected static boolean checkFunctionNameSyntax(final String functionName){
        if (functionName == null || "".equals(functionName))
            return false;
        return  (FUNCTION_NAME_REGEX.matcher(functionName)).matches() ;
    }

    protected String[] evalArguments(final boolean errorFormat) {

        String[] argumentArray = new String[arguments.size()];
        
        for (int i = 0; i < arguments.size(); i++) {
            Argument arg = arguments.get(i);
            StringBuilder argumentBuilder = new StringBuilder();
            
            if (errorFormat && !arg.getName().isEmpty()) {
                argumentBuilder.append(arg.getName()).append("=");
            }
            
            argumentBuilder.append(arg.apply().trim());
            argumentArray[i] = argumentBuilder.toString();
        }
        return argumentArray;
    }

    protected String getNonEvaluateFunction() {
        StringBuilder result = new StringBuilder(String.valueOf(EvaluatedFunction.EVALUATED_FUNCTION_CHAR));
        if (!getFunctionAnnotationName().isEmpty()) {
            result.append(getFunctionAnnotationName()).append(".");
        }
        result.append(getFunctionName()).append("(");
        String[] argumentArray = evalArguments(true);
        if (argumentArray.length == 0) {
            result.append(getParameter().trim());
        } else {
            result.append(String.join(",", argumentArray));
        }
        result.append(")");
        return result.toString();
    }
    
    public String getNextLexem(final String lexem){
        return lexem.substring(Argument.getLastIndexOfBalanceBraquet(lexem)+1,lexem.length());
    }
}
