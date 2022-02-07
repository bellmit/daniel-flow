package io.daniel.flow.portal.domain.aggregate;

import io.daniel.flow.connector.domain.TaskExecuteResult;
import io.daniel.flow.portal.domain.enums.EdgeInstanceState;
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
import io.daniel.flow.portal.domain.refference.instance.node.EndNodeInstance;
import io.daniel.flow.portal.domain.refference.instance.node.StartNodeInstance;
import io.daniel.flow.portal.domain.refference.instance.node.TaskNodeInstance;
import io.daniel.flow.portal.domain.refference.instance.task.TaskInstance;
import lombok.Data;

import java.util.ArrayList;
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
     */
    public Execution cancel() {
        Execution execution = initTransaction(null);
        this.setState(FlowInstanceState.CANCELED);
        findRunningNodes().forEach(node -> node.onCancel(execution));
        return execution;
    }

    /**
     * 找到Start节点
     */
    protected StartNodeInstance findStart() {
        for (AbstractNodeInstance<? extends AbstractNodeDefinition> node : nodes) {
            if (node.getType() == NodeType.START) {
                return (StartNodeInstance) node;
            }
        }
        throw new RuntimeException("该流程实例不符合要求，未创建Start节点");
    }

    /**
     * 根据node实例code找到instance
     */
    protected AbstractNodeInstance<? extends AbstractNodeDefinition> findNode(String nodeCode) {
        for (AbstractNodeInstance<? extends AbstractNodeDefinition> node : nodes) {
            if (node.getDefinition().getDefinitionCode().equals(nodeCode)) {
                return node;
            }
        }
        throw new RuntimeException("该流程实例未找到相应的节点实例");
    }

    /**
     * 创建edge的实例
     */
    public EdgeInstance createEdge(EdgeDefinition definition) {
        EdgeInstance edgeInstance = new EdgeInstance();
        edgeInstance.setDefinition(definition);
        edgeInstance.setState(EdgeInstanceState.INIT);
        edgeInstance.setSource(findNodeInstanceByDefinition(definition.getSource()));
        // 创建边的时候source还没有被实例化
        edgeInstance.setTarget(null);
        edges.add(edgeInstance);
        return edgeInstance;
    }

    /**
     * 根据node的definition找到对应的instance
     */
    private AbstractNodeInstance<? extends AbstractNodeDefinition> findNodeInstanceByDefinition(AbstractNodeDefinition definition) {
        return nodes.stream()
                .filter(node -> node.getDefinition().getDefinitionCode().equals(definition.getDefinitionCode()))
                .findFirst()
                .orElse(null);
    }

    /**
     * 创建node的实例
     */
    public AbstractNodeInstance<? extends AbstractNodeDefinition> createNode(AbstractNodeDefinition definition) {
        AbstractNodeInstance<? extends AbstractNodeDefinition> nodeInstance = null;
        switch (definition.getType()) {
            case START:
                nodeInstance = new StartNodeInstance();
                break;
            case END:
                nodeInstance = new EndNodeInstance();
                break;
            case TASK:
            default:
                TaskNodeInstance taskNodeInstance = new TaskNodeInstance();
                taskNodeInstance.setDone(new ArrayList<>());
                taskNodeInstance.setLeft(new ArrayList<>());
                nodeInstance = taskNodeInstance;
        }
        nodeInstance.setState(NodeInstanceState.INIT);

        Set<EdgeInstance> incoming = nodeInstance.getDefinition().getIncoming().stream()
                .map(this::findInstanceByEdgeDefinition)
                .collect(Collectors.toSet());
        nodeInstance.setIncoming(incoming);
        // 创建nodeInstance时outgoing的edge还没有实例化
        nodeInstance.setOutgoing(null);
        nodes.add(nodeInstance);
        return nodeInstance;
    }

    /**
     * 用edge的definition找到对应的instance
     */
    private EdgeInstance findInstanceByEdgeDefinition(EdgeDefinition definition) {
        return edges.stream()
                .filter(edge -> edge.getDefinition().getDefinitionCode().equals(definition.getDefinitionCode()))
                .findFirst()
                .orElse(null);
    }

    /**
     * 找到当前流程实例中正在运行的Task节点
     */
    private Set<TaskNodeInstance> findRunningNodes() {
        return nodes.stream()
                .filter(node -> node.getType() == NodeType.TASK)
                .map(node -> (TaskNodeInstance)node)
                .filter(node -> node.getState() == NodeInstanceState.RUNNING)
                .collect(Collectors.toSet());
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
