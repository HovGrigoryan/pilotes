package com.shop.pilotes;

import com.shop.pilotes.enums.Role;
import com.shop.pilotes.model.User;
import com.shop.pilotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication(scanBasePackages = "com.shop.pilotes")
public class PilotesApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(PilotesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User admin = User.builder()
                .firstName("admin")
                .lastName("admin")
                .email("admin@gmail.com")
                .password(new BCryptPasswordEncoder().encode("admin"))
                .role(Role.ADMIN)
                .telephone("+37494324314")
                .build();

        userRepository.save(admin);
    }

}
