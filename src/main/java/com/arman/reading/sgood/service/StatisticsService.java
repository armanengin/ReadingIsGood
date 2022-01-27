package com.getir.reading.sgood.service;


import com.getir.reading.sgood.model.Customer;

import java.util.Date;

public interface StatisticsService {
    int getTotalOrderCount(Customer customer, Date date);
    Double totalAmountOfAllPurchasedOrders(Customer customer, Date date);
    int totalCountOfPurchasedBooks(Customer customer, Date date);
}
