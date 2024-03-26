package org.accelerate.tool.interpreter.rules.engine.lexer;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RootNode implements Node {
    protected List<Node> children;
    
    public void addChild(final Node child){
        if (children == null)
            children = new ArrayList<>();
        children.add(child);
    }

    @Override
    public String apply() {
        StringBuilder result = new StringBuilder();
        if (children != null)
            children.forEach(aNode -> result.append(aNode.apply().trim()));
        return result.toString();
    }
}
