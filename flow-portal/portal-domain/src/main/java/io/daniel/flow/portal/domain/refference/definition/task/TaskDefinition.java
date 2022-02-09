package io.daniel.flow.portal.domain.refference.definition.task;

import io.daniel.flow.connector.domain.enums.TaskType;
import io.daniel.flow.portal.domain.refference.context.Expression;
import io.daniel.flow.portal.domain.refference.definition.Definition;
import lombok.Data;

import java.util.Map;

/**
 * 任务配置
 *
 * @author neason-cn
 * @date 2022/2/1
 */
@Data
public class TaskDefinition implements Definition {

    private String code;
    private String name;

    /**
     * 任务类型
     */
    private TaskType type;

    /**
     * 是否自动执行
     */
    private boolean enableAutoResume;

    /**
     * 是否允许跳过
     */
    private boolean enableSkip;

    /**
     * 是否支持重试
     */
    private boolean enableRetry;

    /**
     * 重试次数
     */
    private Integer retries;

    /**
     * 该任务执行之后，结果会自动放入context中，context的key
     */
    private String resultKey;

    /**
     * 是否开启异步处理
     */
    private boolean enableAsync;

    /**
     * 该任务所需要的参数列表
     */
    private Map<String, Expression> params;

    @Override
    public String getDefinitionCode() {
        return code;
    }

    @Override
    public String getDefinitionName() {
        return name;
    }
}
