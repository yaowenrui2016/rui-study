package indi.rui.study.mktools.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2021-11-30
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumeResult {
    private Integer bakTotal;
    private Integer resumeTotal;
    private String ErrorMsg;
}
