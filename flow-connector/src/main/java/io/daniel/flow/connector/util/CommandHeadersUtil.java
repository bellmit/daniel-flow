package io.daniel.flow.connector.util;

import io.daniel.flow.connector.constant.CommandHeaderConstant;
import io.daniel.flow.connector.domain.command.ExecuteCommand;
import io.daniel.flow.connector.domain.enums.CommandType;
import io.daniel.flow.connector.domain.enums.TaskType;

/**
 * @author neason-cn
 * @date 2022/2/8
 */
public class CommandHeadersUtil {

    public static CommandType getCommandType(ExecuteCommand command) {
        String header = command.getHeaders().get(CommandHeaderConstant.COMMAND_TYPE);
        return CommandType.of(header);
    }

    public static TaskType getTaskType(ExecuteCommand command) {
        String header = command.getHeaders().get(CommandHeaderConstant.TASK_TYPE);
        return TaskType.of(header);
    }

}
