package com.nordson.materiel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.nordson.materiel.mapper")
public class MaterielApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaterielApplication.class, args);
	}

}
