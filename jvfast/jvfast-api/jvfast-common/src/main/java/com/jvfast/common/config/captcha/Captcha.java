/**
 * Copyright © 2018 TaoYu (tracy5546@gmail.com)
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
package com.jvfast.common.config.captcha;

import java.util.Map;

/**
 * 验证码组件接口
 *
 * @author TaoYu
 */
public interface Captcha {

    /**
     * 渲染验证码
     *
     * @return 验证码内容
     */
    Map render();

    /**
     * 校对验证码,默认超时15分钟（900s）
     *
     * @param sessionId
     * @return 是否验证成功
     * @see CaptchaException
     */
    String getCaptcha(String sessionId);

}
