package com.mohammali.web.bm.config.swagger;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Value
@ConstructorBinding
@ConfigurationProperties(prefix = "swagger.service")
public class SwaggerProperties {

    String version;
    String title;
    String description;
    String termsPath;
    String email;
    String licenceType;
    String licencePath;
}
