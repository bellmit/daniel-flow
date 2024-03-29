package io.daniel.flow.connector.adptor;

import io.daniel.flow.connector.domain.TaskExecuteResult;
import io.daniel.flow.connector.domain.command.ExecuteCommand;

/**
 * Executor侧的适配器
 *
 * @author neason-cn
 * @date 2022/2/1
 */
public interface ExecutorAdaptor {

    /**
     * Executor向Portal发送任务执行的结果
     */
    void publishResult(TaskExecuteResult result);

    /**
     * Executor接收Portal发送的命令
     * 如果adaptor支持同步调用，当接收到command，直接执行返回结果
     */
    TaskExecuteResult onReceiveCommand(ExecuteCommand command);

}
