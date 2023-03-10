package com.example.dropit;

import com.example.dropit.config.GeoapifyConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties({
		GeoapifyConfigurationProperties.class
})
public class DropitApplication {

	public static void main(String[] args) {
		SpringApplication.run(DropitApplication.class, args);
	}

}
