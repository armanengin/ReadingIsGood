package com.getir.reading.sgood.service;

import com.getir.reading.sgood.model.Book;
import com.getir.reading.sgood.model.Order;
import com.getir.reading.sgood.model.OrderDetail;

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
