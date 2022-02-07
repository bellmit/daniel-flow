package io.daniel.flow.portal.domain.aggregate;

import io.daniel.flow.connector.domain.TaskExecuteResult;
import io.daniel.flow.portal.domain.enums.FlowInstanceState;
import io.daniel.flow.portal.domain.enums.NodeInstanceState;
import io.daniel.flow.portal.domain.enums.NodeType;
import io.daniel.flow.portal.domain.refference.context.Context;
import io.daniel.flow.portal.domain.refference.context.Execution;
import io.daniel.flow.portal.domain.refference.definition.edge.EdgeDefinition;
import io.daniel.flow.portal.domain.refference.definition.node.AbstractNodeDefinition;
import io.daniel.flow.portal.domain.refference.instance.Instance;
import io.daniel.flow.portal.domain.refference.instance.edge.EdgeInstance;
import io.daniel.flow.portal.domain.refference.instance.node.AbstractNodeInstance;
import io.daniel.flow.portal.domain.refference.instance.node.StartNodeInstance;
import io.daniel.flow.portal.domain.refference.instance.node.TaskNodeInstance;
import io.daniel.flow.portal.domain.refference.instance.task.TaskInstance;
import lombok.Data;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author neason-cn
 * @date 2022/2/1
 */
@Data
public class FlowInstance implements Instance<FlowDefinition> {

    private String instanceId;
    private FlowDefinition definition;
    private FlowInstanceState state;
    private Context context;
    private Set<AbstractNodeInstance<? extends AbstractNodeDefinition>> nodes;
    private Set<EdgeInstance> edges;

    @Override
    public FlowDefinition getDefinition() {
        return definition;
    }

    /**
     * 每次对流程实例的一次事务操作，都初始化一个Execution
     */
    private Execution initTransaction(TaskExecuteResult callback) {
        Execution execution = new Execution();
        execution.setCallback(callback);
        execution.setFlowInstance(this);
        execution.setTasks(new HashSet<>());
        return execution;
    }

    public Execution start() {
        return start(null);
    }

    /**
     * 开始一个流程实例
     */
    public Execution start(Map<String, String> params) {
        return start(findStart(), params);
    }

    public Execution start(StartNodeInstance startNode, Map<String, String> params) {
        context.merge(params);
        Execution execution = initTransaction(null);
        startNode.execute(execution);
        return execution;
    }

    /**
     * 推进一个流程节点
     */
    public Execution resume(String nodeCode) {
        return resume(nodeCode, null);
    }

    public Execution resume(String nodeCode, Map<String, String> params) {
        context.merge(params);
        Execution execution = initTransaction(null);
        findNode(nodeCode).execute(execution);
        return execution;
    }

    /**
     * 处理任务执行结果
     */
    public Execution handleCallback(TaskExecuteResult result) {
        Execution execution = initTransaction(result);
        findNode(result.getNodeCode()).execute(execution);
        return execution;
    }

    /**
     * 跳过某一个节点
     */
    public Execution skip(String nodeCode) {
        Execution execution = initTransaction(null);
        findNode(nodeCode).onSkip(execution);
        return execution;
    }

    /**
     * 取消一个流程实例，返回当前正在运行的任务
     * TODO：这些任务是否需要向Executor发送CancelCommand
     */
    public Execution cancel() {
        Execution execution = initTransaction(null);
        execution.setTasks(findRunningTask());
        this.setState(FlowInstanceState.CANCELED);
        return execution;
    }


    protected StartNodeInstance findStart() {
        for (AbstractNodeInstance<? extends AbstractNodeDefinition> node : nodes) {
            if (node.getType() == NodeType.START) {
                return (StartNodeInstance) node;
            }
        }
        throw new RuntimeException("该流程实例不符合要求，未创建Start节点");
    }

    protected AbstractNodeInstance<? extends AbstractNodeDefinition> findNode(String nodeCode) {
        for (AbstractNodeInstance<? extends AbstractNodeDefinition> node : nodes) {
            if (node.getDefinition().getDefinitionCode().equals(nodeCode)) {
                return node;
            }
        }
        throw new RuntimeException("该流程实例未找到相应的节点实例");
    }

    public EdgeInstance createEdge(EdgeDefinition definition) {
        return null;
    }

    public AbstractNodeInstance<? extends AbstractNodeDefinition> createNode(AbstractNodeDefinition definition) {
        return null;
    }

    /**
     * 找到当前流程实例中正在运行的Task节点
     */
    private Set<TaskNodeInstance> findRunningNodes() {
        Set<TaskNodeInstance> runningNodes = new HashSet<>();
        nodes.forEach(node -> {
            // PENDING状态的node只需要修改一下状态
            if (node.getState() == NodeInstanceState.PENDING) {
                node.setState(NodeInstanceState.CANCELED);
            }
            //  RUNNING状态的node找出正在running的task
            if (node.getState() == NodeInstanceState.RUNNING && node instanceof TaskNodeInstance) {
                TaskNodeInstance taskNode = (TaskNodeInstance) node;
                node.setState(NodeInstanceState.CANCELED);
                runningNodes.add(taskNode);
            }
        });
        return runningNodes;
    }

    /**
     * 找到当前流程实例中正在运行的任务
     */
    private Set<TaskInstance> findRunningTask() {
        return findRunningNodes().stream()
                .map(TaskNodeInstance::getRunning)
                .collect(Collectors.toSet());
    }

}
