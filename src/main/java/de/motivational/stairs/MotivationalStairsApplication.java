package de.motivational.stairs;

import de.motivational.stairs.beamer.AppPrincipalFrame;
import de.motivational.stairs.beamer.opengl.StairsTransformFrame;
import de.motivational.stairs.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableConfigurationProperties
public class MotivationalStairsApplication  extends WebMvcAutoConfiguration {
	public static boolean withServer = true;

	public static void main(String[] args) {
		if(withServer) {
			ConfigurableApplicationContext context = new SpringApplicationBuilder(MotivationalStairsApplication.class)
					.headless(false)
					.web(true)
					.run(args);

			//final AppPrincipalFrame frame = context.getBeanFactory().getBean(AppPrincipalFrame.class);
			//frame.startFrame();
		} else {
			StairsTransformFrame transformFrame = new StairsTransformFrame();
		}
	}

	@Bean
	static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	AppConfig appConfig(){
		return new AppConfig();
	}

	@Bean
	AppPrincipalFrame appPrincipalFrame(){
		return new AppPrincipalFrame(appConfig());
	}

}
