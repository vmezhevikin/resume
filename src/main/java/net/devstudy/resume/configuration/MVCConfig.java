package net.devstudy.resume.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan({"net.devstudy.resume.controller"})
public class MVCConfig extends WebMvcConfigurerAdapter
{
	@Bean
	public ViewResolver viewResolver()
	{
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/JSP/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
		registry.addResourceHandler("/media/**").addResourceLocations("/media/");
		registry.addResourceHandler("/restore/static/**").addResourceLocations("/static/");
		registry.addResourceHandler("/restore/media/**").addResourceLocations("/media/");
		registry.addResourceHandler("/sign-up/static/**").addResourceLocations("/static/");
		registry.addResourceHandler("/sign-up/media/**").addResourceLocations("/media/");
		registry.addResourceHandler("/edit/static/**").addResourceLocations("/static/");
		registry.addResourceHandler("/edit/media/**").addResourceLocations("/media/");
		//registry.addResourceHandler("/favicon/**").addResourceLocations("/favicon.ico");
	}
}
