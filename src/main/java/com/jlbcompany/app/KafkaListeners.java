package com.jlbcompany.app;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

//in order to have a listener that listens to our topic, create a method
@Component
public class KafkaListeners {

    //need to pass a group id, if we scale i.e. more instances of the same application
    //they can read from the same partition or topic
    //if you have more listeners, need to change groupId to be unique
    @KafkaListener(
            topics = "jlbcompany",
            groupId = "groupId"
            //containerFactory = "messageFactory"
    )
    void listener(String data) {
        System.out.println("Listener received: " + data + " tada!");
    }
}
