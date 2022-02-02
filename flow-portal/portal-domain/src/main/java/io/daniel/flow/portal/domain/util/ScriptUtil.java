package io.daniel.flow.portal.domain.util;

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

    private static final Pattern CONTEXT_PARTEN = Pattern.compile("(\\$\\{(context.*?)\\})");

    public static String initContextExpress(Expression expression) {
        if (expression == null) {
            return null;
        }
        Matcher matcher = CONTEXT_PARTEN.matcher(expression.getExpression());
        if (matcher.find()) {
            return matcher.replaceAll("$2");
        }
        return null;
    }

    public static void main(String[] args) {
        String express = "${context.name} + ${context.age.a.a} + 2 + ${context.12.123.213}" ;
        Expression expression = new Expression();
        expression.setExpression(express);

        System.out.println(ScriptUtil.initContextExpress(expression));
    }


}
