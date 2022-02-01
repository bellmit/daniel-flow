package io.daniel.flow.portal.domain.enums;

/**
 * 节点的类型
 *
 * @author neason-cn
 * @date 2022/2/1
 */
public enum NodeType {

    START("START"),
    END("END"),
    TASK("TASK");

    private final String type;

    NodeType(String type) {
        this.type = type;
    }

}
