package org.example.springdatajpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Slf4j
@SpringBootApplication
public class SpringdatajpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringdatajpaApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            // user 추가
            // User kanguser = new User("kang", "kang@kang.com");
            // userRepository.save(kanguser);
            //
            // log.info("user 추가 :: " + kanguser);

            // List<User> userList = userRepository.findByName("kang");
            // for(User user : userList){
            //     log.info("user :: " + user);
            // }

            // userRepository.findByEmail("kang@kang.com").
            //         forEach(u -> log.info(u.toString()));

            // userRepository.selectUser("kang").forEach(u -> log.info(u.toString()));

            // int i = userRepository.deleteUserByEmail("kang@kang.com");
            // System.out.println(i + "건 삭제");

            // int i = userRepository.updateUserByEmail(3L, "test@test.com");
            // User user = userRepository.findById(3L).get();
            // System.out.println(user);

            Pageable pageable = PageRequest.of(0, 10);
            List<User> test = userRepository.selectUserByEmail("park", pageable);
            for(User u : test) {
                log.info(u.toString());
            }

        };
    }
}
