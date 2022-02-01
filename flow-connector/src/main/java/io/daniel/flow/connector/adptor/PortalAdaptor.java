package io.daniel.flow.connector.adptor;

import io.daniel.flow.connector.domain.TaskExecuteResult;
import io.daniel.flow.connector.domain.command.ExecuteCommand;

/**
 * Portal侧的适配器
 *
 * @author neason-cn
 * @date 2022/2/1
 */
public interface PortalAdaptor {

    /**
     * Portal向Executor发送命令
     */
    void publishCommand(ExecuteCommand command);

    /**
     * Portal接收Executor执行的结果
     */
    void onReceiveResult(TaskExecuteResult result);

}
