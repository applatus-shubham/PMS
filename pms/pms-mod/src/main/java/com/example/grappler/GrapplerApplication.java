package com.example.grappler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@SpringBootApplication
public class GrapplerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrapplerApplication.class, args);
		System.out.println("Running SuccessFully");

	}
}
