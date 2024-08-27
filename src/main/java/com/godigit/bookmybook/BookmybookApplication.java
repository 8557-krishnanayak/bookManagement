package com.godigit.bookmybook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class BookmybookApplication {


//	comment
	public static void main(String[] args) {
		SpringApplication.run(BookmybookApplication.class, args);
		log.info("--- Application Started ---");
	}

}
