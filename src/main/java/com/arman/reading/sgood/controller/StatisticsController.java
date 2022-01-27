package com.getir.reading.sgood.controller;

import com.getir.reading.sgood.model.Customer;
import com.getir.reading.sgood.service.CustomerService;
import com.getir.reading.sgood.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@RestController
public class StatisticsController {
    private final CustomerService customerService;
    private final StatisticsService statisticsService;

    public StatisticsController(CustomerService customerService, StatisticsService statisticsService) {
        this.customerService = customerService;
        this.statisticsService = statisticsService;
    }

    @RequestMapping(value = "statistic/{customerId}/{date}", method = RequestMethod.GET)
        public ModelAndView showStatistic(@PathVariable("customerId") int customerId,
                                          @PathVariable("date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date ){
            //TO-DO throw an exception if optionalCustomer is empty
            Optional<Customer> optionalCustomer = customerService.findCustomerById(customerId);
            Customer customer = optionalCustomer.get();
            ModelAndView modelAndView = new ModelAndView();

            // **************To get month name from date **************
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int month = cal.get(Calendar.MONTH);
            String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

            // **************To get total_order_count **************
            int total_order_count = statisticsService.getTotalOrderCount(customer, date);

            // **************To get total_amount_of_all_purchased_orders **************
            Double total_amount_of_all_purchased_orders = statisticsService.totalAmountOfAllPurchasedOrders(customer, date);

            // **************To get total_count_of_purchased_orders **************
            int total_count_of_purchased_books = statisticsService.totalCountOfPurchasedBooks(customer, date);

            modelAndView.addObject("month", monthNames[month]);
            modelAndView.addObject("total_order_count", total_order_count);
            modelAndView.addObject("total_amount_of_all_purchased_orders", total_amount_of_all_purchased_orders);
            modelAndView.addObject("total_count_of_purchased_books", total_count_of_purchased_books);

            modelAndView.setViewName("statistic");
            return modelAndView;
    }
}
