package com.example.datn_realeaste_crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DatnRealeasteCrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatnRealeasteCrmApplication.class, args);


//                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//                String rawPassword = "Admin@123";
//                String encodedPassword = encoder.encode(rawPassword);
//                System.out.println(encodedPassword);


    }

}
