package org.example.springdatajpa;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> { // <엔티티 클래스, 엔티티 클래스의 PK의 타입>을 넣어줘야함
    List<User> findByName(String name);
    List<User> findByEmail(String email);
    List<User> findByNameContaining(String name);
    // 입력된 이름과, 이메일에 해당하는 user 검색
    // where name = ? and email = ?
    List<User> findByNameAndEmail(String name, String email);

    // 입력된 이름이거나, 이메일인 user 검색
    List<User> findByNameOrEmail(String name, String email);

    @Query("SELECT u from User u where u.name=:name")  // User 가 테이블 명이 아니라 엔티티 (대소문자 구분), 클래스명임
    List<User> selectUser(@Param("name")String name);
    // 콜론 뒤 name은 엔티티의 필드명

    @Query("select u from User u where u.name like %:name%")
    List<User> selectUserByName(@Param("name")String name);

    // JPA에서 삭제를 하기전에 먼저 조회를 해야함, 조회 후 삭제, 수정도 마찬가지
    // Transactional 사용하는 이유 : 조회, 삭제가 한 트랜잭션 안에 있도록 하기 위해서, 안쓰면 조회 시, 삭제 시의 엔티티 매니저가 다를 수도 있음
    // 삭제, 수정은 서비스 레이어에서 수행, 엔티티를 조회, 트랜잭션이 끝나는 시점에 수정, 삭제 수행
    @Transactional
    @Modifying  // 값을 변경해도 된다고 인지
    @Query("delete from User u where u.email = :email")
    int deleteUserByEmail(@Param("email")String email);

    @Transactional
    @Modifying
    @Query("update User u set u.email=:email where u.id = :id")
    int updateUserByEmail(@Param("id")Long id, @Param("email")String email);


    @Query(nativeQuery = true, value = "select * from jpa_users where email like %:email%")
    List<User> selectUserByEmail(@Param("email")String email, Pageable pageable);
}
