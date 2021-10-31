package com.jun.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class TestserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(TestserviceApplication.class, args);
		StringTest strTest = new StringTest();
		strTest.processLanguageDiscrepancies();
		RegexDemo regexDemo = new RegexDemo();
		regexDemo.run();
	}

}
