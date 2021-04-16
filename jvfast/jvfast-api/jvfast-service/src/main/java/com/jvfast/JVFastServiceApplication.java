package com.jvfast;

import com.jvfast.common.util.PrintApplicationUtil;
import org.apache.catalina.core.AprLifecycleListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JVFastServiceApplication {

    /**
     * 内置tomcat开启apr模式提高性能,windows和linux配置不同，参考文章: https://code.pingbook.top/blog/setup/tomcat.html
     * @return
     */
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
        tomcatServletWebServerFactory.setProtocol("org.apache.coyote.http11.Http11AprProtocol");
        tomcatServletWebServerFactory.addContextLifecycleListeners( new AprLifecycleListener());
        return tomcatServletWebServerFactory;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(JVFastServiceApplication.class, args);
        PrintApplicationUtil.print(applicationContext);
    }
}
