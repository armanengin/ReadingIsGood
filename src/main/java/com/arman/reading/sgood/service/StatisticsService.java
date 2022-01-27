package com.arman.reading.sgood.service;


import com.arman.reading.sgood.model.Customer;

import java.util.Date;

public interface StatisticsService {
    int getTotalOrderCount(Customer customer, Date date);
    Double totalAmountOfAllPurchasedOrders(Customer customer, Date date);
    int totalCountOfPurchasedBooks(Customer customer, Date date);
}
