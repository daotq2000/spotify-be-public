//package com.spotify.config;
//
//import com.amazonaws.services.s3.AmazonS3Client;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//public class S3Config {
//
//    @Value("${aws.region}")
//    private String region;
//
//    @Bean
//    public AmazonS3Client s3client() {
//        return (AmazonS3Client) AmazonS3Client.builder().withRegion(region).build();
//    }
//}
