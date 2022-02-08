package io.daniel.flow.portal.service;

import io.daniel.flow.connector.domain.TaskExecuteResult;
import io.daniel.flow.portal.domain.aggregate.FlowInstance;

import java.util.Map;

/**
 * 流程实例的相关Service
 *
 * @author neason-cn
 * @date 2022/2/1
 */
public interface FlowInstanceService {

    /**
     * 根据流程模板定义的ID创建实例
     */
    FlowInstance createWithId(String definitionId);

    /**
     * 根据流程模板定义code的最新版本创建实例
     */
    FlowInstance createWithCode(String definitionCode);

    /**
     * 根据流程模板定义code和指定的版本创建实例
     */
    FlowInstance createWithCodeAndVersion(String definitionCode, String version);

    /**
     * 重新唤醒一个流程实例，并推进
     */
    void resume(String instanceId, String nodeCode, Map<String, String> params);

    /**
     * 处理Executor返回的执行结果
     */
    void handleCallBack(TaskExecuteResult callback);

    /**
     * 跳过一个节点
     */
    void skip(String instanceId, String nodeCode);

    /**
     * 取消一个流程实例
     */
    void cancel(String instanceId);

}
