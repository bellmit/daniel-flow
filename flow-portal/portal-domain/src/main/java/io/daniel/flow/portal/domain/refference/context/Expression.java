package io.daniel.flow.portal.domain.refference.context;

import lombok.Data;

/**
 * 包装context的表达式
 * context的表达式与真实值都是String,为了区分避免混淆
 *
 *
 * @author neason-cn
 * @date 2022/2/1
 */
@Data
public class Expression {

    private String expression;

}
