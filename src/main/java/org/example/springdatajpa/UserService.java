package org.example.springdatajpa;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor    // final 붙은 객체 의존성 주입
public class UserService {
    // @Autowired   // 1. 필드로 주입
    private final UserRepository userRepository;    // DI(의존성 주입)

    // 2. 생성자로 의존성 주입
    // public UserService(UserRepository userRepository) {
    //     this.userRepository = userRepository;
    // }

    // 3. 설정자(Setter 메소드)로 주입
    // public void setUserRepository(UserRepository userRepository) {
    //     this.userRepository = userRepository;
    // }

}
