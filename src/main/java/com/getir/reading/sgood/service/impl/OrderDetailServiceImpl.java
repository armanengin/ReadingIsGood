package com.getir.reading.sgood.service.impl;

import com.getir.reading.sgood.model.OrderDetail;
import com.getir.reading.sgood.repository.OrderDetailRepo;
import com.getir.reading.sgood.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepo orderDetailRepository;

    public OrderDetailServiceImpl(OrderDetailRepo orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    public OrderDetail saveOrderDetail(OrderDetail orderDetail){
        return orderDetailRepository.save(orderDetail);
    }
}
