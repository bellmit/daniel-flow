package io.daniel.flow.portal.infra.impl.script;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import io.daniel.flow.portal.infra.script.ScriptRunner;

import java.util.Map;

/**
 * QLExpress脚本执行器
 *
 * @author neason-cn
 * @date 2022/2/2
 */
public class QLExpressRunner implements ScriptRunner {

    @Override
    public Object run(String script, Map<String, Object> context) {
        ExpressRunner ql = new ExpressRunner();
        Object result = null;
        try {
            result = ql.execute(script, convert(context), null, true, false);
        } catch (Exception e) {
            throw new RuntimeException("QL表达式执行失败", e);
        }
        return result;
    }

    @Override
    public <T> T run(String script, Map<String, Object> context, Class<T> clazz) {
        Object result = run(script, context);
        return (T) result;
    }

    private DefaultContext<String, Object> convert(Map<String, Object> input) {
        DefaultContext<String, Object> context = new DefaultContext<>();
        context.putAll(input);
        return context;
    }

}
