package com.peace;

import java.io.File;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MramApplication {

public static String ROOT = "uploads";
	
	@Bean
	CommandLineRunner init() {
      return (String[] args) -> {
          new File(ROOT).mkdir();
      };
	}
	public static void main(String[] args) {
		SpringApplication.run(MramApplication.class, args);
	}
}
