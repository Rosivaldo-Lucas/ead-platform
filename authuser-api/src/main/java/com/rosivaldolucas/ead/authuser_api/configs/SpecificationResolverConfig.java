package com.rosivaldolucas.ead.authuser_api.configs;

import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.lang.NonNull;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class SpecificationResolverConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver();

        argumentResolvers.add(new SpecificationArgumentResolver());
        argumentResolvers.add(pageableResolver);

        super.addArgumentResolvers(argumentResolvers);
    }

}
