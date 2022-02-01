package io.daniel.flow.portal.domain.support.metric;

/**
 * 流程引擎中需要监控的指标
 *
 * @author neason-cn
 * @date 2022/2/1
 */
public interface Metric<T> {

    T getMetric();

}
