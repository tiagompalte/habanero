package com.tiagopalte.habanero.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = { "file:\\${USERPROFILE}\\aws\\.habanero-s3.properties", "file://${HOME}/aws/.habanero-s3.properties" }, ignoreResourceNotFound = true)
public class S3Config {

    @Value("${s3.region}")
    private String region;

    @Autowired
    private Environment env;

    private static final String AWS_ACCESS_KEY_ID = "AWS_ACCESS_KEY_ID";
    private static final String AWS_SECRET_ACCESS_KEY = "AWS_SECRET_ACCESS_KEY";

    @Bean
    public AmazonS3 s3Client(){
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(env.getProperty(AWS_ACCESS_KEY_ID),env.getProperty(AWS_SECRET_ACCESS_KEY));
        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                                .withRegion(Regions.fromName(region))
                                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
        return amazonS3;
    }

}
