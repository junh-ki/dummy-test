package com.jun.springcloud;

import com.jun.springcloud.builder.NutritionFacts;
import com.jun.springcloud.singleton.Elvis;
import com.jun.springcloud.string.RegexDemo;
import com.jun.springcloud.string.StringBuilderDemo;
import com.jun.springcloud.string.StringTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class TestserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(TestserviceApplication.class, args);
		//testString();
		//testBuilder();
		testSingleton();
	}

	public static void testString() {
		StringTest strTest = new StringTest();
		strTest.processLanguageDiscrepancies();
		RegexDemo regexDemo = new RegexDemo();
		regexDemo.run();
		StringBuilderDemo stringBuilderDemo = new StringBuilderDemo();
		stringBuilderDemo.run();
	}

	public static void testBuilder() {
		NutritionFacts nutritionFacts = new NutritionFacts.Builder(10, 20)
				.fat(5)
				.calories(10)
				.carbonhydrate(4)
				.sodium(3)
				.build();
		System.out.println(nutritionFacts.toString());
	}

	private static void testSingleton() {
		System.out.println(Elvis.getInstance().toString());
	}

}
