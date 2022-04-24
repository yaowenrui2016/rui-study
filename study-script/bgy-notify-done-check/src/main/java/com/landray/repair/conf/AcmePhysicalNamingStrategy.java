package com.landray.repair.conf;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: yaowr
 * @create: 2022-01-28
 */
@Slf4j
public class AcmePhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    private static final Map<String, String> ABBREVIATIONS;

    static {
        ABBREVIATIONS = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        ABBREVIATIONS.put("account", "acct");
        ABBREVIATIONS.put("number", "num");
    }

//    @Override
//    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
//        final List<String> parts = splitAndReplace(name.getText());
//        return jdbcEnvironment.getIdentifierHelper().toIdentifier(
//                StringUtils.join(parts, '_'),
//                name.isQuoted()
//        );
//    }

//    @Override
//    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
//        final List<String> parts = splitAndReplace(name.getText());
//        // Acme Corp says all sequences should end with _seq
//        if (!"seq".equals(parts.get(parts.size() - 1))) {
//            parts.add("seq");
//        }
//        return jdbcEnvironment.getIdentifierHelper().toIdentifier(
//                StringUtils.join(parts, '_'),
//                name.isQuoted()
//        );
//    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        final List<String> parts = splitAndReplace(name.getText());
        List<String> resultParts = new ArrayList<>();
        for (int i = 0, p = 0; i < parts.size(); i++) {
            try {
                Integer.valueOf(parts.get(i));
                // 如果是数字
                if (p - 1 > 0) {
                    String pre = resultParts.get(p - 1);
                    resultParts.set(p - 1, pre.concat(parts.get(i)));
                }
            } catch (NumberFormatException e) {
                resultParts.add(parts.get(i));
                p++;
            }
        }
        return jdbcEnvironment.getIdentifierHelper().toIdentifier(
                StringUtils.join(resultParts, '_'),
                name.isQuoted()
        );
    }

    private List<String> splitAndReplace(String name) {
        return Arrays.stream(StringUtils.splitByCharacterTypeCamelCase(name))
                .filter(StringUtils::isNotBlank)
                .map(p -> ABBREVIATIONS.getOrDefault(p, p).toLowerCase(Locale.ROOT))
                .collect(Collectors.toList());
    }
}
