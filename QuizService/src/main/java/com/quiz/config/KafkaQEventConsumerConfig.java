package com.quiz.config;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import com.quiz.common.view.QuestionEvent;


@Configuration
@ConditionalOnProperty(name = "app.kafka.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaQEventConsumerConfig {


    @Bean
    public ConsumerFactory<String, QuestionEvent> consumerFactory() {
    	   Map<String, Object> props = new HashMap<>();
           props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
           props.put(ConsumerConfig.GROUP_ID_CONFIG, "quiz-service-group");
           props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
           props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class); // ✅ correct one
           props.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // trust all packages, or restrict to your package
           
           return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), 
                   new JsonDeserializer<>(QuestionEvent.class, false));
           
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, QuestionEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, QuestionEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setCommonErrorHandler(errorHandler());
        return factory;
    }
    
    @Bean
    public DefaultErrorHandler errorHandler() {
        // retry 5 times, wait 2s each
        return new DefaultErrorHandler(
            new FixedBackOff(2000L, 5L)
        );
    }
}
