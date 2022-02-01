package io.daniel.flow.portal.domain.refference.instance.task;

import io.daniel.flow.connector.domain.enums.TaskResultState;
import io.daniel.flow.portal.domain.refference.context.Execution;
import io.daniel.flow.portal.domain.refference.definition.task.TaskDefinition;
import io.daniel.flow.portal.domain.refference.instance.Instance;
import io.daniel.flow.portal.domain.support.Action;
import lombok.Data;

/**
 * @author neason-cn
 * @date 2022/2/1
 */
@Data
public class TaskInstance implements Instance<TaskDefinition>, Action, Cloneable {

    private TaskDefinition definition;
    private TaskResultState state;
    private String result;

    @Override
    public TaskDefinition getDefinition() {
        return definition;
    }

    @Override
    public void execute(Execution execution) {
    }

    @Override
    public TaskInstance clone() {
        TaskInstance clone = new TaskInstance();
        clone.setDefinition(definition);
        clone.setState(state);
        clone.setResult(result);
        return clone;
    }
}
