package com.jvfast.common.config.web;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.jvfast.common.api.ResultCode;
import com.jvfast.common.config.JVFastCommonProperties;
import com.jvfast.common.shiro.config.ShiroProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    private final JVFastCommonProperties jvFastCommonProperties;
    private final ShiroProperties shiroProperties;

    @Bean
    public Docket swagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(swaggerApiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, defaultGlobalResponseCodes())
                .globalResponseMessage(RequestMethod.POST, defaultGlobalResponseCodes())
                .globalResponseMessage(RequestMethod.DELETE, defaultGlobalResponseCodes())
                .globalResponseMessage(RequestMethod.PUT, defaultGlobalResponseCodes())
                .globalOperationParameters(defaultGlobalHeaders());

    }

    private ApiInfo swaggerApiInfo() {
        JVFastCommonProperties.SWagger swagger = jvFastCommonProperties.getSwagger();
        return new ApiInfoBuilder()
                .title(swagger.getTitle())
                .version(swagger.getVersion())
                .description(swagger.getDescription())
                .termsOfServiceUrl(swagger.getTermsOfServiceUrl())
                .contact(new Contact(swagger.getContact().getName(), swagger.getContact().getUrl(), swagger.getContact().getEmail()))
                .build();
    }

    private List<ResponseMessage> defaultGlobalResponseCodes() {
        List<ResponseMessage> responseMessages = Lists.newArrayList();

        // ModelRef errorModel = new ModelRef("RestApiExceptionModel");
        ResponseMessageBuilder responseMessageBuilder = new ResponseMessageBuilder();
        Stream.of(ResultCode.values()).forEach(code -> {
            ResponseMessage responseMessage = responseMessageBuilder.code(code.getCode()).message(code.getMsg()).build();
            responseMessages.add(responseMessage);
        });
        ResponseMessage defaultSuccessResponseCode = responseMessageBuilder.code(HttpServletResponse.SC_OK).message("Http返回状态码").build();
        responseMessages.add(defaultSuccessResponseCode);
        return responseMessages;

    }

    /**
     * 设置默认headers
     *
     * @return
     */
    private List<Parameter> defaultGlobalHeaders() {
        List<Parameter> headers = Lists.newArrayList();
        ModelRef modelRef = new ModelRef("string");
        // TODO  测试token值,上线关闭
        ShiroProperties.JWT jwt = shiroProperties.getJwt();
        String header = jwt.getTokenHeader();
        String testTokenValue = jvFastCommonProperties.getSwagger().getTestToken();
        ParameterBuilder tokenParameter = new ParameterBuilder();
        Parameter token = tokenParameter.name(header).description("认证请求token").modelRef(modelRef).parameterType("header")
                .defaultValue(testTokenValue).required(true)
                .build();
        headers.add(token);
        return headers;
    }
}
