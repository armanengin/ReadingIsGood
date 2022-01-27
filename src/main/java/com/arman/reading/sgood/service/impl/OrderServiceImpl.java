package com.getir.reading.sgood.service.impl;

import com.getir.reading.sgood.exception.StockIsNegativeException;
import com.getir.reading.sgood.model.Book;
import com.getir.reading.sgood.model.Order;
import com.getir.reading.sgood.model.OrderDetail;
import com.getir.reading.sgood.repository.OrderDetailRepo;
import com.getir.reading.sgood.repository.OrderRepo;
import com.getir.reading.sgood.service.OrderDetailService;
import com.getir.reading.sgood.service.OrderService;
import com.getir.reading.sgood.service.SQSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepository;
    private final OrderDetailRepo orderDetailRepository;
    private final OrderDetailService orderDetailService;
    private final SQSService sqsService;

    public OrderServiceImpl(OrderRepo orderRepository, OrderDetailRepo orderDetailRepository,
                            OrderDetailService orderDetailService, SQSService sqsService) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.orderDetailService = orderDetailService;
        this.sqsService = sqsService;
    }

    public Order saveOrder(Order order){
        orderRepository.save(order);
        orderDetailService.saveOrderDetail(order.getOrderDetail());
        updateBookStock(order);
        return order;
    }

    public Optional<Order> findOrderById(int id){
        Optional<Order> order = orderRepository.findById(id);
        return order;
    }

    public Book updateBookStock(Order order){
        Book book = order.getBook();
        int orderedBookCount = orderDetailRepository.findById(order.getId()).get().getPurchased_book_count();

        //TO DO - check if stock num has become negative. If it is send an exception
        //same has to be done also in the BookController
        if(book.getStock() - orderedBookCount <= 0){
            sqsService.sendSqsMessage(order);
            throw new StockIsNegativeException("The order is not valid because the stock for the book is: "
                    + book.getStock() + " but the required book count is: " + orderedBookCount);
        }else{
            book.setStock(book.getStock() - orderedBookCount);
        }
        return book;
    }

    public List<Order> listOrdersByDate(Date startDate, Date endDate){
        List<Order> allOrders = orderRepository.findAll();
        List<Order> ordersListedByDate = new ArrayList<>();
        allOrders.stream().forEach(order -> {
            Date currDate = orderDetailRepository.findById(order.getId()).get().getDate();
            if(startDate.compareTo(currDate) <= 0 && endDate.compareTo(currDate) >= 0)
                ordersListedByDate.add(order);
        });
        return ordersListedByDate;
    }

    public List<Order> findAll(){
        List<Order> orders = new ArrayList<Order>();
        orderRepository.findAll().forEach(orders::add);
        return orders;
    }

    public void deleteOrderById(int id){
        orderRepository.deleteById(id);
    }

    public Optional<OrderDetail> findOrderDetailById(int id){
        return orderDetailRepository.findById(id);
    }
}
