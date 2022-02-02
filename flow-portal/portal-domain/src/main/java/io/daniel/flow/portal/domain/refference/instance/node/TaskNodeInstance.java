package io.daniel.flow.portal.domain.refference.instance.node;

import io.daniel.flow.connector.domain.TaskExecuteResult;
import io.daniel.flow.connector.domain.enums.TaskResultState;
import io.daniel.flow.portal.domain.enums.FlowInstanceState;
import io.daniel.flow.portal.domain.enums.JoinMode;
import io.daniel.flow.portal.domain.enums.NodeInstanceState;
import io.daniel.flow.portal.domain.enums.NodeType;
import io.daniel.flow.portal.domain.refference.context.Execution;
import io.daniel.flow.portal.domain.refference.definition.node.TaskNodeDefinition;
import io.daniel.flow.portal.domain.refference.definition.task.TaskDefinition;
import io.daniel.flow.portal.domain.refference.instance.task.TaskInstance;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @author neason-cn
 * @date 2022/2/1
 */
@Data
public class TaskNodeInstance extends AbstractNodeInstance<TaskNodeDefinition> {

    private TaskNodeDefinition definition;
    private List<TaskInstance> done;
    private TaskInstance running;
    private List<TaskDefinition> left;
    private JoinMode joinMode;

    @Override
    public TaskNodeDefinition getDefinition() {
        return definition;
    }

    @Override
    public void execute(Execution execution) {
        if (canAccept()) {
            if (canResume()) {
                if (execution.getCallback() != null) {
                    handleCallback(execution);
                } else {
                    popAndRunNewTask(execution);
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
        if (joinMode == JoinMode.WAIT_ANY) {
            return incoming.stream()
                    .anyMatch(edgeInstance -> edgeInstance.isAccess().isAllow());
        } else {
            return incoming.stream()
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
        if (callback.getState() == TaskResultState.SUCCESS) {
            done.add(running.clone());
            running = null;
            if (allTaskDone()) {
                this.setState(NodeInstanceState.FINISH);
                createEdgesAndAutoExecute(execution);
            } else {
                TaskDefinition taskDefinition = popTask();
                if (taskDefinition.isEnableAutoResume()) {
                    popAndRunNewTask(execution);
                } else {
                    keepWaiting();
                }
            }
        } else {
            this.setState(NodeInstanceState.ERROR);
            execution.getFlowInstance().setState(FlowInstanceState.ERROR);
        }

    }

    private boolean allTaskDone() {
        return left == null || popTask() == null;
    }

    private void popAndRunNewTask(Execution execution) {
        TaskDefinition taskDefinition = popTask();
        TaskInstance taskInstance = new TaskInstance();
        taskInstance.setDefinition(taskDefinition);
        taskInstance.setState(TaskResultState.RUNNING);
        running = taskInstance;
        execution.getTasks().add(taskInstance);
    }

    private TaskDefinition popTask() {
        return left.get(0);
    }

}
