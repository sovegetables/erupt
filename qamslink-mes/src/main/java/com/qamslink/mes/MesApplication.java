package com.qamslink.mes;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import xyz.erupt.core.annotation.EruptScan;

import javax.persistence.EntityManager;

@SpringBootApplication
@EruptScan
@EntityScan
@EnableJpaRepositories
public class MesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MesApplication.class, args);
	}

	@Bean
	public JPAQueryFactory jpaQuery(EntityManager entityManager) {
		return new JPAQueryFactory(entityManager);
	}
}
