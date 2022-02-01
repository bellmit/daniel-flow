package io.daniel.flow.portal.domain.refference.instance;

import io.daniel.flow.portal.domain.refference.definition.Definition;

/**
 * 实例类的接口，每个实例类都是从一个定义类实例化而来
 *
 * @author neason-cn
 * @date 2022/2/1
 */
public interface Instance<T extends Definition> {

    /**
     * 获取实例的定义
     */
    T getDefinition();

}
