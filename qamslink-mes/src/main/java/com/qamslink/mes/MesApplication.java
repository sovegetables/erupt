package com.qamslink.mes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import xyz.erupt.core.annotation.EruptScan;

@SpringBootApplication
@EruptScan
@EntityScan
@EnableJpaRepositories
public class MesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MesApplication.class, args);
	}
}
