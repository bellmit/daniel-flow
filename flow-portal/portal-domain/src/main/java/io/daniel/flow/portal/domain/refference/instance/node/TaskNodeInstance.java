package io.daniel.flow.portal.domain.refference.instance.node;

import io.daniel.flow.connector.domain.TaskExecuteResult;
import io.daniel.flow.connector.domain.enums.TaskResultState;
import io.daniel.flow.portal.domain.enums.JoinMode;
import io.daniel.flow.portal.domain.enums.NodeInstanceState;
import io.daniel.flow.portal.domain.enums.NodeType;
import io.daniel.flow.portal.domain.refference.context.Execution;
import io.daniel.flow.portal.domain.refference.definition.node.TaskNodeDefinition;
import io.daniel.flow.portal.domain.refference.definition.task.TaskDefinition;
import io.daniel.flow.portal.domain.refference.instance.task.TaskInstance;
import io.daniel.flow.portal.domain.util.ContextUtil;
import lombok.Data;

import java.util.Arrays;
import java.util.Map;

/**
 * @author neason-cn
 * @date 2022/2/1
 */
@Data
public class TaskNodeInstance extends AbstractNodeInstance<TaskNodeDefinition> {

    private TaskNodeDefinition definition;
    private TaskInstance taskInstance;

    @Override
    public TaskNodeDefinition getDefinition() {
        return definition;
    }

    @Override
    public void execute(Execution execution) {
        if (canAccept()) {
            if (canResume()) {
                if (execution.getCallback() != null) {
                    // 处理callback
                    handleCallback(execution);
                } else {
                    // 没有callback代表是第一次触发
                    runTask(execution);
                }
            }
        } else {
            keepWaiting();
        }
    }

    @Override
    public NodeType getType() {
        return NodeType.TASK;
    }

    /**
     * 是否满足join条件
     */
    private boolean canAccept() {
        if (definition.getJoinMode() == JoinMode.WAIT_ANY) {
            return incoming.stream()
                    .anyMatch(edgeInstance -> edgeInstance.isAccess().isAllow());
        } else {
            // incoming只有到到达了的edge，没有到达的还没有被实例化，所以要加一个size的判断
            return incoming.size() == definition.incoming().size() &&
                    incoming.stream()
                            .allMatch(edgeInstance -> edgeInstance.isAccess().isAllow());
        }
    }

    /**
     * 该节点能否处理任务
     */
    private boolean canResume() {
        return Arrays.asList(
                NodeInstanceState.INIT,
                NodeInstanceState.ERROR,
                NodeInstanceState.PENDING,
                NodeInstanceState.RUNNING
        ).contains(state);
    }

    private void keepWaiting() {
        this.setState(NodeInstanceState.PENDING);
    }


    private void handleCallback(Execution execution) {
        TaskExecuteResult callback = execution.getCallback();
        taskInstance.setState(callback.getState());
        taskInstance.setResult(callback.getData());
        if (callback.getState() == TaskResultState.SUCCESS) {
            this.setState(NodeInstanceState.FINISH);
            taskInstance.setState(TaskResultState.SUCCESS);
            createEdgesAndAutoExecute(execution);
        } else {
            this.setState(NodeInstanceState.ERROR);
        }
    }

    private void runTask(Execution execution) {
        TaskDefinition taskDefinition = definition.getTaskDefinition();
        TaskInstance taskInstance = new TaskInstance();
        taskInstance.setDefinition(taskDefinition);
        taskInstance.setState(TaskResultState.RUNNING);
        Map<String, String> params = ContextUtil.contextMapConvert(taskDefinition.getParams(), execution.getFlowInstance().getContext());
        taskInstance.setParams(params);
        execution.getTasks().add(taskInstance);
    }

    @Override
    public void onCancel(Execution execution) {
        if (state == NodeInstanceState.RUNNING) {
            taskInstance.setState(TaskResultState.CANCELED);
            this.state = NodeInstanceState.CANCELED;
            execution.getTasks().add(taskInstance);
        }
    }

}
