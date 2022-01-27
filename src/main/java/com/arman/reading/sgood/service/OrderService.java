package com.arman.reading.sgood.service;

import com.arman.reading.sgood.model.Book;
import com.arman.reading.sgood.model.Order;
import com.arman.reading.sgood.model.OrderDetail;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order saveOrder(Order order);
    Optional<Order> findOrderById(int id);
    Book updateBookStock(Order order);
    List<Order> listOrdersByDate(Date startDate, Date endDate);
    List<Order> findAll();
    void deleteOrderById(int id);
    Optional<OrderDetail> findOrderDetailById(int id);
}
