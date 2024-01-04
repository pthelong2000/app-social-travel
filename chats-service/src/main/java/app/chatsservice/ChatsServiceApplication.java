package app.chatsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ChatsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatsServiceApplication.class, args);
	}

}
