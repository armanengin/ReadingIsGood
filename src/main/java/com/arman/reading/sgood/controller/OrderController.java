package com.arman.reading.sgood.controller;

import com.arman.reading.sgood.exception.EntityNotFoundException;
import com.arman.reading.sgood.model.Book;
import com.arman.reading.sgood.model.OrderDetail;
import com.arman.reading.sgood.model.Order;
import com.arman.reading.sgood.service.BookService;
import com.arman.reading.sgood.service.OrderDetailService;
import com.arman.reading.sgood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("order")
public class OrderController {
    private final OrderService orderService;
    private final BookService bookService;
    private final OrderDetailService orderDetailService;

    public OrderController(OrderService orderService, BookService bookService, OrderDetailService orderDetailService) {
        this.orderService = orderService;
        this.bookService = bookService;
        this.orderDetailService = orderDetailService;
    }

    @PostMapping("/save")
    public ResponseEntity<Order> create(@Valid @RequestBody Order order){
        try{
            Order savedOrder = orderService.saveOrder(order);
            orderDetailService.saveOrderDetail(order.getOrderDetail());
            updateBookStock(savedOrder.getId());
            return new ResponseEntity<>(savedOrder, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/updateBookStock/{orderId}")
    public ResponseEntity<Book> updateBookStock(@PathVariable("orderId") int orderId){
        Optional<Order> orderOptional = orderService.findOrderById(orderId);
        if(orderOptional.isPresent()){
            try{
                Book book = orderService.updateBookStock(orderOptional.get());
                return new ResponseEntity<>(bookService.saveBook(book), HttpStatus.OK);
            }catch(Exception e){
                return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") int id) throws EntityNotFoundException {
        Optional<Order> orderOptional = orderService.findOrderById(id);
        if(orderOptional.isPresent()){
            return new ResponseEntity<>(orderOptional.get(), HttpStatus.OK);
        }else{
            throw new EntityNotFoundException("Order is not exist for the Id!");
        }
    }

    @GetMapping("getByDate/{startDate}/{endDate}")
    public ResponseEntity<List<Order>> getOrdersByDate(@PathVariable("startDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
                                                       @PathVariable("endDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate){
        try {
            List<Order> orders = orderService.listOrdersByDate(startDate, endDate);
            if (orders.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(orders, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Order>> getAll(){
        List<Order> orders = orderService.findAll();
        try {
            if (orders.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(orders, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id){
        try{
            orderService.deleteOrderById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("{id}/getDetail")
    public ResponseEntity<OrderDetail> getDetail(@PathVariable("id") int id){
        Optional<OrderDetail> optionalOrderDetail = orderService.findOrderDetailById(id);
        if(optionalOrderDetail.isPresent()){
            OrderDetail orderDetail = optionalOrderDetail.get();
            return new ResponseEntity<>(orderDetail, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
