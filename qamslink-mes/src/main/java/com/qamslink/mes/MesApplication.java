package com.qamslink.mes;

import com.qamslink.mes.model.basic.MesBarcodeRule;
import com.qamslink.mes.repository.MesBarcodeRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
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

	@Autowired
	private MesBarcodeRuleRepository mesBarcodeRuleRepository;

	@Bean
	public CommandLineRunner demo(MesBarcodeRuleRepository repository) {
		return (args) -> {
//			Iterable<MesBarcodeRule> all = repository.findAll();
//			System.out.println(all);
//			Iterable<MesBarcodeRule> all1 = mesBarcodeRuleRepository.findAll();
//			System.out.println(all1);
		};
	}
}
