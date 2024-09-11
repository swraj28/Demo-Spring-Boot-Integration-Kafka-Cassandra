# Demo Project To Integrate Spring Boot with Apache Kafka and Apache Cassandra



## Prerequisites
- Java 17
- Maven 3.6+
- Spring Boot 3.3.3

## Setup
1. Clone the repository:
    ```sh
    git clone https://github.com/swraj28/Demo-Spring-Boot-Integration-Kafka-Cassandra
    ```

2. Build the project:
    ```sh
    mvn clean install
    ```

## Running the Application
1. Run the Spring Boot application:
    ```sh
    mvn spring-boot:run
    ```

## Logging Configuration
- The application uses Log4j2 for logging.
- Log files are stored in `/var/log/smsParsingCCTransactions/`.
- Ensure the directory exists and has the correct permissions:
    ```sh
    sudo mkdir -p /var/log/smsParsingCCTransactions
    sudo chown -R $(whoami) /var/log/smsParsingCCTransactions
    ```

## Running Tests
1. Run the tests using Maven:
    ```sh
    mvn test
    ```

## Dependencies
- Spring Boot Starter
- Log4j2
- Spring Data Cassandra
- Apache Commons Collections
- Apache Commons Lang3
- Spring Kafka
- Lombok

## Dependencies
- **Spring Boot Starter**: Provides core features of Spring Boot.
- **Log4j2**: Used for logging.
- **Spring Data Cassandra**: Provides integration with Cassandra.
- **Apache Commons Collections**: Utility classes for collections.
- **Apache Commons Lang3**: Utility classes for the java.lang API.
- **Spring Kafka**: Provides integration with Apache Kafka.
- **Lombok**: Reduces boilerplate code by generating getters, setters, and other methods.

## Technologies and Annotations

### Spring Boot
Spring Boot simplifies the development of stand-alone, production-grade Spring-based applications. It provides a range of non-functional features common to large classes of projects, such as embedded servers and security.

### Apache Kafka
Apache Kafka is a distributed streaming platform used for building real-time data pipelines and streaming applications. It is horizontally scalable, fault-tolerant, and fast.

### Apache Cassandra
Apache Cassandra is a distributed NoSQL database designed to handle large amounts of data across many commodity servers, providing high availability with no single point of failure.

### Annotations
- `@SpringBootApplication`: Indicates a configuration class that declares one or more `@Bean` methods and also triggers auto-configuration and component scanning.
- `@EnableKafka`: Enables Kafka listener annotated endpoints that are created under the `@KafkaListener` annotation.
- `@KafkaListener`: Marks a method to be the target of a Kafka message listener on the specified topics.
- `@Entity`: Specifies that the class is an entity and is mapped to a database table.
- `@Table`: Specifies the primary table for the annotated entity.
- `@Id`: Specifies the primary key of an entity.
- `@GeneratedValue`: Provides for the specification of generation strategies for the values of primary keys.
- `@Column`: Used to specify the mapped column for a persistent property or field.
- `@Repository`: Indicates that the class provides the mechanism for storage, retrieval, search, update, and delete operation on objects.
- `@Service`: Indicates that the class is a service, which holds business logic.
- `@RestController`: Combines `@Controller` and `@ResponseBody`, indicating that the class is a controller where every method returns a domain object instead of a view.
- `@RequestMapping`: Provides routing information and is used to map web requests to specific handler classes or handler methods.