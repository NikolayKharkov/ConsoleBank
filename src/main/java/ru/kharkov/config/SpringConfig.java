package ru.kharkov.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("ru.kharkov")
@PropertySource("classpath:application.properties")
public class SpringConfig {

}
