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

import lombok.extern.slf4j.Slf4j;
import org.fusesource.jansi.Ansi;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * <p>
 * 打印项目信息
 * </p>
 *
 * @author geekidea
 * @date 2019-05-08
 **/
@Slf4j
public class PrintApplicationUtil {


    /**
     * 执行之前，打印前置条件提示
     */
    public static void printTip() {

        StringBuffer tip = new StringBuffer();
        tip.append("======================================================================================\n");
        tip.append("                                                                                  \n");
        tip.append("                               !!!准备工作!!!                                      \n");
        tip.append(" 1.请先在MySQL中创建数据库，默认数据库名称为：jvfast                                  \n");
        tip.append(" 2.数据库脚本在项目docs/schema.sql                                                  \n");
        tip.append(" 3.请先启动redis服务                                                               \n");
        tip.append("                                                                                  \n");
        tip.append("======================================================================================\n");
        log.info("\n{}", Ansi.ansi().eraseScreen().fg(Ansi.Color.YELLOW).a(tip.toString()).reset().toString());
    }

    /**
     * 启动成功之后，打印项目信息
     */
    public static void print(ConfigurableApplicationContext context) {
        // 将启动的java的pid写入配置的文件中,方便调试
        context.addApplicationListener(new ApplicationPidFileWriter());
        ConfigurableEnvironment environment = context.getEnvironment();

        // 项目名称
        String projectFinalName = environment.getProperty("spring.application.name");
        // 项目版本
        String projectVersion = environment.getProperty("spring.application.version");
        // 项目profile
        String profileActive = environment.getProperty("spring.profiles.active");
        // 项目路径
        String contextPath = environment.getProperty("server.servlet.context-path");
        // 项目端口
        String port = environment.getProperty("server.port");
        // banner
        String startSuccess = " ____    __                    __        ____                                                   \n" +
                "/\\  _`\\ /\\ \\__                /\\ \\__    /\\  _`\\                                                 \n" +
                "\\ \\,\\L\\_\\ \\ ,_\\    __     _ __\\ \\ ,_\\   \\ \\,\\L\\_\\  __  __    ___    ___     __    ____    ____  \n" +
                " \\/_\\__ \\\\ \\ \\/  /'__`\\  /\\`'__\\ \\ \\/    \\/_\\__ \\ /\\ \\/\\ \\  /'___\\ /'___\\ /'__`\\ /',__\\  /',__\\ \n" +
                "   /\\ \\L\\ \\ \\ \\_/\\ \\L\\.\\_\\ \\ \\/ \\ \\ \\_     /\\ \\L\\ \\ \\ \\_\\ \\/\\ \\__//\\ \\__//\\  __//\\__, `\\/\\__, `\\\n" +
                "   \\ `\\____\\ \\__\\ \\__/.\\_\\\\ \\_\\  \\ \\__\\    \\ `\\____\\ \\____/\\ \\____\\ \\____\\ \\____\\/\\____/\\/\\____/\n" +
                "    \\/_____/\\/__/\\/__/\\/_/ \\/_/   \\/__/     \\/_____/\\/___/  \\/____/\\/____/\\/____/\\/___/  \\/___/ \n" +
                "                                                                                                \n" +
                "                                                                                                ";

        String homeUrl = "http://" + IPUtil.getLocalhostIp() + ":" + port + contextPath;
        String swaggerUrl = "http://" + IPUtil.getLocalhostIp() + ":" + port + contextPath + "doc.html";
        log.info("\n{}", AnsiUtil.getAnsi(Ansi.Color.BLUE, startSuccess));
        log.info("projectName : {}", projectFinalName);
        log.info("projectVersion : {}", projectVersion);
        log.info("profileActive : {}", profileActive);
        log.info("contextPath : {}", contextPath);
        log.info("port : {}", port);
        log.info("home: {}", homeUrl);
        log.info("docs: {}", swaggerUrl);
        printTip();
    }

}
