package io.daniel.flow.portal.domain.refference.definition.node;

import io.daniel.flow.portal.domain.enums.NodeType;

/**
 * 开始节点的定义
 *
 * @author neason-cn
 * @date 2022/2/1
 */
public class StartNodeDefinition extends AbstractNodeDefinition {

    public NodeType getType() {
        return NodeType.START;
    }

}
