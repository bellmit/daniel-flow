package io.daniel.flow.portal.domain.enums;

import lombok.Getter;

/**
 * @author neason-cn
 * @date 2022/2/1
 */
public enum NodeInstanceState {

    INIT("INIT"),
    PENDING("PENDING"),
    RUNNING("RUNNING"),
    FINISH("FINISH"),
    SKIPPED("SKIPPED"),
    CANCELED("CANCELED"),
    ERROR("ERROR"),

    ;

    @Getter
    private final String state;

    NodeInstanceState(String state) {
        this.state = state;
    }

}
