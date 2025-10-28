package org.example.springdatajpa2;

import lombok.RequiredArgsConstructor;
import org.example.springdatajpa.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    // @Autowired   // 1. 필드주입
    // private CustomerService customerService;

    // 2. 생성자 주입 변형
    private final CustomerRepository customerRepository;

    // 3. 설정자 주입 (의존성을 주입 받을 customerRepository 필드가 있어야함)
    // public void setCustomerService(CustomerService customerService) {
    //     this.customerService = customerService;
    // }

    // 고객정보를 얻어오고 싶다.
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found!"));
    }

    // 전체 고객정보를 얻어오고 싶다.
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    // 고객정보를 추가하고 싶다.
    public Customer createCustomer(String name, String email) {
        Customer customer = new Customer(name, email);
        return customerRepository.save(customer);
    }

    // 고객정보를 수정하고 싶다
    @Transactional
    public Customer updateCustomer(Long id, Customer customer) {
        Customer findCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found!"));
        findCustomer.setName(customer.getName());
        findCustomer.setEmail(customer.getEmail());
        findCustomer.setAge(customer.getAge());

        return customerRepository.save(findCustomer);
    }

    // 고객을 삭제하고 싶다.
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    // 이메일에 해당하는 고객 정보를 얻어오고 싶다.
    public Customer getCustomerByEmail(String email) {
        return customerRepository.getCustomerByEmail(email).orElseThrow(() -> new IllegalArgumentException(email + "을 사용하는 고객을 찾을 수 없습니다."));
    }

    // 고객 정보, 고객의 주문 수 조회
    public List<Object[]> findCustomerOrderCount() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Object[]> customers = customerRepository.findCustomerOrderCount(pageable);

        // customers.forEach(customer -> {
        //     String customerInfo = customer[0].toString();
        //     Long orderCount = (Long) customer[1];
        //     System.out.println("고객정보 : " + customerInfo + ", 주문 수 : " + orderCount);
        // });

        return customers;
    }

    public List<Object[]> findCustomerRecentOrder() {
        List<Object[]> customers = customerRepository.findCustomerRecentOrder();

        customers.forEach(customer -> {
            String customerInfo = customer[0].toString();
            String orderCount = customer[1].toString();
            System.out.println("고객정보 : " + customerInfo + ", 최근 주문 : " + orderCount);
        });

        return customers;
    }

    public List<Customer> findCustomerOlder() {
        List<Customer> customers = customerRepository.findCustomerOlder();

        return customers;
    }
}
