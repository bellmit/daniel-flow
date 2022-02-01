package io.daniel.flow.portal.domain.refference.instance.task;

import io.daniel.flow.portal.domain.refference.context.Execution;
import io.daniel.flow.portal.domain.refference.definition.task.TaskDefinition;
import io.daniel.flow.portal.domain.refference.instance.Instance;
import io.daniel.flow.portal.domain.support.Action;

/**
 * @author neason-cn
 * @date 2022/2/1
 */
public class TaskInstance implements Instance<TaskDefinition>, Action {

    private TaskDefinition definition;
    private String result;

    @Override
    public TaskDefinition getDefinition() {
        return definition;
    }

    @Override
    public void execute(Execution execution) {

    }
}
