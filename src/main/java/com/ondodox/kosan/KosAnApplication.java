package com.ondodox.kosan;

import com.ondodox.kosan.user.User;
import com.ondodox.kosan.user.UserRepository;
import com.ondodox.kosan.user.UserRole;
import com.ondodox.kosan.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sun.tools.jar.CommandLine;

import java.util.Optional;

@SpringBootApplication
public class KosAnApplication {

    public static void main(String[] args) {
        SpringApplication.run(KosAnApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService){
        return args -> {
            Optional<User> userOptional = userService.findByUsernameOptional("admin");
            if (!userOptional.isPresent()){
                User user = new User();
                user.setUsername("admin");
                user.setName("Admin");
                user.setPassword("admin");
                user.setRole(UserRole.ADMIN);
                userService.registerUser(user);
            }
        };
    }
}
