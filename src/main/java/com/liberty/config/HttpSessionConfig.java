package com.liberty.config;

/**
 * Created by user on 01.05.2017.
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@Configuration
@EnableJdbcHttpSession
public class HttpSessionConfig {
}

