package io.daniel.flow.portal.infra.script;

import java.util.Map;

/**
 * @author neason-cn
 * @date 2022/2/2
 */
public interface ScriptRunner {

    Object run(String script, Map<String, Object> context);

    <T> T run(String script, Map<String, Object> context, Class<T> clazz);

}
