package com.jlbcompany.app;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/messages")
public class MessageController {

    //inject the Kafka template
    private final KafkaTemplate<String, Message> kafkaTemplate;

    //add a constructor
    public MessageController(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    //because we want to expose this method to our clients
    //in order to issue post request against it
    //@PostMapping
    //public void publish(@RequestBody MessageRequest request) {
    //    kafkaTemplate.send("jlbcompany", request.message());

    //to convert request.message into message for Message.class
    //Because the message is a String we need to change both the Producer and Consumer
    //so we can work with objects
    @PostMapping
    public void publish(@RequestBody MessageRequest request) {
        Message message = new Message(request.message(), LocalDateTime.now());
        kafkaTemplate.send("jlbcompany", message);

    }

}
