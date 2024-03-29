package io.daniel.flow.portal.domain.refference.definition.node;

import io.daniel.flow.portal.domain.enums.JoinMode;
import io.daniel.flow.portal.domain.enums.NodeType;
import io.daniel.flow.portal.domain.refference.definition.task.TaskDefinition;
import lombok.Data;

/**
 * 任务节点的定义
 *
 * @author neason-cn
 * @date 2022/2/1
 */
@Data
public class TaskNodeDefinition extends AbstractNodeDefinition {

    private JoinMode joinMode;
    private TaskDefinition taskDefinition;

    public NodeType getType() {
        return NodeType.TASK;
    }

}
