package io.daniel.flow.portal.domain.refference.context;

import io.daniel.flow.connector.domain.TaskExecuteResult;
import io.daniel.flow.portal.domain.aggregate.FlowInstance;
import io.daniel.flow.portal.domain.refference.instance.task.TaskInstance;
import lombok.Data;

import java.util.Set;

/**
 * 在一次内存事务中操作共用的数据
 *
 * @author neason-cn
 * @date 2022/2/1
 */
@Data
public class Execution {

    /**
     * 流程实例
     */
    private FlowInstance flowInstance;

    /**
     * 流程的一次事务内推进所要执行的任务
     */
    private Set<TaskInstance> tasks;

    /**
     * 处理executor返回的结果
     */
    private TaskExecuteResult callback;

}
