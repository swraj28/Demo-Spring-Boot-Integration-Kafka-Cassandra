package com.example.demo.commons.configurations.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration("smsParsingCCTransactionsKafkaConfig")
@ConfigurationProperties("sms-parsing-cc-transactions-kafka-config.kafka")
public class smsParsingCCTransactionsKafkaConfig {
    private String bootstrapServers;
    private List<String> topicNames;
    private String consumerGroup;

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public List<String> getTopicNames() {
        return topicNames;
    }

    public void setTopicNames(List<String> topicNames) {
        this.topicNames = topicNames;
    }

    public String getConsumerGroup() {
        return consumerGroup;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    @Override
    public String toString() {
        return "KafkaPropertiesConfig{" +
                "bootstrapServers='" + bootstrapServers + '\'' +
                ", topicNames=" + topicNames +
                ", consumerGroup='" + consumerGroup + '\'' +
                '}';
    }
}