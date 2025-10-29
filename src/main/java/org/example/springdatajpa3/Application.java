package org.example.springdatajpa3;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            // userRepository.save(new User("admin", "admin@example.com"));

            // userRepository.findAll().forEach(System.out::println);

            // userRepository.lionCustom();

            // userRepository.findUsersByName("p")
            //         .forEach(System.out::println);

            userRepository.findUserDynamically("ng", null)
                    .forEach(System.out::println);
        };
    }
}
