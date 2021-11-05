package indi.rui.study.kafkatest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2021-11-03
 */
@Getter
@Setter
public class SourceAppDTO {
    private String name;
    private String code;
    private List<SourceAppDTO> children;
}
