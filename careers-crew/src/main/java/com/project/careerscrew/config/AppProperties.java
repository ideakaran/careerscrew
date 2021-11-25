package com.project.careerscrew.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "myapp")
@Slf4j
@Getter
@Setter
public class AppProperties {

    public AppProperties() {
        log.info("Application Properties Initialized");
    }

    private String frontSite;
    private String companyBrandName;

    // Mail config
    private Mail mail = new Mail();

    // CORS configuration
    private Cors cors = new Cors();

    // Default
    private Defaults defaults = new Defaults();

    @Getter
    @Setter
    public static class Mail {
        private String defaultEmailAddress;
        private long verificationCodeExpirationSeconds = 600; // 10 minutes
    }

    @Getter
    @Setter
    public static class Cors {

        private String[] allowedOrigins;
        private String[] allowedMethods = {"GET", "POST", "PUT", "DELETE", "OPTIONS"};
        private String[] allowedHeaders = {"*"};
        private String[] exposedHeaders = {"*"};
        private long maxAge = 3600L;
    }

    @Getter
    @Setter
    public static class Defaults {

        private String initialAdminName;
        private String initialAdminEmail;
        private String initialAdminPass;

        private long resumeMaxFileSizeMB = -1;
        private String[] resumeFileSupportedTypes;
        private long probationCompletionDays;
    }

}
