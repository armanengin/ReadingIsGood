armanarmanpackagearmanarman.reading.sgood.service.impl;

import com.arman.reading.sgood.model.Customer;
import com.arman.reading.sgood.model.Order;
import com.arman.reading.sgood.repository.OrderDetailRepo;
import com.arman.reading.sgood.service.CustomerService;
import com.arman.reading.sgood.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final CustomerService customerService;
    private final OrderDetailRepo orderDetailRepository;

    public StatisticsServiceImpl(CustomerService customerService, OrderDetailRepo orderDetailRepository) {
        this.customerService = customerService;
        this.orderDetailRepository = orderDetailRepository;
    }

    public int getTotalOrderCount(Customer customer, Date date){
        java.util.Date currDate = new java.util.Date();
        List<Order> orders = customerService.listOrdersByDate(customer, date, currDate);
        return orders.size();
    }

    public Double totalAmountOfAllPurchasedOrders(Customer customer, Date date){
        Double totalPurchasedOrdersAmount = 0.0;
        java.util.Date currDate = new java.util.Date();
        List<Order> orders = customerService.listOrdersByDate(customer, date, currDate);
        for(Order order : orders){
            totalPurchasedOrdersAmount += orderDetailRepository.findById(order.getId()).get().getPurchase();
        }
        return totalPurchasedOrdersAmount;
    }

    public int totalCountOfPurchasedBooks(Customer customer, Date date){
        int totalCountOfPurchasedBooks = 0;
        java.util.Date currDate = new java.util.Date();
        List<Order> orders = customerService.listOrdersByDate(customer, date, currDate);
        for(Order order : orders){
            totalCountOfPurchasedBooks += orderDetailRepository.findById(order.getId()).get().getPurchased_book_count();
        }
        return totalCountOfPurchasedBooks;
    }
}
