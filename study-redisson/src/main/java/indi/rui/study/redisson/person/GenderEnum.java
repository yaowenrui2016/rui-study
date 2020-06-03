package indi.rui.study.redisson.person;

import javax.persistence.AttributeConverter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author: yaowr
 * @create: 2020-01-31
 */
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum GenderEnum {
    MALE("男"), FEMALE("女");

    private String value;

    public static GenderEnum of(String value) {
        for (GenderEnum genderEnum : GenderEnum.values()) {
            if (genderEnum.getValue().equals(value)) {
                return genderEnum;
            }
        }
        return null;
    }

    public static class Convertor implements AttributeConverter<GenderEnum, String> {
        @Override
        public String convertToDatabaseColumn(GenderEnum attribute) {
            if (attribute == null) {
                return null;
            }
            return attribute.getValue();
        }

        @Override
        public GenderEnum convertToEntityAttribute(String dbData) {
            if (dbData == null || dbData.length() == 0) {
                return null;
            }
            return GenderEnum.of(dbData);
        }
    }
}
