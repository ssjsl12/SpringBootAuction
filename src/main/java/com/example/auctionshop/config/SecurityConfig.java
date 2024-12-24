package com.example.auctionshop.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig
{
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

       log.info("Configuring SecurityFilterChain");
       System.out.println("-------------Configuring SecurityFilterChain------------------");

       http
               .authorizeHttpRequests(config->
                       config.requestMatchers("/css/**","/js/**" , "/img/**").permitAll()
                               .requestMatchers("/","/members/**", "/item/**", "/images/**").permitAll()
                               .requestMatchers("/admin/**").hasRole("ADMIN")
                               .anyRequest().authenticated()
               );
       http
               .formLogin(config->config.loginPage("/members/login")
               .defaultSuccessUrl("/")
               .usernameParameter("email")
               .failureUrl("/members/login/error")
       )
               .logout(
                       config->config.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                               .logoutSuccessUrl("/members/login")

       );


       http.csrf(config->config.disable());


       return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
