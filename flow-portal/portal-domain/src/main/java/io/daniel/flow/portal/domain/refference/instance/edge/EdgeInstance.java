package io.daniel.flow.portal.domain.refference.instance.edge;

import io.daniel.flow.portal.domain.enums.Access;
import io.daniel.flow.portal.domain.refference.context.Execution;
import io.daniel.flow.portal.domain.refference.definition.edge.EdgeDefinition;
import io.daniel.flow.portal.domain.refference.definition.node.AbstractNodeDefinition;
import io.daniel.flow.portal.domain.refference.instance.Instance;
import io.daniel.flow.portal.domain.refference.instance.node.AbstractNodeInstance;
import io.daniel.flow.portal.domain.support.Accessible;
import io.daniel.flow.portal.domain.support.Action;
import io.daniel.flow.portal.domain.support.graph.Edge;
import io.daniel.flow.portal.domain.support.metric.Timing;
import lombok.Data;

import java.util.Collections;
import java.util.Set;

/**
 * @author neason-cn
 * @date 2022/2/1
 */
@Data
public class EdgeInstance implements Edge<AbstractNodeInstance<? extends AbstractNodeDefinition>>, Instance<EdgeDefinition>, Action, Accessible, Timing {

    private EdgeDefinition definition;
    private boolean expressResult;
    private AbstractNodeInstance<? extends AbstractNodeDefinition> source;
    private AbstractNodeInstance<? extends AbstractNodeDefinition> target;
    private Long start;
    private Long end;

    @Override
    public Access isAccess() {
        return Access.of(expressResult);
    }

    @Override
    public Execution execute(Execution execution) {
        return null;
    }

    @Override
    public Set<AbstractNodeInstance<? extends AbstractNodeDefinition>> incoming() {
        return Collections.singleton(source);
    }

    @Override
    public Set<AbstractNodeInstance<? extends AbstractNodeDefinition>> outgoing() {
        return Collections.singleton(target);
    }

    @Override
    public Long getStart() {
        return start;
    }

    @Override
    public Long getEnd() {
        return end;
    }

    @Override
    public EdgeDefinition getDefinition() {
        return definition;
    }
}
