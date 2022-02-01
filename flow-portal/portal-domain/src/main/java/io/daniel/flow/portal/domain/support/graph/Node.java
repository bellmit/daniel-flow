package io.daniel.flow.portal.domain.support.graph;

import io.daniel.flow.portal.domain.enums.NodeType;

/**
 * Edge的两头都是Node
 *
 * @author neason-cn
 * @date 2022/1/31
 */
public interface Node<E extends Edge> extends Element<E>{

    /**
     * Node的类型
     */
    NodeType getType();

}
