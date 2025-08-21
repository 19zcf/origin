package com.zcf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;



@SpringBootApplication
public class TiliasWebManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TiliasWebManagementApplication.class, args);
    }

}
