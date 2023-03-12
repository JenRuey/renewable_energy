package com.liteon.renewable_energy;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.liteon.renewable_energy.repository")
public class RenewableEnergyApplication {
	public static void main(String[] args) {
		SpringApplication.run(RenewableEnergyApplication.class, args);

		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(Controller.class));
		for (BeanDefinition beanDefinition : scanner.findCandidateComponents("com.liteon.renewable_energy.controller")){
			System.out.println(beanDefinition.getBeanClassName());
		}
	}
}
