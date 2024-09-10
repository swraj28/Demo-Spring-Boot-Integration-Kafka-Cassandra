package com.example.demo.commons.configurations;

import com.example.demo.commons.constants.Constants;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.demo.commons.configurations.property.smsParsingCCTransactionsKafkaConfig;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    private final smsParsingCCTransactionsKafkaConfig smsParsingCCTransactionsKafkaConfig;

    @Autowired
    public KafkaConfig(@NonNull smsParsingCCTransactionsKafkaConfig smsParsingCCTransactionsKafkaConfig) {
        this.smsParsingCCTransactionsKafkaConfig = smsParsingCCTransactionsKafkaConfig;
    }

    @Bean
    public Map<String, Object> smsParsingCCTransactionsKafkaProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, smsParsingCCTransactionsKafkaConfig.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, smsParsingCCTransactionsKafkaConfig.getConsumerGroup());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, Constants.CommonConstants.KAFKA_POOL_SIZE);
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 1048576 * 8);
        return props;
    }

    @Bean
    public ConsumerFactory<String, String> smsParsingCCTransactionsFactory() {
        return new DefaultKafkaConsumerFactory<>(smsParsingCCTransactionsKafkaProperties());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> smsParsingCCTransactionsContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(smsParsingCCTransactionsFactory());
        factory.setBatchListener(true);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }
}