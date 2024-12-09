package io.github.kszapsza.springairag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringAiRagApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringAiRagApplication.class, args);
	}
}
