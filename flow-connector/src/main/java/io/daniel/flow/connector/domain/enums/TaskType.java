package io.daniel.flow.connector.domain.enums;

import lombok.Getter;

/**
 * executor支持的任务类型
 *
 * @author neason-cn
 * @date 2022/2/1
 */
public enum TaskType {

    HTTP("HTTP")

    ;

    @Getter
    private final String type;

    TaskType(String type) {
        this.type = type;
    }

    public static TaskType of(String type) {
        for (TaskType enumType: values()) {
            if (enumType.getType().equals(type)) {
                return enumType;
            }
        }
        return null;
    }

}
