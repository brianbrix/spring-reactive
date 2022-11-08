package com.me.reactivetutorial;

import com.me.reactivetutorial.db.repos.ProductRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})

@EnableR2dbcRepositories(basePackageClasses = ProductRepo.class)
public class ReactiveTutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveTutorialApplication.class, args);
	}

}
