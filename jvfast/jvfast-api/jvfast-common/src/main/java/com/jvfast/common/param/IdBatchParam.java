package com.jvfast.common.param;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @project: jvfast
 * @author: Walter Hu
 * @create: 2019-12-11 00:28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("ID数组参数")
public class IdBatchParam implements Serializable {
    private static final long serialVersionUID = -2323510450L;

    @NotEmpty(message = "至少传递一个参数")
    private List<Long> ids = Lists.newArrayList();
}
