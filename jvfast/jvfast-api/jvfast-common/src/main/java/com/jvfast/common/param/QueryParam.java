/*
 * Copyright 2019-2029 geekidea(https://github.com/geekidea)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jvfast.common.param;

import com.jvfast.common.constant.PageConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 查询参数
 *
 * @author geekidea
 * @since 2018-11-08
 */
@Data
@ApiModel("查询参数对象")
public abstract class QueryParam implements Serializable {
    private static final long serialVersionUID = -3263921252635611410L;

    @ApiModelProperty(value = "页码,默认为1")
    private Integer pageNo = PageConst.PAGE_INDEX;
    @ApiModelProperty(value = "页大小,默认为10")
    private Integer pageSize = PageConst.PAGE_SIZE;
    @ApiModelProperty(value = "搜索字符串")
    private String keyword;
    /**
     * 查询记录的开始时间和结束时间条件
     */
    private LocalDate startDate;
    private LocalDate endDate;

    public void setPageNo(Integer current) {
        if (current == null || current <= 0) {
            this.pageNo = PageConst.PAGE_INDEX;
        } else {
            this.pageNo = current;
        }
    }

    public void setPageSize(Integer size) {
        if (size == null || size <= 0) {
            this.pageSize = PageConst.PAGE_SIZE;
        } else {
            this.pageSize = size;
        }
    }

}
