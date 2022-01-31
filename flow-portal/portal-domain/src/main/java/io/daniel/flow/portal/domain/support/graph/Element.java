package io.daniel.flow.portal.domain.support.graph;

import java.util.Set;

/**
 * 通用Graph中的一个元素的定义，泛型是该元素的相邻元素的类型
 *
 * @author neason-cn
 * @date 2022/1/31
 */
public interface Element<E extends Element> {

    /**
     * 入口节点集合
     */
    Set<E> incoming();

    /**
     * 出口节点集合
     */
    Set<E> outgoing();

}
