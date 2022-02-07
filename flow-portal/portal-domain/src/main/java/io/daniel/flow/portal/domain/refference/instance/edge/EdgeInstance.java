package io.daniel.flow.portal.domain.refference.instance.edge;

import io.daniel.flow.portal.domain.enums.Access;
import io.daniel.flow.portal.domain.enums.EdgeInstanceState;
import io.daniel.flow.portal.domain.refference.context.Execution;
import io.daniel.flow.portal.domain.refference.definition.edge.EdgeDefinition;
import io.daniel.flow.portal.domain.refference.definition.node.AbstractNodeDefinition;
import io.daniel.flow.portal.domain.refference.instance.Instance;
import io.daniel.flow.portal.domain.refference.instance.node.AbstractNodeInstance;
import io.daniel.flow.portal.domain.support.Accessible;
import io.daniel.flow.portal.domain.support.Action;
import io.daniel.flow.portal.domain.support.graph.Edge;
import io.daniel.flow.portal.domain.support.metric.Timing;
import io.daniel.flow.portal.domain.util.ScriptUtil;
import io.daniel.flow.portal.infra.impl.script.QLExpressRunner;
import io.daniel.flow.portal.infra.script.ScriptRunner;
import lombok.Data;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author neason-cn
 * @date 2022/2/1
 */
@Data
public class EdgeInstance implements Edge<AbstractNodeInstance<? extends AbstractNodeDefinition>>, Instance<EdgeDefinition>, Action, Accessible, Timing {

    private EdgeDefinition definition;
    private EdgeInstanceState state;
    private AbstractNodeInstance<? extends AbstractNodeDefinition> source;
    private AbstractNodeInstance<? extends AbstractNodeDefinition> target;
    private Long start;
    private Long end;

    @Override
    public Access isAccess() {
        return Access.of(state == EdgeInstanceState.ALLOW);
    }

    @Override
    public void execute(Execution execution) {
        boolean result = runScript(execution);
        if (result) {
            this.setState(EdgeInstanceState.ALLOW);
            createNodesAndAutoExecute(execution);
        } else {
            this.setState(EdgeInstanceState.DENY);
        }
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

    /**
     * 运行边上配置的条件
     */
    private boolean runScript(Execution execution) {
        ScriptRunner runner = new QLExpressRunner();
        String script = ScriptUtil.extract(definition.getCondition());
        Map<String, Object> context = execution.getFlowInstance().getContext().getAll();
        return runner.run(script, context, boolean.class);
    }

    /**
     * 创建后续节点并执行
     */
    private void createNodesAndAutoExecute(Execution execution) {
        AbstractNodeInstance<? extends AbstractNodeDefinition> node =
                execution.getFlowInstance().getOrCreateNode(definition.getTarget(), this);
        node.execute(execution);
    }

}
