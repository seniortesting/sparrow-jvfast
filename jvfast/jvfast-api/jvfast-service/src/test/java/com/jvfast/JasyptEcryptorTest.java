package com.jvfast;

import cn.hutool.core.lang.Console;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
public class JasyptEcryptorTest {

    @Autowired
    StringEncryptor stringEncryptor;


    @Test
    public void encryptPwd() {
        // 将上面的加密码放在对应的application.yml中 ENC(8s8U3PNKC/PLgzK4nUzkqFwRBy0exXWv)
        // ENC(+MMpFNWpyeg99/Jpa6ewEQWvjncpFn0L) eocpviiluqamdjhe
        String gmail = stringEncryptor.decrypt("MOJAVmMYz5sp3pl1b7b6qGzu/wC9MzNA");
        Console.log("gmail: {}", gmail);

    }

}
