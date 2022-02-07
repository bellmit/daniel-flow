package io.daniel.flow.portal.domain.refference.instance.node;

import io.daniel.flow.portal.domain.aggregate.FlowInstance;
import io.daniel.flow.portal.domain.enums.NodeInstanceState;
import io.daniel.flow.portal.domain.refference.context.Execution;
import io.daniel.flow.portal.domain.refference.definition.node.AbstractNodeDefinition;
import io.daniel.flow.portal.domain.refference.instance.Instance;
import io.daniel.flow.portal.domain.refference.instance.edge.EdgeInstance;
import io.daniel.flow.portal.domain.support.Action;
import io.daniel.flow.portal.domain.support.graph.Node;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author neason-cn
 * @date 2022/2/1
 */
@Data
public abstract class AbstractNodeInstance<T extends AbstractNodeDefinition> implements Instance<T>, Node<EdgeInstance>, Action {

    protected NodeInstanceState state;
    protected Set<EdgeInstance> incoming;
    protected Set<EdgeInstance> outgoing;

    @Override
    public Set<EdgeInstance> incoming() {
        return incoming;
    }

    @Override
    public Set<EdgeInstance> outgoing() {
        return outgoing;
    }

    /**
     * 创建节点后面的边，并自动执行边的execute
     *
     * @param execution
     */
    protected void createEdgesAndAutoExecute(Execution execution) {
        createEdges(execution).forEach(edgeInstance -> edgeInstance.execute(execution));
    }

    /**
     * 创建边
     */
    private Set<EdgeInstance> createEdges(Execution execution) {
        FlowInstance flowInstance = execution.getFlowInstance();
        Set<EdgeInstance> edges = new HashSet<>();
        this.getDefinition().getOutgoing().forEach(definition -> {
            EdgeInstance edge = flowInstance.createEdge(definition);
            edges.add(edge);
        });
        this.outgoing = edges;
        return edges;
    }

    /**
     * 跳过当前节点，重置状态，直接推进
     */
    public void onSkip(Execution execution) {
        this.setState(NodeInstanceState.SKIPPED);
        createEdgesAndAutoExecute(execution);
    }

    /**
     * 取消/中断一个节点
     */
    public abstract void onCancel(Execution execution);

}
