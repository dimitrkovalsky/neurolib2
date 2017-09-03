package com.liberty;

import com.liberty.config.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({SwaggerConfig.class, DbConfig.class, SecurityConfig.class, SocialConfig.class, HttpSessionConfig.class, XmlMarshalingConfig.class})
public class NeurolibApplication {

    public static void main(String[] args) {
        SpringApplication.run(NeurolibApplication.class, args);
    }
}
