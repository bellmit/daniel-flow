package io.daniel.flow.connector.domain;

import io.daniel.flow.connector.domain.enums.TaskResultState;
import lombok.Data;

import java.util.List;

/**
 * @author neason-cn
 * @date 2022/2/1
 */
@Data
public class TaskExecuteResult {

    private String flowInstanceId;
    private String nodeCode;
    private String taskId;
    private String taskRecordId;
    private TaskResultState state;
    private String data;
    private List<ShowResultItem> showItems;

}
