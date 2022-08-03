package org.accelerate.tdm.smartdata.rules.engine.lexer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Lexer {

    private static Map<String,Node> compilationCache = new ConcurrentHashMap<>();
  
    /**
     * @param lexem
     * @return
     */
    public Node lex(final String lexem){
        String localLexem = lexem;

        if (localLexem == null)
            localLexem = "";
        Node root = compilationCache.get(localLexem);
        if (root == null){
            root = new Node();
            lex (localLexem,root);
            compilationCache.put(localLexem, root);
        }
        return root;
    }

    protected void lex(final String lexem, final Node parent){
        String current = "";
        
        if (lexem != null)
            current = lexem;
        if (!"".equals(current)) {
            switch (current.charAt(0)){
                case ('@'):
                    current = createEvalutedFunctionNode(parent,current);
                    break;
                case ('#'):
                    current = createNotEvalutedFunctionNode(parent,current);
                    break;
                case ('$'):
                    current = createVariableNode(parent,current);
                    break;
                default:
                    current = createLiteralNode(parent,current);
                    break;
            }
            lex(current,parent);
        } 
    }

    private String createNotEvalutedFunctionNode(final Node parent, final String lexem){
        return createFunctionNode(parent,lexem, false);
    }

    private String createEvalutedFunctionNode(final Node parent, final String lexem){
        return createFunctionNode(parent,lexem, true);
    }

    private String createFunctionNode(final Node parent, final String lexem, final boolean isEvaluated){
        Function functionNode = Function.createInstance(lexem);
        
        if (functionNode != null){
            functionNode.setEvaluated(isEvaluated);
            parent.addChild(functionNode);
            int annotationClassNameLenght = 0;
            if (functionNode.getFunctionAnnotationName() != null &&
                !"".equals(functionNode.getFunctionAnnotationName()))
                annotationClassNameLenght = functionNode.getFunctionAnnotationName().length()+1;
            return lexem.substring(annotationClassNameLenght+functionNode.getFunctionName().length()+2+functionNode.getParameter().length()+1,lexem.length());
        }
        return createLiteralNode(parent, lexem);
    }

    private String createLiteralNode(final Node parent, final String lexem){
        Literal literal = Literal.createInstance(lexem);
        
        if (literal != null){
            parent.addChild(literal);
            return lexem.substring(literal.getValue().length(),lexem.length());
        }
        return null;
    }

    private String createVariableNode(final Node parent, final String lexem){
        Variable variable = Variable.createInstance(lexem);
        
        if (variable != null){
            parent.addChild(variable);
            String result = lexem.substring(variable.getKeyName().length()+3,lexem.length());
            variable.setKeyName(variable.getKeyName().trim());
            return result;
        }
        return createLiteralNode(parent,lexem);
    }
}
