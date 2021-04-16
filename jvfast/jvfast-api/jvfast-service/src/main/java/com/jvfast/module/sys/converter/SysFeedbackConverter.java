package com.jvfast.module.sys.converter;

import com.jvfast.module.sys.model.entity.SysFeedback;
import com.jvfast.module.sys.model.param.SysFeedbackAddParam;
import com.jvfast.module.sys.model.param.SysFeedbackUpdateParam;
import com.jvfast.module.sys.model.vo.SysFeedbackQueryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysFeedback对象转换接口
 */
@Mapper(uses = {SysFeedbackConverter.ScreenshotTransform.class})
//@Mapper(componentModel = "spring")
public interface SysFeedbackConverter {

    SysFeedbackConverter INSTANCE = Mappers.getMapper(SysFeedbackConverter.class);

    /**
     * 添加操作
     * SysFeedbackAddParam对象转换为SysFeedback
     *
     * @param sysFeedbackAddParam
     * @return
     */
    SysFeedback convertSysFeedbackAddParam(SysFeedbackAddParam sysFeedbackAddParam);

    /**
     * 修改操作
     * SysFeedbackUpdateParam对象转换为SysFeedback
     *
     * @param sysFeedbackUpdateParam
     * @return
     */
    SysFeedback convertSysFeedbackUpdateParam(SysFeedbackUpdateParam sysFeedbackUpdateParam);

    /**
     * 查询列表转化实体
     * SysFeedback对象转换为SysFeedbackQueryVo
     *
     * @param sysFeedback
     * @return
     */
    List<SysFeedbackQueryVo> convertSysFeedbackList(List<SysFeedback> sysFeedback);

    /**
     * 查询分页操作
     * SysFeedback对象转换为SysFeedbackQueryVo
     *
     * @param sysFeedback
     * @return
     */
    SysFeedbackQueryVo convertSysFeedback(SysFeedback sysFeedback);


    class ScreenshotTransform {

        public String list2String(List<String> screenshots) {
            return String.join(",", screenshots);
        }
    }

}
