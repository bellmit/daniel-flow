package io.daniel.flow.portal.domain.util;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import io.daniel.flow.portal.domain.refference.context.Expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 脚本执行相关的工具方法
 *
 * @author neason-cn
 * @date 2022/2/2
 */
public class ScriptUtil {

    private static final Pattern CONTEXT_PATTERN = Pattern.compile("(\\$\\{(context\\..*?)\\})");

    /**
     * 标识符替换，去除${}
     */
    public static String extract(Expression expression) {
        if (expression == null || expression.getExpression() == null) {
            return null;
        }
        Matcher matcher = CONTEXT_PATTERN.matcher(expression.getExpression());
        if (matcher.find()) {
            return matcher.replaceAll("$2");
        }
        return expression.getExpression();
    }

}
