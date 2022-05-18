package com.jlbcompany.app.config;

import com.jlbcompany.app.Message;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    //configuration that we can pass to a consumer factory
    //Consumer needs to deserialize using the same data types or custom object
    public Map<String, Object> consumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        //props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }
    //define the consumer factory
    //send java object i.e. <String, Customer>
    //instantiate as a Bean
    @Bean
    //public ConsumerFactory<String, String> consumerFactory() {
    public ConsumerFactory<String, Message> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfig(),
                new StringDeserializer(),
                new JsonDeserializer<>()
        );
    }

    //specify the listener container factory
    //receive all messages, topics from all partitions on a single thread
    public KafkaListenerContainerFactory<
            ConcurrentMessageListenerContainer<String,Message>> messageFactory (
            ConsumerFactory<String, Message> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, Message> messageFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        messageFactory.setConsumerFactory(consumerFactory);
        return messageFactory;

    }
}
