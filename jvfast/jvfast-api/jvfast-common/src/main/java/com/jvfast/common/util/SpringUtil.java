/**
 * Copyright 2019-2029 geekidea(https://github.com/geekidea)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jvfast.common.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * Spring工具类,获取Spring上下文对象等
 *
 * @author geekidea
 * @date 2018-11-08
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class SpringUtil implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext != null) {
            log.warn("SpringUtil中的ApplicationContext被覆盖, 原有ApplicationContext为:" + SpringUtil.applicationContext);
        }
        SpringUtil.applicationContext = applicationContext;
    }


    public static Object getBean(String name) {
        assertContextInjected();
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        assertContextInjected();
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        assertContextInjected();
        return applicationContext.getBean(name, clazz);
    }

    public static void publishEvent(ApplicationEvent event) {
        if (applicationContext == null) {
            return;
        }
        try {
            applicationContext.publishEvent(event);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    public void destroy() throws Exception {
        log.debug("清除SpringUtil中的ApplicationContext:"
                + applicationContext);
        applicationContext = null;
    }

    /**
     * 检查ApplicationContext不为空.
     */
    private static void assertContextInjected() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext属性未注入, 请在applicationContext" +
                    ".xml中定义SpringUtil或在SpringBoot启动类中注册SpringUtil.");
        }
    }
}
