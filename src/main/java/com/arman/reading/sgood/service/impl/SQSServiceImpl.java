armanpackage com.arman.reading.sgood.service.impl;

import com.arman.reading.sgood.model.Order;
import com.arman.reading.sgood.service.OrderService;
import com.arman.reading.sgood.service.SQSService;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Getter
public class SQSServiceImpl implements SQSService {
    private final QueueMessagingTemplate queueMessagingTemplate;
    private boolean hasSchedularWorked;

    public SQSServiceImpl(QueueMessagingTemplate queueMessagingTemplate){
        this.queueMessagingTemplate = queueMessagingTemplate;
        this.hasSchedularWorked = false;
    }

    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;

    public void sendSqsMessage(Order order) {
        queueMessagingTemplate.send(endpoint, MessageBuilder.withPayload(order).build());
    }

    @Scheduled(fixedDelay = 1800000)
    public void runScheduler(){
        hasSchedularWorked = true;
    }

    public boolean getHasSchedularWorked(){
        return hasSchedularWorked;
    }

    public void setHasSchedularWorked(boolean hasSchedularWorked){
        this.hasSchedularWorked = hasSchedularWorked;
    }

    /*
    @SqsListener("ReadingIsGood-sqs.fifo")
    public void SQSListener(Order order){
        if(hasSchedularWorked) {
            orderService.saveOrder(order);
            hasSchedularWorked = false;
        }
    }
     */
}
