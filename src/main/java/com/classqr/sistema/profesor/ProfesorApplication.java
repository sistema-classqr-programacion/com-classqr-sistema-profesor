package com.classqr.sistema.profesor;

import com.classqr.sistema.commons.configuration.ApplicationConfig;
import com.classqr.sistema.commons.configuration.SpringSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(
		basePackages = "com.classqr.sistema"
)
@Import({SpringSecurityConfig.class, ApplicationConfig.class})
public class ProfesorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfesorApplication.class, args);
	}

}
