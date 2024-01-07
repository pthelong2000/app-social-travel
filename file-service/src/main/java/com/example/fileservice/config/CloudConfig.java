package com.example.fileservice.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudConfig {
    @Bean
    public Cloudinary cloudinaryConfig() {
        Map config = new HashMap();
        config.put("cloud_name", "springtestimageapi");
        config.put("api_key", "646949719412351");
        config.put("api_secret", "fqQ8dEVg-4AvfAE_Idq7kcsWtks");
        return new Cloudinary(config);
    }

}
