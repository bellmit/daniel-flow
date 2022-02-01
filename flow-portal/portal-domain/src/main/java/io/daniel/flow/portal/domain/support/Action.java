package io.daniel.flow.portal.domain.support;

import io.daniel.flow.portal.domain.refference.context.Execution;

/**
 * 流程中一个事务内活动的接口
 *
 * @author neason-cn
 * @date 2022/2/1
 */
public interface Action {

    void execute(Execution execution);

}
