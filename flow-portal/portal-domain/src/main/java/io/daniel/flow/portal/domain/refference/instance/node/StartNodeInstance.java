package io.daniel.flow.portal.domain.refference.instance.node;

import io.daniel.flow.portal.domain.enums.NodeInstanceState;
import io.daniel.flow.portal.domain.enums.NodeType;
import io.daniel.flow.portal.domain.refference.context.Execution;
import io.daniel.flow.portal.domain.refference.definition.node.StartNodeDefinition;
import io.daniel.flow.portal.domain.refference.instance.edge.EdgeInstance;
import lombok.Data;

import java.util.Collections;
import java.util.Set;

/**
 * @author neason-cn
 * @date 2022/2/1
 */
@Data
public class StartNodeInstance extends AbstractNodeInstance<StartNodeDefinition> {

    private StartNodeDefinition definition;

    @Override
    public StartNodeDefinition getDefinition() {
        return definition;
    }

    @Override
    public void execute(Execution execution) {
        this.setState(NodeInstanceState.FINISH);
        createEdgesAndAutoExecute(execution);
    }

    @Override
    public NodeType getType() {
        return NodeType.START;
    }

    @Override
    public Set<EdgeInstance> incoming() {
        return Collections.emptySet();
    }

    @Override
    public void onCancel(Execution execution) {
        throw new UnsupportedOperationException();
    }

}
