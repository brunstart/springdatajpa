package org.example.springdatajpa3;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
// Spring Data JPA가 구현한 구현체가 아니라 필요한 부분 직접 구현해서 사용가능
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final EntityManager entityManager;

    // JPQL를 통해서 쿼리 생성
    // CriteriaBuilder를 이용해서 쿼리 생성, entityManager로 실행
    @Override
    public List<User> findUsersByName(String name){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> user = query.from(User.class);// User 엔티티로부터 쿼리 사용
        query.select(user).where(builder.like(user.get("name"), "%"+name+"%"));
        // select u from User u where u.name like %name%;
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<User> findUserDynamically(String name, String email){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> user = query.from(User.class);// User 엔티티로부터 쿼리 사용

        List<Predicate> predicates = new ArrayList<>();
        if(name != null){
            // predicates.add(builder.equal(user.get("name"), name));
            predicates.add(builder.like(user.get("name"), "%"+name+"%"));
        }

        if(email != null){
            predicates.add(builder.equal(user.get("email"), email));
        }

        query.select(user).where(builder.and(predicates.toArray(new Predicate[0]))); // predicates 배열 첫번째 원소를 where절 조건으로 사용
        // 사용자가 입력한 값에 따라 쿼리가 동적으로 생성 (builder.and를 써서 and로 연결됨, builder.or면 or로 연결)
        // name = null, email = null --> select u from User u; 로 쿼리가 완성됨
        // name != null, email = null --> select u from User u where u.name = name;
        // name = null, email != null --> select u from User u where u.email = email;
        // name != null, email != null --> select u from User u where u.name = name and u.email = email;

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void lionCustom() {
        System.out.println("lionCustom");
    }
}
