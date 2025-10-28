package org.example.springdatajpa2;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @Test
    void getCustomer() {
        customerService.getCustomer(1L);

    }

    @Test
    void getCustomers() {
        List<Customer> customers = customerService.getCustomers();
        customers.forEach(System.out::println);
    }

    @Test
    void createCustomer() {
        Customer customer = customerService.createCustomer("강경미", "carami@gmail.com");

        // assertNotNull(customer);
        // assertNotNull(customer.getId());

        assertThat(customer).isNotNull();
        assertThat(customer.getId()).isNotNull();
        assertThat(customer.getEmail()).isEqualTo("carami@gmail.com");
    }

    @Test
    void updateCustomer() {
        Customer updateCustomer = new Customer("고길동", "gogo@gmail.com");
        updateCustomer.setAge(20);
        Customer customer = customerService.updateCustomer(1L, updateCustomer);
        assertThat(customer.getName()).isEqualTo("고길동");
    }

    @Test
    void deleteCustomer() {
        customerService.deleteCustomer(1L);

        assertThatThrownBy(() -> customerService.getCustomer(1L))
                .isInstanceOf(IllegalArgumentException.class)   // 없으면 IllegalArgumentException이 발생하도록 해놨기 때문에
                .hasMessage("Customer not found!");
    }

    @Test
    void getCustomerByEmail() {
        Customer customerByEmail = customerService.getCustomerByEmail("hong@example.com");

        assertThat(customerByEmail).isNotNull();
        assertThat(customerByEmail.getId()).isNotNull();
        assertThat(customerByEmail.getName()).isEqualTo("홍길동");
    }

    @Test
    void getCustomerByEmailNotFound() {
        // Customer customerByEmail = customerService.getCustomerByEmail("notfound@example.com");
        assertThatThrownBy(() -> customerService.getCustomerByEmail("test@test.com"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void findCustomerOrderCount() {
        assertThat(customerService.findCustomerOrderCount()).hasSize(5);    // 한 페이지를 5개로 해서 5가 맞음
    }

    @Test
    void findCustomerRecentOrder() {
        customerService.findCustomerRecentOrder();
    }

    @Test
    void findCustomerOlder() {
        List<Customer> customers = customerService.findCustomerOlder();
        customers.forEach(System.out::println);
    }
}