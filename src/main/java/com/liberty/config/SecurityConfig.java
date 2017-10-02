package com.liberty.config;

import com.liberty.error.ApplicationException;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.io.InputStream;

/**
 * Created by user on 01.05.2017.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/signin")
                .and()
                .logout()
                .logoutUrl("/signout")
                .deleteCookies("SESSION")
                .and()
                .authorizeRequests()
                .antMatchers("/connect/**").denyAll()
                .antMatchers( "/favicon.ico", "/resources/**", "/api/images/**", "/signin/**", "/book/*", "/author/*","/genre/*","/selection/*").permitAll()
                .antMatchers("/genres","/selections/*","/reco","/").permitAll()
                .antMatchers("/searchbook","/searchbooktypeahead","/searchauthor","/searchauthortypeahead", "/api/authorborn").permitAll()
                .antMatchers("/**").authenticated();

    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring().antMatchers("secured/")
                .antMatchers("/**/*.css", "/**/*.png", "/**/*.gif", "/**/*.jpg", "/**/*.js","/sitemap.xml","/sitemaps/*.xml");
    }

    @Bean
    public AntiSamy antiSamy(){
        return new AntiSamy();
    }

    @Bean
    public Policy policy(){
        try{
            //Allow i,em,a,b,blockquote,br tags only
            InputStream policy = SecurityConfig.class.getResourceAsStream("/antisamy-slashdot.xml");
            return  Policy.getInstance(policy);
        }catch (PolicyException e){
            throw new ApplicationException(e.getMessage());
        }

    }
}

