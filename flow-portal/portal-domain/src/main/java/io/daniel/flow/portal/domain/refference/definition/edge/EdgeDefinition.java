package io.daniel.flow.portal.domain.refference.definition.edge;

import io.daniel.flow.portal.domain.refference.context.Expression;
import io.daniel.flow.portal.domain.refference.definition.Definition;
import io.daniel.flow.portal.domain.refference.definition.node.AbstractNodeDefinition;
import io.daniel.flow.portal.domain.support.graph.Edge;
import lombok.Data;

import java.util.Collections;
import java.util.Set;

/**
 * 流程拓扑图中"边"的定义
 *
 * @author neason-cn
 * @date 2022/2/1
 */
@Data
public class EdgeDefinition implements Definition, Edge<AbstractNodeDefinition> {

    private String code;
    private String name;

    /**
     * 边的优先级
     */
    private Integer priority;

    /**
     * 推进表达式，该表达式成立才会往下推进
     */
    private Expression condition;

    /**
     * 边的入口
     */
    private AbstractNodeDefinition source;

    /**
     * 边的出口
     */
    private AbstractNodeDefinition target;

    public String getDefinitionCode() {
        return code;
    }

    public String getDefinitionName() {
        return name;
    }

    public Set<AbstractNodeDefinition> incoming() {
        return Collections.singleton(source);
    }

    public Set<AbstractNodeDefinition> outgoing() {
        return Collections.singleton(target);
    }

}
