package com.example.auctionshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing //등록자 , 수정자 처리해주는 AuditorAware Bean 등록
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider()
    {
        return new AuditorAwareImpl();
    }
}
