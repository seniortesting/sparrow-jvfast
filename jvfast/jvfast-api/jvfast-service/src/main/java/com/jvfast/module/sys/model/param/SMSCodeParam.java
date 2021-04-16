package com.jvfast.module.sys.model.param;

import com.jvfast.common.constraint.Phone;
import com.jvfast.common.enums.SmsTemplateEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SMSCodeParam {

    @Phone(message = "非法的手机号码")
    private String phone;
    private SmsTemplateEnum type;
}
