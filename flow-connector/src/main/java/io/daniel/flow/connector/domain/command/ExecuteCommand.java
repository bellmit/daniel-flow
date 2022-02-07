package io.daniel.flow.connector.domain.command;

import io.daniel.flow.connector.domain.enums.CommandType;
import io.daniel.flow.connector.domain.task.BaseTask;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * portal 向 executor 发送的command
 *
 * @author neason-cn
 * @date 2022/2/1
 */
@Data
public class ExecuteCommand implements Serializable {

    /**
     * keys:
     * x-flow-command-type: 命令类型
     * x-flow-task-type :   任务类型
     */
    private Map<String, String> headers;

    private BaseTask body;

}
