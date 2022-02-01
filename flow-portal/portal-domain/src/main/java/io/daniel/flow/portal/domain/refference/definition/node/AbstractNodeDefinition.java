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
public abstract class AbstractNodeDefinition implements Definition, Node<EdgeDefinition> {

    private String code;
    private String name;
    private Set<EdgeDefinition> incoming;
    private Set<EdgeDefinition> outgoing;

    public Set<EdgeDefinition> incoming() {
        return incoming;
    }

    public Set<EdgeDefinition> outgoing() {
        return outgoing;
    }

    public String getDefinitionCode() {
        return code;
    }

    public String getDefinitionName() {
        return name;
    }
}
