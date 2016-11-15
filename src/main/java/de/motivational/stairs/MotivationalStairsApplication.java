package de.motivational.stairs;

import de.motivational.stairs.beamer.AppPrincipalFrame;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class MotivationalStairsApplication  extends WebMvcAutoConfiguration {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(MotivationalStairsApplication.class)
				.headless(false)
				.web(true)
				.run(args);
		//SpringApplication.run(MotivationalStairsApplication.class, args);
	}
/*
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MotivationalStairsApplication.class);
	}
*/

	/*
	@Bean
	public AppPrincipalFrame appPrincipalFrame() {
		return new AppPrincipalFrame();
	}
	*/
}
