package io.daniel.flow.portal.domain.refference.instance.node;

import io.daniel.flow.portal.domain.enums.NodeInstanceState;
import io.daniel.flow.portal.domain.refference.definition.node.AbstractNodeDefinition;
import io.daniel.flow.portal.domain.refference.instance.Instance;
import io.daniel.flow.portal.domain.refference.instance.edge.EdgeInstance;
import io.daniel.flow.portal.domain.support.Action;
import io.daniel.flow.portal.domain.support.graph.Node;
import lombok.Data;

import java.util.Set;

/**
 * @author neason-cn
 * @date 2022/2/1
 */
@Data
public abstract class AbstractNodeInstance<T extends AbstractNodeDefinition> implements Instance<T>, Node<EdgeInstance>, Action {

    private NodeInstanceState state;
    private Set<EdgeInstance> incoming;
    private Set<EdgeInstance> outgoing;

    @Override
    public Set<EdgeInstance> incoming() {
        return incoming;
    }

    @Override
    public Set<EdgeInstance> outgoing() {
        return outgoing;
    }
}
