package net.devstudy.resume.configuration;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@ComponentScan({ "net.devstudy.resume.service.impl", "net.devstudy.resume.controller", "net.devstudy.resume.filter",
		"net.devstudy.resume.listener", "net.devstudy.resume.scheduler", "net.devstudy.resume.component.impl",
		"net.devstudy.resume.validator" })
public class ServiceConfig {
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() throws IOException {
		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		configurer.setLocations(getResources());
		return configurer;
	}

	private static Resource[] getResources() {
		return new Resource[] { new ClassPathResource("application.properties"), new ClassPathResource("logic.properties") };
	}
}