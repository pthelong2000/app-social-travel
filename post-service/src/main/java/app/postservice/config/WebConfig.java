package app.postservice.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public Cloudinary cloudinaryConfig() {
        Map config = new HashMap();
        config.put("cloud_name", "springtestimageapi");
        config.put("api_key", "646949719412351");
        config.put("api_secret", "fqQ8dEVg-4AvfAE_Idq7kcsWtks");
        return new Cloudinary(config);
    }



}
