package io.daniel.flow.portal.domain.aggregate;

import io.daniel.flow.connector.domain.TaskExecuteResult;
import io.daniel.flow.portal.domain.enums.NodeType;
import io.daniel.flow.portal.domain.refference.context.Context;
import io.daniel.flow.portal.domain.refference.context.Execution;
import io.daniel.flow.portal.domain.refference.definition.edge.EdgeDefinition;
import io.daniel.flow.portal.domain.refference.definition.node.AbstractNodeDefinition;
import io.daniel.flow.portal.domain.refference.instance.Instance;
import io.daniel.flow.portal.domain.refference.instance.edge.EdgeInstance;
import io.daniel.flow.portal.domain.refference.instance.node.AbstractNodeInstance;
import io.daniel.flow.portal.domain.refference.instance.node.StartNodeInstance;
import lombok.Data;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author neason-cn
 * @date 2022/2/1
 */
@Data
public class FlowInstance implements Instance<FlowDefinition> {

    private String instanceId;
    private FlowDefinition definition;
    private Context context;
    private Set<AbstractNodeInstance<? extends AbstractNodeDefinition>> nodes;
    private Set<EdgeInstance> edgs;

    @Override
    public FlowDefinition getDefinition() {
        return definition;
    }

    private Execution initTransaction(TaskExecuteResult callback) {
        Execution execution = new Execution();
        execution.setCallback(callback);
        execution.setFlowInstance(this);
        execution.setTasks(new HashSet<>());
        return execution;
    }

    /**
     * 开始一个流程实例
     */
    public Execution start() {
        return findStart().execute(initTransaction(null));
    }

    public Execution start(StartNodeInstance startNode) {
        return startNode.execute(initTransaction(null));
    }

    /**
     * 推进一个流程节点
     */
    public Execution resume(String nodeCode) {
        return findNode(nodeCode).execute(initTransaction(null));
    }

    public Execution resume(String nodeCode, Map<String, String> addition) {
        context.merge(addition);
        return findNode(nodeCode).execute(initTransaction(null));
    }

    /**
     * 处理任务执行结果
     */
    public Execution handleCallback(TaskExecuteResult result) {
        return findNode(result.getNodeCode()).execute(initTransaction(result));
    }

    protected AbstractNodeInstance<? extends AbstractNodeDefinition> findStart() {
        for (AbstractNodeInstance<? extends AbstractNodeDefinition> node: nodes) {
            if (node.getType() == NodeType.START) {
                return node;
            }
        }
        throw new RuntimeException("该流程实例不符合要求，未创建Start节点");
    }

    protected AbstractNodeInstance<? extends AbstractNodeDefinition> findNode(String nodeCode) {
        for (AbstractNodeInstance<? extends AbstractNodeDefinition> node: nodes) {
            if (node.getDefinition().getDefinitionCode().equals(nodeCode)) {
                return node;
            }
        }
        throw new RuntimeException("该流程实例未找到相应的节点实例");
    }

    protected EdgeInstance createEdge(EdgeDefinition definition) {
        return null;
    }

    protected AbstractNodeInstance<? extends AbstractNodeDefinition> createNode(AbstractNodeDefinition definition) {
        return null;
    }

}
