package com.example.arexmytest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement //开启注解事务管理
public class ArexMyTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArexMyTestApplication.class, args);
	}

}
