package com.jlbcompany.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;

@SpringBootApplication
public class KafkaApplication {

    public static void main(String[] args) {
                SpringApplication.run(KafkaApplication.class, args);
    }

    //will receive the Kafka template we have defined
    //this method will run as soon as we run the application
    @Bean
    CommandLineRunner commandLineRunner(KafkaTemplate<String, Message> kafkaTemplate) {
        return args -> {
            for (int i = 0; i < 5; i++) {
                //kafkaTemplate.send("amigoscode", "hello kafka :)");
                kafkaTemplate.send("jlbcompany",
                        new Message(
                                "hello kafka :) " + i,
                                LocalDateTime.now()
                        )
                );
            }
        };
    }
}
