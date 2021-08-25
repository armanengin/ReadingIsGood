package com.getir.reading.sgood.service;

import com.getir.reading.sgood.model.Customer;
import com.getir.reading.sgood.model.Order;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    boolean isCustomerAlreadyPresent(Customer customer);
    List<Order> getOrders(Customer customer, int numInPage, int pageSize);
    List<Customer> findAll();
    void deleteCustomerById(int id);
    Optional<Customer> findCustomerById(int id);
    List<Order> listOrdersByDate(Customer customer, Date startDate, Date endDate);
}
