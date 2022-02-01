package io.daniel.flow.portal.domain.refference.definition.node;

import io.daniel.flow.portal.domain.enums.NodeType;

/**
 * 任务节点的定义
 *
 * @author neason-cn
 * @date 2022/2/1
 */
public class TaskNodeDefinition extends AbstractNodeDefinition {

    public NodeType getType() {
        return NodeType.TASK;
    }

}
