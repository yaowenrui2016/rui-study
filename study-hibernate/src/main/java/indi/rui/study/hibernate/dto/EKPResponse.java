package indi.rui.study.hibernate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2021-11-26
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EkpResponse {
    private int returnState;
    private String message;
}
