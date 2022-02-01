package io.daniel.flow.portal.domain.refference.definition;

/**
 * 定义类的标识接口
 *
 * @author neason-cn
 * @date 2022/2/1
 */
public interface Definition {

    /**
     * 定义的Code
     */
    String getDefinitionCode();

    /**
     * 定义的名称，用于展示
     */
    String getDefinitionName();

}
