package com.chatapp.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class AppConfig {

    /**
     * Instantiate and configure a PropertySourcesPlaceholderConfigurer.
     * This method creates a new instance of PropertySourcesPlaceholderConfigurer and sets the location of the property file to be used as "dev.env".
     * It returns the configured PropertySourcesPlaceholderConfigurer object.
     *
     * @return PropertySourcesPlaceholderConfigurer object configured with the location of the property file.
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new FileSystemResource("dev.env"));
        return configurer;
    }

}