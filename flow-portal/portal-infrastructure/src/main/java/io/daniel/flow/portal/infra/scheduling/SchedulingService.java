package io.daniel.flow.portal.infra.scheduling;

/**
 * 定时调度的service
 *
 * @author neason-cn
 * @date 2022/2/1
 */
public interface SchedulingService {

    void schedule(String definitionId, String cronExpression);

    void cancel(String definitionId);

}
