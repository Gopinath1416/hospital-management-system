package com.hospital.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI hospitalManagementOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Hospital Management System API")
                        .description(
                                "REST API documentation for managing Patients, Doctors, Appointments and Medical Records")
                        .version("1.0"));
    }
}
