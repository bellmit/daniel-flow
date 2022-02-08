package io.daniel.flow.portal.service;

import io.daniel.flow.portal.domain.aggregate.FlowDefinition;

/**
 * 流程定义/模板相关的Service
 *
 * @author neason-cn
 * @date 2022/2/1
 */
public interface FlowDefinitionService {

    /**
     * 保存流程模板定义
     */
    void save(FlowDefinition definition);

    /**
     * enable开关
     */
    void exchange(String definitionId, boolean enable);

    /**
     * 通过ID获取流程模板定义
     */
    FlowDefinition getById(String definitionId);

    /**
     * 通过code获取流程模板最新版本的定义
     */
    FlowDefinition getByCode(String definitionCode);

    /**
     * 通过code和指定的version获取流程模板定义
     */
    FlowDefinition getByCodeAndVersion(String definitionCode, String version);

}
