package indi.rui.study.unittest.dto.e2e;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2022-05-17
 */
@Getter
@Setter
public class ReferGroup {

    private String type;

    private String name;

    private String entityName;

    private Boolean isOrg;

    private List<ReferTreeNode> referNodes;
}
