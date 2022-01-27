package com.getir.reading.sgood;

import com.getir.reading.sgood.model.Customer;
import com.getir.reading.sgood.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReadingIsGoodApplicationTests {
    @Autowired
    CustomerService customerService;

    @Test
    void contextLoads() {
    }

    @Test
    void testAddCustomer() {
        Customer customer = new Customer();
        customer.setFirst_name("test");
        customer.setLast_name("test_lastname");
        customer.setPassword("123456");
        customer.setEmail("test@gmail.com");
        customerService.saveCustomer(customer);
    }

}
