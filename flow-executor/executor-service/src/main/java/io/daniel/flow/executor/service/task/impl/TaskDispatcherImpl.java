package io.daniel.flow.executor.service.task.impl;

import io.daniel.flow.connector.domain.command.ExecuteCommand;
import io.daniel.flow.connector.util.CommandHeadersUtil;
import io.daniel.flow.executor.domain.executor.TaskExecutor;
import io.daniel.flow.executor.service.task.TaskDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author neason-cn
 * @date 2022/2/8
 */
@Component
@RequiredArgsConstructor
public class TaskDispatcherImpl implements TaskDispatcher {

    private final List<TaskExecutor> executorCandidates;

    @Override
    public TaskExecutor dispatch(ExecuteCommand command) {
        if (executorCandidates == null) {
            return null;
        }
        for (TaskExecutor executor: executorCandidates) {
            if (executor.getSupportTask().equals(CommandHeadersUtil.getTaskType(command))) {
                return executor;
            }
        }
        return null;
    }

}
