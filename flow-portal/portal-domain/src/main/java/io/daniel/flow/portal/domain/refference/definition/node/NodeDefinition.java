package io.daniel.flow.portal.domain.refference.definition.node;

import io.daniel.flow.portal.domain.refference.definition.Definition;
import io.daniel.flow.portal.domain.refference.definition.edge.EdgeDefinition;
import io.daniel.flow.portal.domain.support.graph.Node;
import lombok.Data;

import java.util.Set;

/**
 * @author neason-cn
 * @date 2022/2/1
 */
@Data
public class NodeDefinition implements Definition, Node<EdgeDefinition> {

    private String code;
    private String name;

    public Set<EdgeDefinition> incoming() {
        return null;
    }

    public Set<EdgeDefinition> outgoing() {
        return null;
    }

    public String getDefinitionCode() {
        return null;
    }

    public String getDefinitionName() {
        return null;
    }
}
