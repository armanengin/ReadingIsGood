package com.getir.reading.sgood.controller;

import com.getir.reading.sgood.model.Customer;
import com.getir.reading.sgood.model.Order;
import com.getir.reading.sgood.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //main methods
    @PostMapping("/save")
    public ResponseEntity<Customer> create(@Valid @RequestBody Customer customer){
        try{
            Customer savedCustomer = customerService.saveCustomer(customer);
            return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/getOrders/{pageNo}/{size}")
    public ResponseEntity<List<Order>> getAllOrders(@RequestBody Customer customer,
                                                    @PathVariable("pageNo") int pageNo,
                                                    @PathVariable("size") int size){
        try {
            List<Order> orders = customerService.getOrders(customer, pageNo, size);

            if(orders.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(orders, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //not main methods
    @GetMapping("/getAll")
    public ResponseEntity<List<Customer>> getAll(){
        List<Customer> customers = customerService.findAll();
        try {
            if (customers.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(customers, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id){
        try{
            customerService.deleteCustomerById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
  //  Will persist new customers
   //     Will query all orders of the customer ( Paging sounds really nice )
