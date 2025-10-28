package org.example.springdatajpa2;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> getCustomerByEmail(String email);

    // 각 고객과 고객의 주문 수를 알고 싶다. (주문 없는 고객을 보려면 Customer 엔티티에 있는 orders 와 join 해야함)
    @Query("select c, count(o) from Customer c left join c.orders o group by c")
    List<Object[]> findCustomerOrderCount(Pageable pageable);

    // 고객의 세부 정보와 그 고객이 가장 최근 주문을 조회
    @Query("select c, o from Customer c left join c.orders o where o.date = (select max(o2.date) from Order o2 where o2.customer = c)")
    List<Object[]> findCustomerRecentOrder();

    // 평균 나이보다 많은 고객을 조회
    @Query(nativeQuery = true, value = "select c.* from customers c where c.age > (select avg(c2.age) from customers c2)")
    List<Customer> findCustomerOlder();
}
