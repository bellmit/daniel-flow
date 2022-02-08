package io.daniel.flow.connector.domain.enums;

import lombok.Getter;

/**
 * @author neason-cn
 * @date 2022/2/1
 */
public enum CommandType {

    EXECUTE("EXECUTE"),
    CANCEL("CANCEL")
    ;

    @Getter
    private final String type;

    CommandType(String type) {
        this.type = type;
    }

    public static CommandType of(String type) {
        for (CommandType enumType: values()) {
            if (enumType.getType().equals(type)) {
                return enumType;
            }
        }
        return null;
    }

}
