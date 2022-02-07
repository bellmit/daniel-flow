package io.daniel.flow.connector.domain.command;

import io.daniel.flow.connector.domain.enums.CommandType;
import io.daniel.flow.connector.domain.task.BaseTask;
import lombok.Data;

import java.util.Map;

/**
 * portal 向 executor 发送的command
 *
 * @author neason-cn
 * @date 2022/2/1
 */
@Data
public class ExecuteCommand {

    private CommandType type;
    private Map<String, String> headers;
    private BaseTask body;

}
