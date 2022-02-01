package io.daniel.flow.portal.domain.refference.instance.node;

import io.daniel.flow.portal.domain.enums.FlowInstanceState;
import io.daniel.flow.portal.domain.enums.NodeType;
import io.daniel.flow.portal.domain.refference.context.Execution;
import io.daniel.flow.portal.domain.refference.definition.node.EndNodeDefinition;
import io.daniel.flow.portal.domain.refference.instance.edge.EdgeInstance;

import java.util.Collections;
import java.util.Set;

/**
 * @author neason-cn
 * @date 2022/2/1
 */
public class EndNodeInstance extends AbstractNodeInstance<EndNodeDefinition> {

    private EndNodeDefinition defition;

    @Override
    public EndNodeDefinition getDefinition() {
        return defition;
    }

    @Override
    public void execute(Execution execution) {
        execution.getFlowInstance().setState(FlowInstanceState.FINISH);
    }

    @Override
    public NodeType getType() {
        return NodeType.END;
    }

    @Override
    public Set<EdgeInstance> outgoing() {
        return Collections.emptySet();
    }
}
