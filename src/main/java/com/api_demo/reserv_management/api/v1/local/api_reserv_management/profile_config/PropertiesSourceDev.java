package com.api_demo.reserv_management.api.v1.local.api_reserv_management.profile_config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:dev.properties")
@Profile("dev")
public class PropertiesSourceDev {

}
