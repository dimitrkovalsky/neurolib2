package com.liberty;

import com.liberty.config.*;
import com.liberty.service.RecommendationService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class PreprocessRunner {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(PreprocessRunner.class).run(args);
        RecommendationService service = context.getBean(RecommendationService.class);
        service.preProcess();
    }
}
