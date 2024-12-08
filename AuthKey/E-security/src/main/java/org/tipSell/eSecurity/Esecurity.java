package org.tipSell.eSecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
//@EnableDiscoveryClient
@ComponentScan(basePackages = "com.mail.*")
public class Esecurity {

	public static void main(String[] args) {
		SpringApplication.run(Esecurity.class, args);
	}
}
