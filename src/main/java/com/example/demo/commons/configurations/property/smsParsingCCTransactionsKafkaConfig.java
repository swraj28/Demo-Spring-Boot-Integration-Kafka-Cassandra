package com.example.demo.commons.configurations.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Setter
@Getter
@Configuration("smsParsingCCTransactionsKafkaConfig")
@ConfigurationProperties("sms-parsing-cc-transactions-kafka-config.kafka")
public class smsParsingCCTransactionsKafkaConfig {
    private String bootstrapServers;
    private List<String> topicNames;
    private String consumerGroup;

    @Override
    public String toString() {
        return "KafkaPropertiesConfig{" +
                "bootstrapServers='" + bootstrapServers + '\'' +
                ", topicNames=" + topicNames +
                ", consumerGroup='" + consumerGroup + '\'' +
                '}';
    }
}