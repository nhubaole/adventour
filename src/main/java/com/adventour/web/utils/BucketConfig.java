package com.adventour.web.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class BucketConfig {

    @Value("${aws.access.key}")
    String awsAccessKey;

    @Value("${aws.secret.key}")
    String awsSecretKey;
    @Value("${aws.s3.endpoint}")
    String endpoint;

    @Bean
    public S3Client getAmazonS3Client() throws URISyntaxException {
        AwsCredentials credentials = AwsBasicCredentials.create(awsAccessKey, awsSecretKey);
        Region region = Region.AP_SOUTHEAST_1;

        return S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .endpointOverride(new URI(endpoint))
                .forcePathStyle(true)
                .build();
    }
}