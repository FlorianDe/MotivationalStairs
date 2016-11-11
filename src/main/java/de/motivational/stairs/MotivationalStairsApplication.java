package de.motivational.stairs;

import de.motivational.stairs.beamer.AppPrincipalFrame;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class MotivationalStairsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(MotivationalStairsApplication.class).headless(false).run(args);
		//AppPrincipalFrame appFrame = context.getBean(AppPrincipalFrame.class);
	}

	@Bean
	public AppPrincipalFrame appPrincipalFrame() {
		return new AppPrincipalFrame();
	}
}
