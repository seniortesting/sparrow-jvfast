package com.jvfast.common.config.web;

import com.jvfast.common.config.JVFastCommonProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.VersionResourceResolver;


@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final WebMvcProperties webMvcProperties;
    private final JVFastCommonProperties jvFastCommonProperties;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String defaultStaticPathPattern = webMvcProperties.getStaticPathPattern();
        VersionResourceResolver versionResourceResolver = new VersionResourceResolver();
        versionResourceResolver.addContentVersionStrategy(defaultStaticPathPattern);

        // 上传文件到目录 files下面,然后会自动匹配路由 /webfile/到对应的files目录下面的文件
        // 例如上传目录为: files/upload/23232.png ,那么访问路径是: /context-path/webfile/upload/23232.png
        registry.addResourceHandler(jvFastCommonProperties.getResourcePatterns() + "**")
                .addResourceLocations("file:" + jvFastCommonProperties.getStorePath()).resourceChain(true)
                .addResolver(versionResourceResolver);
        // swagger bootstrap配置
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/").resourceChain(true)
                .addResolver(versionResourceResolver);
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/").resourceChain(true)
                .addResolver(versionResourceResolver);
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/").resourceChain(true)
                .addResolver(versionResourceResolver);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "doc.html");
    }

}
