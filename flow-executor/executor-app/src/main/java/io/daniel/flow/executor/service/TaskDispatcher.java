package io.daniel.flow.executor.service;

import io.daniel.flow.connector.domain.command.ExecuteCommand;
import io.daniel.flow.executor.domain.executor.TaskExecutor;

/**
 * @author neason-cn
 * @date 2022/2/7
 */
public interface TaskDispatcher {

    TaskExecutor dispatch(ExecuteCommand command);

}
