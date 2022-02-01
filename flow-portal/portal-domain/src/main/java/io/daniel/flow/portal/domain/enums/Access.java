package io.daniel.flow.portal.domain.enums;

import io.daniel.flow.portal.domain.support.Accessible;

/**
 * @author neason-cn
 * @date 2022/2/1
 */
public enum Access implements Accessible {

    ALLOW(true),
    DENY(false);

    ;

    private final boolean value;

    Access(boolean value) {
        this.value = value;
    }

    @Override
    public Access isAccess() {
        return this;
    }

    public static Access of(String value) {
        return of(Boolean.parseBoolean(value));
    }

    public static Access of(boolean value) {
        return  value ? ALLOW : DENY;
    }

}
