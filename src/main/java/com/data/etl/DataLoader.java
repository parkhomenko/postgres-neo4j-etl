package com.data.etl;

import com.data.etl.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    private final AnnotationConfigApplicationContext applicationContext;
    private final MailService mailService;

    @Autowired
    public DataLoader(AnnotationConfigApplicationContext applicationContext, MailService mailService) {
        this.applicationContext = applicationContext;
        this.mailService = mailService;
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            mailService.extractAndUploadMails();

            int code = SpringApplication.exit(applicationContext);
            System.exit(code);
        };
    }
}
