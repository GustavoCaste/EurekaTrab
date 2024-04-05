package br.com.everdev.demoeurekaclientb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DemoEurekaClientBApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoEurekaClientBApplication.class, args);
	}

}
