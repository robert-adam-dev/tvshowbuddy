package br.com.tvshowbuddy.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MongoConfig {

    @Value("${mongo.user}")
    private String mongoUser;

    @Value("${mongo.password}")
    private String mongoPassword;

    @Value("${mongo.host}")
    private String mongoHost;

    @Value("${mongo.port}")
    private String mongoPort;

    @Value("${mongo.database}")
    private String mongoDatabase;

    @Bean
    public MongoClient mongoClient() {
        String connectionString = String.format(
                "mongodb+srv://%s:%s@%s",
                mongoUser, mongoPassword, mongoHost
        );

        log.info("Trying to connect to MongoDB.");

        try {
            MongoClient mongoClient = MongoClients.create(connectionString);
            log.info("Connection to MongoDB successful!");
            return mongoClient;
        } catch (Exception e) {
            log.error("Failed to connect to MongoDB: {}", e.getMessage(), e);
            throw e;
        }
    }
}