package swa.meet.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swa.meet.configs.RabbitMQConfig;
import swa.meet.entities.Meeting;
import swa.meet.rabitmq.listener.MeetingNotificationCLI;

@Service
public class MeetingNotificationService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MeetingNotificationService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void notifyMeetingUpdate(Meeting meeting) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, meeting);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(Meeting meeting) {
        MeetingNotificationCLI.addMeeting(meeting);

    }
}
