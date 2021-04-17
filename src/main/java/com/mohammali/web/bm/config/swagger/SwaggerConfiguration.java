package com.mohammali.web.bm.config.swagger;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
@RequiredArgsConstructor
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfiguration {

    private final SwaggerProperties swaggerProperties;
    private final ServerProperties serverProperties;

    @Bean
    public Docket swaggerDocumentation() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(paths())
            .build()
            .protocols(protocols())
            .apiInfo(apiInfo());
    }

    private Set<String> protocols() {
        var set = new HashSet<String>();
        if (serverProperties.getSsl() != null && serverProperties.getSsl().isEnabled()) {
            set.add("https");
        } else {
            set.add("http");
        }
        return set;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title(swaggerProperties.getTitle())
            .description(swaggerProperties.getDescription())
            .termsOfServiceUrl(swaggerProperties.getTermsPath())
            .contact(new Contact("", "", swaggerProperties.getEmail()))
            .license(swaggerProperties.getLicenceType())
            .licenseUrl(swaggerProperties.getLicencePath())
            .version(swaggerProperties.getVersion())
            .build();
    }

    private Predicate<String> paths() {
        return Predicates.or(PathSelectors.regex("/api.*"));
    }
}
