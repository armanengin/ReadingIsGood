package com.arman.reading.sgood.controller;

import com.arman.reading.sgood.model.Order;
import com.arman.reading.sgood.service.OrderService;
import com.arman.reading.sgood.service.SQSService;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SQSController {
    private SQSService sqsService;
    private OrderService orderService;

    @GetMapping
    public void SendMessage(Order order){
        sqsService.sendSqsMessage(order);
    }

    @SqsListener("ReadingIsGood-sqs.fifo")
    public void listenSQS(Order order){
        sqsService.runScheduler();
        if(sqsService.getHasSchedularWorked()) {
            orderService.saveOrder(order);
            sqsService.setHasSchedularWorked(false);
        }
    }
}
