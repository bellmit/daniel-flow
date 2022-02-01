package io.daniel.flow.connector.domain.command;

import io.daniel.flow.connector.domain.enums.CommandType;

import java.util.Map;

/**
 * portal 向 executor 发送的command
 *
 * @author neason-cn
 * @date 2022/2/1
 */
public interface ExecuteCommand<T> {

    CommandType commandType();

    Map<String, String> headers();

    T body();

}
