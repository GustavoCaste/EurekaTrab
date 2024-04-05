package br.com.everdev.demoeurekaclientc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DemoEurekaClientCApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoEurekaClientCApplication.class, args);
	}

}
