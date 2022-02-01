package io.daniel.flow.portal.domain.refference.context;

import java.io.Serializable;
import java.util.Map;

/**
 * 整个流程实例共享的一个上下文
 *
 * @author neason-cn
 * @date 2022/2/1
 */
public interface Context extends Serializable {

    String get(String key);

    <T> T get(String key, Class<T> type);

    String extract(Expression expression);

    <T> T extract(Expression expression, Class<T> type);

    void merge(Map<String, String> addition);

    void clear();

}
