package vn.iotstar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.iotstar.model.Customer;

import java.util.List;

@RestController
@EnableMethodSecurity
public class CustomerController {
    @Autowired
    final private List<Customer> customers = List.of(Customer.builder().id("001").name("John").phoneNumber("1234567890").email("johndoe@gmai.com").build(),
            Customer.builder().id("002").name("Jane").phoneNumber("0987654321").email("jane@gmail.com").build());

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello it's Guest");
    }

    @GetMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> getCustomerById(@PathVariable("id") String id) {
        // Tìm kiếm khách hàng theo ID
        List<Customer> filteredCustomers = this.customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .toList();

        // Kiểm tra nếu không tìm thấy khách hàng
        if (filteredCustomers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customer with ID " + id + " not found");
        }

        // Trả về khách hàng đầu tiên tìm thấy
        return ResponseEntity.ok(filteredCustomers.get(0));
    }

    @PostMapping("/customer/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Customer>> getAllCustomerList() {
        List<Customer> list = this.customers;
        return ResponseEntity.ok(list);
    }
}