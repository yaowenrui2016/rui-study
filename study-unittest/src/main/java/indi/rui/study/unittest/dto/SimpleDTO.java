package indi.rui.study.unittest.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2021-12-09
 */
@Getter
@Setter
public class SimpleDTO {
    private String id;
    private String name;
    private String code;

    public static SimpleDTO of(String id, String name, String code) {
        SimpleDTO dto = new SimpleDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setCode(code);
        return dto;
    }
}
