package indi.rui.study.hibernate.generator;

import org.hibernate.Session;
import org.hibernate.tuple.ValueGenerator;

import java.util.Date;

/**
 * @author: yaowr
 * @create: 2020-04-21
 */
public class CreateTimeGenerator implements ValueGenerator<Date> {
    @Override
    public Date generateValue(Session session, Object owner) {
        return new Date();
    }
}
