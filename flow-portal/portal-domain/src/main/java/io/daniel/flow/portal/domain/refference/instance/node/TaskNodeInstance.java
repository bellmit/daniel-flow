package io.daniel.flow.portal.domain.refference.instance.node;

import io.daniel.flow.portal.domain.enums.NodeType;
import io.daniel.flow.portal.domain.refference.context.Execution;
import io.daniel.flow.portal.domain.refference.definition.node.TaskNodeDefinition;
import lombok.Data;

/**
 * @author neason-cn
 * @date 2022/2/1
 */
@Data
public class TaskNodeInstance extends AbstractNodeInstance<TaskNodeDefinition> {

    private TaskNodeDefinition definition;

    @Override
    public TaskNodeDefinition getDefinition() {
        return definition;
    }

    @Override
    public void execute(Execution execution) {

    }

    @Override
    public NodeType getType() {
        return NodeType.TASK;
    }

}
