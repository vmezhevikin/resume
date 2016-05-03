package net.devstudy.resume.configuration;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@ComponentScan({ "net.devstudy.resume.service.impl", "net.devstudy.resume.controller", "net.devstudy.resume.filter", "net.devstudy.resume.listener" })
public class ServiceConfig
{
	/**
	 * http://docs.spring.io/autorepo/docs/spring/4.2.5.RELEASE/spring-framework
	 * -reference/html/beans.html
	 * 
	 * Also, be particularly careful with BeanPostProcessor and
	 * BeanFactoryPostProcessor definitions via @Bean. Those should usually be
	 * declared as static @Bean methods, not triggering the instantiation of
	 * their containing configuration class. Otherwise, @Autowired and @Value
	 * won’t work on the configuration class itself since it is being created as
	 * a bean instance too early.
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() throws IOException
	{
		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		configurer.setLocations(getResources());
		return configurer;
	}

	private static Resource[] getResources()
	{
		return new Resource[] { new ClassPathResource("application.properties") };
	}
}