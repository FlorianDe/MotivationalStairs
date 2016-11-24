package de.motivational.stairs;

import de.motivational.stairs.beamer.AppPrincipalFrame;
import de.motivational.stairs.beamer.opengl.StairsTransformFrame;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class MotivationalStairsApplication  extends WebMvcAutoConfiguration {

	public static void main(String[] args) {
		///*
		ConfigurableApplicationContext context = new SpringApplicationBuilder(MotivationalStairsApplication.class)
				.headless(false)
				.web(true)
				.run(args);
		AppPrincipalFrame frame = context.getBeanFactory().getBean(AppPrincipalFrame.class);

		frame.startFrame();

//*/
		//StairsTransformFrame transformFrame = new StairsTransformFrame();
		//SpringApplication.run(MotivationalStairsApplication.class, args);
	}

}
