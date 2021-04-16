package com.jvfast.common.util;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.junit.Test;

public class TranslatorUtilTest {

    @Test
    public void translate() {
        Long code = 3232334343443432329L;

        String inviteCode = InviteCodeUtil.toSerialCode(code);
        System.out.println(inviteCode);
        long codeToId = InviteCodeUtil.codeToId(inviteCode);
        System.out.println(codeToId);
        for (int k = 0, len = 100; k < len; k++) {
            String id = IdWorker.getIdStr();
            System.out.println(id);
        }

        String a = "ewewewe";
        String[] split = a.split(",");
        System.out.println(split);
    }
}
