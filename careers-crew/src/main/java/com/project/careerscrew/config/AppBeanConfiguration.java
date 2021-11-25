package com.project.careerscrew.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class AppBeanConfiguration {

    @Autowired
    private AppProperties appProperties;

    /**
     * Setting up Cors Configuration
     * `http.cors(withDefaults())` uses a Bean by the name of CorsConfigurationSource
     *
     * @return CorsConfigurationSource
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(appProperties.getCors().getAllowedOrigins()));
        configuration.setAllowedMethods(Arrays.asList(appProperties.getCors().getAllowedMethods()));
        configuration.setAllowedHeaders(Arrays.asList(appProperties.getCors().getAllowedHeaders()));
        configuration.setExposedHeaders(Arrays.asList(appProperties.getCors().getExposedHeaders()));
        configuration.setMaxAge(appProperties.getCors().getMaxAge());
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Setting up Password encoder
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
