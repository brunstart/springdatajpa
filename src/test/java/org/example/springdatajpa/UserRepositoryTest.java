package org.example.springdatajpa;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional  // 테스트가 끝난 후 롤백함
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() throws Exception {
        // userRepository.deleteAll();

        userRepository.save(new User("carami", "carami@gmail.com"));
        userRepository.save(new User("kang", "kang@gmail.com"));
        userRepository.save(new User("kim", "kim@gmail.com"));
        userRepository.save(new User("park", "park@gmail.com"));
        userRepository.save(new User("yang", "yang@gmail.com"));
    }

    @Test
    void 사용자_추가() {
        User user = new User("carami2222", "carami2222@gmail.com");
        User saveuser = userRepository.save(user);

        // assertThat(user.getId()).isNotNull();    // 너무 느려서 Build Tool gradle -> IntelliJ로 변경
        // assertThat(user.getName()).isEqualTo("park");

        System.out.println(saveuser);
    }

    @Test
    void 사용자_수정() {
        User user = new User("hong", "hong@exam.com");
        User saveuser = userRepository.save(user);

        saveuser.setName("hhhh");

        userRepository.save(saveuser);  // 수정도 save 사용

        User updateUser = userRepository.findById(saveuser.getId()).get();
        System.out.println(updateUser);

        assertThat(saveuser.getName()).isEqualTo("hhhh");
    }

    @Test
    void 사용자_삭제() {
        User user = new User("testUser", "testUser@exam.com");
        userRepository.save(user);

        User finduser = userRepository.findById(user.getId()).get();

        userRepository.delete(finduser);
        assertThat(userRepository.findById(user.getId()).orElse(null)).isNull();
    }

    @Test
    void findAll() {
        userRepository.findAll().
                forEach(u -> System.out.println(u));
    }

    @Test
    void findByEmail() {
        List<User> users = userRepository.findByEmail("park@gmail.com");

        assertThat(users).hasSize(1);
        assertThat(users.get(0).getName()).isEqualTo("park");
    }

    @Test
    void 기타검색() {
        List<User> userList = userRepository.findByNameContaining("ng");

        // for (User user : userList) {
        //     System.out.println(user);
        // }

        assertThat(userList).hasSize(2);
        assertThat(userList).extracting(User::getName).
                containsExactly("kang", "yang");    // 순서도 상관이 있음

        List<User> kangUsers = userRepository.findByNameAndEmail("kang", "kang@gmail.com");
        assertThat(kangUsers).hasSize(1);

        List<User> kangUsers2 = userRepository.findByNameAndEmail("kang", "kang@exam.com");
        assertThat(kangUsers2).hasSize(0);

        List<User> kangUsers3 = userRepository.findByNameOrEmail("kang", "kang@exam.com");
        assertThat(kangUsers3).hasSize(1);
    }

    @Test
    void selectUser() {
        List<User> kang = userRepository.selectUser("kang");
        List<User> userList = userRepository.selectUserByName("ng");

        assertThat(kang).hasSize(1);
        userList.forEach(u -> System.out.println(u));
    }
}
