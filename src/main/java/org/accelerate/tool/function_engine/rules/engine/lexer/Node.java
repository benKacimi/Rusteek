package org.accelerate.tdm.smartdata.rules.engine.lexer;

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
public class Node implements INode {
    protected List<INode> children;
    
    public void addChild(final INode child){
        if (children == null)
            children = new ArrayList<>();
        children.add(child);
    }

    @Override
    public String apply() {
        StringBuilder result = new StringBuilder();
        if (children != null)
            children.forEach(aNode -> result.append(aNode.apply()));
        return result.toString();
    }
}
