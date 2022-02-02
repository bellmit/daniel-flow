package io.daniel.flow.portal.domain.support.metric;

/**
 * @author neason-cn
 * @date 2022/2/1
 */
public interface Timing extends Metric<Long> {

    Long getStart();

    Long getEnd();

    default Long getDuring() {
        return getEnd() - getStart();
    }

    @Override
    default Long getMetric() {
        return getDuring();
    }
}
