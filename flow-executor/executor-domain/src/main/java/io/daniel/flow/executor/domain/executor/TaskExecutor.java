package io.daniel.flow.executor.domain.executor;

import io.daniel.flow.connector.domain.command.ExecuteCommand;
import io.daniel.flow.connector.domain.enums.TaskType;

/**
 * 具体任务的执行器
 *
 * @author neason-cn
 * @date 2022/2/7
 */
public interface TaskExecutor {

    void execute(ExecuteCommand command);

    void cancel(ExecuteCommand command);

    TaskType getSupportTask();

}
