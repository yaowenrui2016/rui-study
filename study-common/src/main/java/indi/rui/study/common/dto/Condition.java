package indi.rui.study.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author: yaowr
 * @create: 2020-06-09
 */
@Getter
@NoArgsConstructor
public class Condition {

    private String propName;
    private Operator operator;
    private Object value;

    public Condition(String propName, Operator operator, Object value) {
        this.propName = propName;
        this.operator = operator;
        this.value = value;
    }

    @Getter
    @AllArgsConstructor
    public enum Operator {
        eq("=", "等于"),
        lt("<", "小于"),
        lte("<=", "小于等于"),
        gt(">", "大于"),
        gte(">=", "大于等于"),
        neq("!=", "不等于"),
        in("in", "在集合中"),
        notIn("not in", "不在集合中"),
        between("between", "在两值之间"),
        notBetween("not between", "不在两值之间"),
        like("like", "模糊匹配"),
        isNull("is null", "为空"),
        isNotNull("is not null", "不为空"),
        ;

        private String symbol;
        private String description;
    }

    public static Condition of(String propName, Object value) {
        return new Condition(propName, Operator.eq, value);
    }

    public static Condition of(String propName, Operator operator, Object value) {
        return new Condition(propName, operator, value);
    }
}
