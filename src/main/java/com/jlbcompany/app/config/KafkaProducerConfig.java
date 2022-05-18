package com.jlbcompany.app.config;


import com.jlbcompany.app.Message;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    //variable to hold the bootstrap server url
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    //configuration that we can pass to a producer factory
    public Map<String, Object> producerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }
    //define the producer factory, responsible for creating producer instances/kafka producers
    //send java object i.e. <String, Customer>
    //instantiate as a Bean
    @Bean
    //public ProducerFactory<String, String> producerFactory() {
    public ProducerFactory<String, Message> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    //way to send messages
    //dependency injection producerFactory
    @Bean
    //public KafkaTemplate<String, String> kafkaTemplate(
    public KafkaTemplate<String, Message> kafkaTemplate(
            ProducerFactory<String, Message> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }
}
