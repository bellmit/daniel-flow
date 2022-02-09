package io.daniel.flow.portal.domain.util;

import com.alibaba.fastjson.JSONObject;
import io.daniel.flow.portal.domain.refference.context.Context;
import io.daniel.flow.portal.domain.refference.context.ContextConstant;
import io.daniel.flow.portal.domain.refference.context.Expression;
import io.daniel.flow.portal.infra.impl.script.QLExpressRunner;
import io.daniel.flow.portal.infra.script.ScriptRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author neason-cn
 * @date 2022/2/9
 */
public class ContextUtil {

    /**
     * 将一个表达式的Map，根据context实例化出具体的值
     *
     * @param contextExp value表达式
     * @param context    流程实例上下文
     * @return
     */
    public static Map<String, String> contextMapConvert(Map<String, Expression> contextExp, Context context) {
        Map<String, String> result = new HashMap<>();
        ScriptRunner runner = new QLExpressRunner();
        Map<String, Object> qlContext = new HashMap<>();
        qlContext.put(ContextConstant.KEY_IN_QL_CONTEXT, context.getAll());

        contextExp.forEach((k, v) -> {
            String expression = v.getExpression();
            Object value = runner.run(expression, qlContext);
            if (value == null) {
                result.put(k, expression);
            }else {
                result.put(k, JSONObject.toJSONString(value));
            }
        });

        return result;
    }

}
