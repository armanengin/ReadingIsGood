package com.getir.reading.sgood.service.impl;

import com.getir.reading.sgood.model.Customer;
import com.getir.reading.sgood.model.Order;
import com.getir.reading.sgood.repository.CustomerRepo;
import com.getir.reading.sgood.repository.OrderDetailRepo;
import com.getir.reading.sgood.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final BCryptPasswordEncoder encoder;
    private final CustomerRepo customerRepository;
    private final OrderDetailRepo orderDetailRepository;

    public CustomerServiceImpl(BCryptPasswordEncoder encoder, CustomerRepo customerRepository, OrderDetailRepo orderDetailRepository) {
        this.encoder = encoder;
        this.customerRepository = customerRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer){
        customer.setPassword(encoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

    @Override
    public boolean isCustomerAlreadyPresent(Customer customer){
        if(customerRepository.existsById(customer.getId()))
            return true;
        return false;
    }

    @Override
    public List<Order> getOrders(Customer customer, int numInPage, int pageSize){
        Pageable pageable = PageRequest.of(numInPage, pageSize);
        Page<Order> page = new PageImpl<>(customer.getOrders(), pageable, customer.getOrders().size());
        return page.toList();
    }

    @Override
    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

    @Override
    public void deleteCustomerById(int id){
        customerRepository.deleteById(id);
    }

    public Optional<Customer> findCustomerById(int id){
        return customerRepository.findById(id);
    }

    public List<Order> listOrdersByDate(Customer customer, Date startDate, Date endDate){
        List<Order> allOrders = customer.getOrders();
        List<Order> ordersListedByDate = new ArrayList<>();
        allOrders.stream().forEach(order -> {
            Date currDate = orderDetailRepository.findById(order.getId()).get().getDate();
            if(startDate.compareTo(currDate) <= 0 && endDate.compareTo(currDate) >= 0)
                ordersListedByDate.add(order);
        });
        return ordersListedByDate;
    }
}
