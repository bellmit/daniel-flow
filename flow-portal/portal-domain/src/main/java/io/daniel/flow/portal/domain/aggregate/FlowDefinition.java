package io.daniel.flow.portal.domain.aggregate;

import io.daniel.flow.portal.domain.refference.context.Expression;
import io.daniel.flow.portal.domain.refference.definition.Definition;
import io.daniel.flow.portal.domain.refference.definition.edge.EdgeDefinition;
import io.daniel.flow.portal.domain.refference.definition.node.AbstractNodeDefinition;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 流程图的定义
 *
 * @author neason-cn
 * @date 2022/2/1
 */
@Data
public class FlowDefinition implements Definition {

    private List<AbstractNodeDefinition> nodes;
    private List<EdgeDefinition> edges;
    private Map<String, Expression> globalConfig;

    @Override
    public String getDefinitionCode() {
        return null;
    }

    @Override
    public String getDefinitionName() {
        return null;
    }
}
