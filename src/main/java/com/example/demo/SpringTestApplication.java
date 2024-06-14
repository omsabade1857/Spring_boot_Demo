package com.example.demo;

import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringTestApplication {

	//logger 
    private static final Logger logger = LogManager.getLogger(SpringTestApplication.class);
    
    public static void main(String[] args) {
//    	TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
        SpringApplication.run(SpringTestApplication.class, args);
        logger.info("Application started");
    }
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
