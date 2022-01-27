package com.getir.reading.sgood.service;


import com.amazonaws.services.sqs.model.SendMessageResult;
import com.getir.reading.sgood.model.Order;

public interface SQSService {
    void sendSqsMessage(Order order);
    void runScheduler();
    boolean getHasSchedularWorked();
    void setHasSchedularWorked(boolean hasSchedularWorked);
//    void SQSListener(Order order);
}
